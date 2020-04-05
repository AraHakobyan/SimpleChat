package am.simple.chat.app.chat.data;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.microsoft.signalr.HubConnection;
import org.jetbrains.annotations.NotNull;
import am.simple.chat.app.chat.data.model.OnReceivedTestModel;
import am.simple.chat.app.chat.data.model.OnTestMessageModel;
import am.simple.chat.app.chat.data.model.SendTestMessageModel;
import am.simple.chat.core.data.type.SocketConnectionType;

import static am.simple.chat.core.constants.NetworkConstants.SOCKET_CLIENT_RECEIVE_THE_MESSAGE;
import static am.simple.chat.core.constants.NetworkConstants.SOCKET_CONNECT;
import static am.simple.chat.core.constants.NetworkConstants.SOCKET_NOTIFY_MESSAGE_RECEIVED;
import static am.simple.chat.core.constants.NetworkConstants.SOCKET_RECEIVE_MESSAGE;
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

    protected void initHubConnection(MutableLiveData<Integer> socketConnectionLiveData) {
        hubConnection.on(SOCKET_CONNECT,(isConnected) -> {
            socketConnectionLiveData.postValue(isConnected ? SocketConnectionType.CONNECTED : SocketConnectionType.UNDEFINED);
        }, Boolean.class);
        hubConnection.on(SOCKET_RECEIVE_MESSAGE,(data) -> {
                messageReceived(new OnReceivedTestModel(data));
        }, OnTestMessageModel.class);
        hubConnection.on(SOCKET_CLIENT_RECEIVE_THE_MESSAGE,(data) -> {
                Log.d("Socket1111", "SOCKET_CLIENT_RECEIVE_THE_MESSAGE " + data);
        }, OnReceivedTestModel.class);
    }

    private void messageReceived(OnReceivedTestModel model){
        hubConnection.invoke(SOCKET_NOTIFY_MESSAGE_RECEIVED, model);
    }

    protected void closeConnection() {
        hubConnection.stop();
    }
}
