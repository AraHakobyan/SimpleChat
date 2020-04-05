package am.simple.chat.app.chat.data;

import android.util.Log;

import com.microsoft.signalr.HubConnection;

import org.jetbrains.annotations.NotNull;

import am.simple.chat.app.chat.data.model.OnReceivedTestModel;
import am.simple.chat.app.chat.data.model.OnTestMessageModel;

/**
 * Created by Ara Hakobyan on 4/5/2020.
 * Company IDT
 */
public class SocketRepository {

    protected HubConnection hubConnection;

    public SocketRepository(@NotNull HubConnection hubConnection) {
        this.hubConnection = hubConnection;
    }

    protected void initHubConnection() {

        hubConnection.on("OnConnected",(isConnected) -> {
                Log.d("Socket1111", "OnConnected = $isConnected");
        }, Boolean.class);
        hubConnection.on("OnMessage",(data) -> {
                Log.d("Socket1111", "OnMessage " + data);
        }, OnTestMessageModel.class);
        hubConnection.on("OnReceived",(data) -> {
                Log.d("Socket1111", "OnReceived $it" + data);
        }, OnReceivedTestModel.class);
    }
}
