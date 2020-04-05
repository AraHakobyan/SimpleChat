package am.simple.chat.app.chat.data

import am.simple.chat.app.chat.data.model.SendTestMessageModel
import am.simple.chat.app.chat.data.network.ChatNetworkApi
import android.util.Log
import com.microsoft.signalr.Action
import com.microsoft.signalr.HubConnection

/**
 * Created by Ara Hakobyan on 4/4/2020.
 * Company IDT
 */
class ChatRepository(
    private val chatApi: ChatNetworkApi,
    private val hubConnection: HubConnection
) {

    init {
        initHubConnection()
        onStartSocketConnection()
    }

    suspend fun getMessages() = chatApi.getMessages()

    private fun initHubConnection() {
        hubConnection.run {
           val b = on("OnConnected", Action {
                Log.d("Socket1111", "OnConnected")
            })
            Log.d("Socket1111", "b = $b")
            on("OnMessage",Action {
                Log.d("Socket1111", "OnMessage")
            })
            on("OnReceived", Action {
                Log.d("Socket1111", "OnReceived")
            })
        }

    }

    suspend fun sendMessage(model: SendTestMessageModel) {
        hubConnection.invoke("OnMessage", model)
    }

    private fun onStartSocketConnection() {
        hubConnection.start()
    }

    fun onCloseSocketConnection() {
        hubConnection.stop()
    }

}