package am.simple.chat.app.chat.data

import am.simple.chat.app.chat.data.model.ChatItem
import am.simple.chat.app.chat.data.model.OnReceivedTestModel
import am.simple.chat.app.chat.data.network.ChatNetworkApi
import am.simple.chat.core.data.type.SocketConnectionType
import androidx.lifecycle.MutableLiveData
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

    fun onStartSocketConnection(
        socketConnectionLiveData: MutableLiveData<@SocketConnectionType Int>,
        messageSeenLiveData: MutableLiveData<OnReceivedTestModel>,
        newMessageLiveData: MutableLiveData<ChatItem>
    ) {
        initHubConnection(socketConnectionLiveData, messageSeenLiveData, newMessageLiveData)
        openConnection()
    }

    fun onCloseSocketConnection() {
        closeConnection()
    }
}