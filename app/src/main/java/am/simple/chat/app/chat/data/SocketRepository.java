package am.simple.chat.app.chat.data;
import androidx.lifecycle.MutableLiveData;
import com.microsoft.signalr.HubConnection;
import com.microsoft.signalr.HubConnectionState;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
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
        hubConnection.start().blockingAwait();
    }

    public void sendMessage(SendTestMessageModel model) {
        hubConnection.invoke(SOCKET_SEND_MESSAGE, model);
    }

    protected void initHubConnection(MutableLiveData<Integer> socketConnectionLiveData, MutableLiveData<ArrayList<ChatItem>> messagesLiveData) {
        hubConnection.on(SOCKET_CONNECT,(isConnected) -> {
            socketConnectionLiveData.postValue(isConnected ? SocketConnectionType.CONNECTED : SocketConnectionType.UNDEFINED);
        }, Boolean.class);
        hubConnection.on(SOCKET_RECEIVE_MESSAGE_FROM_OTHER_USER,(data) -> {
                messageReceived(new OnReceivedTestModel(data));
                addMessage(messagesLiveData, data);
        }, OnTestMessageModel.class);
        hubConnection.on(SOCKET_CLIENT_RECEIVE_THE_MESSAGE,(data) -> { }, OnReceivedTestModel.class);
        hubConnection.onClosed(exception -> socketConnectionLiveData.postValue(SocketConnectionType.UNDEFINED));
    }

    private void addMessage(MutableLiveData<ArrayList<ChatItem>> messagesLiveData, OnTestMessageModel data){
        ArrayList<ChatItem> list = messagesLiveData.getValue();
        if (list == null) list = new ArrayList<>();
        list.add(new ChatItem(data));
        messagesLiveData.postValue(list);
    }

    private void messageReceived(OnReceivedTestModel model){
        hubConnection.invoke(SOCKET_NOTIFY_MESSAGE_RECEIVED, model);
    }

    public Boolean isConnected() {
        return hubConnection.getConnectionState() == HubConnectionState.CONNECTED;
    }

    protected void closeConnection() {
        hubConnection.stop();
    }
}
