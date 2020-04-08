package am.simple.chat.app.chat.view.activiy

import am.simple.chat.app.chat.data.ChatRepository
import am.simple.chat.app.chat.data.model.ChatItem
import am.simple.chat.app.chat.data.model.OnReceivedTestModel
import am.simple.chat.app.chat.data.model.SendTestMessageModel
import am.simple.chat.core.constants.USER_UNDEFINED
import am.simple.chat.core.data.type.SocketConnectionType
import am.simple.chat.core.view.BaseActivityViewModel
import android.content.Context
import androidx.lifecycle.MutableLiveData
import java.lang.Exception

/**
 * Created by Ara Hakobyan on 4/4/2020.
 * Company IDT
 */
class ChatActivityViewModel(private val repository: ChatRepository,context: Context) : BaseActivityViewModel(context){
    var userIdLiveData: MutableLiveData<Int> = MutableLiveData(USER_UNDEFINED)

    val socketConnectionLiveData = MutableLiveData<@SocketConnectionType Int>().also {
        it.value = SocketConnectionType.DISCONNECTED
    }
    val newMessageLiveData = MutableLiveData<ChatItem>()
    val messagesLiveData = MutableLiveData<ArrayList<ChatItem>>()
    val messageSeenLiveData = MutableLiveData<OnReceivedTestModel>()

    fun getMessages() = executeBackendCall(call = { repository.getMessages() })

    fun startSocket() {
        repository.onStartSocketConnection(socketConnectionLiveData, messageSeenLiveData, newMessageLiveData)
    }

    fun sendMessage(text: String) {
        if (!repository.isConnected) return
        if (userIdLiveData.value == USER_UNDEFINED) throw Exception("User id hasn't been specified!")
        val message = SendTestMessageModel().apply {
            userId = this@ChatActivityViewModel.userIdLiveData.value!!
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