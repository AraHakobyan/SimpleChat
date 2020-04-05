package am.simple.chat.app.chat.data

import am.simple.chat.app.chat.data.network.ChatNetworkApi
import com.microsoft.signalr.HubConnection

/**
 * Created by Ara Hakobyan on 4/4/2020.
 * Company IDT
 */
class ChatRepository(
    private val chatApi: ChatNetworkApi,
    hubConnection: HubConnection
) : SocketRepository(hubConnection){

    suspend fun getMessages() = chatApi.getMessages()


//    suspend fun sendMessage(model: SendTestMessageModel) {
//        hubConnection.invoke("OnMessage", model)
//    }
//
//    suspend fun receiveMessage(model: OnReceivedTestModel) {
//        hubConnection.invoke("OnReceived",model)
//    }

    fun onStartSocketConnection() {
        initHubConnection()
        hubConnection.start()
    }

    fun onCloseSocketConnection() {
        hubConnection.stop()
    }

}