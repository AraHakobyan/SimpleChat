package am.simple.chat.app.chat.data;
import androidx.lifecycle.MutableLiveData;

import com.microsoft.signalr.HubConnection;
import com.microsoft.signalr.HubConnectionState;

import org.jetbrains.annotations.NotNull;

import am.simple.chat.app.chat.data.model.ChatItem;
import am.simple.chat.app.chat.data.model.OnReceivedTestModel;
import am.simple.chat.app.chat.data.model.OnTestMessageModel;
import am.simple.chat.app.chat.data.model.SendTestMessageModel;
import am.simple.chat.core.data.type.SocketConnectionType;

import static am.simple.chat.core.constants.NetworkConstants.SOCKET_CLIENT_RECEIVE_THE_MESSAGE;
import static am.simple.chat.core.constants.NetworkConstants.SOCKET_CONNECT;
import static am.simple.chat.core.constants.NetworkConstants.SOCKET_NOTIFY_MESSAGE_RECEIVED;
import static am.simple.chat.core.constants.NetworkConstants.SOCKET_RECEIVE_MESSAGE_FROM_OTHER_USER;
import static am.simple.chat.core.constants.NetworkConstants.SOCKET_SEND_MESSAGE;

/**
 * Created by Ara Hakobyan on 4/5/2020.
 * Company IDT
 */
public class SocketRepository {

    private HubConnection hubConnection;

    public SocketRepository(@NotNull HubConnection hubConnection) {
        this.hubConnection = hubConnection;
    }

    protected void openConnection() {
        hubConnection.start();
    }

    public void sendMessage(SendTestMessageModel model) {
        hubConnection.invoke(SOCKET_SEND_MESSAGE, model);
    }

    protected void initHubConnection(MutableLiveData<Integer> socketConnectionLiveData,
                                     MutableLiveData<OnReceivedTestModel> messageSeenLiveData, MutableLiveData<ChatItem> newMessageLiveData) {
        hubConnection.on(SOCKET_CONNECT, (isConnected) -> connectionChanged(isConnected, socketConnectionLiveData), Boolean.class);
        hubConnection.on(SOCKET_RECEIVE_MESSAGE_FROM_OTHER_USER, (data) -> newMessageReceived(data, newMessageLiveData), OnTestMessageModel.class);
        hubConnection.on(SOCKET_CLIENT_RECEIVE_THE_MESSAGE, (data) -> myMessageReceived(data, messageSeenLiveData), OnReceivedTestModel.class);
        hubConnection.onClosed(exception -> connectionClosed(socketConnectionLiveData));
    }

    private void connectionChanged(Boolean isConnected, MutableLiveData<Integer> socketConnectionLiveData) {
        socketConnectionLiveData.postValue(isConnected ? SocketConnectionType.CONNECTED : SocketConnectionType.UNDEFINED);
    }

    /**
     * Received message from other user
     */
    private void newMessageReceived(OnTestMessageModel data, MutableLiveData<ChatItem> newMessageLiveData) {
        messageReceived(new OnReceivedTestModel(data));
        newMessageLiveData.postValue(new ChatItem(data));
    }
    /**
     * My message has is seen by other
     */
    private void myMessageReceived(OnReceivedTestModel data, MutableLiveData<OnReceivedTestModel> messageSeenLiveData) {
        messageSeenLiveData.postValue(data);
    }

    private void connectionClosed(MutableLiveData<Integer> socketConnectionLiveData){
        socketConnectionLiveData.postValue(SocketConnectionType.UNDEFINED);
    }

    private void messageReceived(OnReceivedTestModel model) {
        hubConnection.invoke(SOCKET_NOTIFY_MESSAGE_RECEIVED, model);
    }

    public Boolean isConnected() {
        return hubConnection.getConnectionState() == HubConnectionState.CONNECTED;
    }

    protected void closeConnection() {
        hubConnection.stop();
    }
}
