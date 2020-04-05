package am.simple.chat.app.chat.view.fragment

import am.simple.chat.app.chat.data.ChatRepository
import am.simple.chat.core.view.BaseFragmentViewModel
import android.content.Context

/**
 * Created by Ara Hakobyan on 4/4/2020.
 * Company IDT
 */
class ChatFragmentViewModel(private val repository: ChatRepository,context: Context) : BaseFragmentViewModel(context) {

    fun getMessages() = executeBackendCall(call = { repository.getMessages() })

}