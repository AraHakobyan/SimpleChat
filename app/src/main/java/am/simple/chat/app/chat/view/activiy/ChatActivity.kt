package am.simple.chat.app.chat.view.activiy

import am.simple.chat.R
import am.simple.chat.core.constants.USER_UNDEFINED
import am.simple.chat.core.data.type.SocketConnectionType
import am.simple.chat.core.network.Output
import am.simple.chat.core.view.BaseActivity
import androidx.lifecycle.Observer
import org.koin.androidx.viewmodel.ext.android.getViewModel

class ChatActivity : BaseActivity<ChatActivityViewModel>() {

    override fun onCreateView(): Int = R.layout.activity_chat

    override fun initActivityViewModel() {
        viewModel = getViewModel()
    }

    override fun initObservers() {
        super.initObservers()
        viewModel.userIdLiveData.observe(this, Observer(::userSelected))
        viewModel.socketConnectionLiveData.observe(this, Observer (::socketConnectionChanged))
    }

    private fun socketConnectionChanged(connectionState: Int) {
        when(connectionState) {
            SocketConnectionType.CONNECTED -> Unit
            SocketConnectionType.UNDEFINED -> viewModel.startSocket()
        }
    }

    private fun userSelected(userId: Int) {
        if (userId != USER_UNDEFINED){
            viewModel.getMessages().observe(this, Observer {
                when(it){
                    is Output.Success ->viewModel.messagesLiveData.value = it.output.data.items
                    is Output.Error -> viewModel.errorLiveData.value = it.errorModel
                }
            })
            navigate(R.id.toChatFragment)
        }
    }

    override fun setupView() = Unit

    override fun initSocketConnection() {
        viewModel.startSocket()
    }
}
