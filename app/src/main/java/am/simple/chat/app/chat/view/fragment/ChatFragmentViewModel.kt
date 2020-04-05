package am.simple.chat.app.chat.view.fragment

import am.simple.chat.app.chat.data.ChatRepository
import am.simple.chat.app.chat.data.model.ChatItem
import am.simple.chat.app.chat.data.model.SendTestMessageModel
import am.simple.chat.core.data.type.SocketConnectionType
import am.simple.chat.core.view.BaseFragmentViewModel
import android.content.Context
import androidx.lifecycle.MutableLiveData

/**
 * Created by Ara Hakobyan on 4/4/2020.
 * Company IDT
 */
class ChatFragmentViewModel(private val repository: ChatRepository,context: Context) : BaseFragmentViewModel(context) {

    val socketConnectionLiveData = MutableLiveData<@SocketConnectionType Int>()
    val messagesLiveData = MutableLiveData<ArrayList<ChatItem>>()

    fun getMessages() = executeBackendCall(call = { repository.getMessages()})

    fun startSocket() {
        repository.onStartSocketConnection(socketConnectionLiveData)
    }

    fun sendMessage(text: String) {
        val message = SendTestMessageModel().apply {
            userId = 1
            message = text
        }
        repository.sendMessage(message)
       addNewMessage(message)
    }

    private fun addNewMessage(message: SendTestMessageModel) {
        messagesLiveData.postValue(messagesLiveData.value?.also {
            it.add(ChatItem(message))
        })
    }

    override fun onCleared() {
        repository.onCloseSocketConnection()
        super.onCleared()
    }
}