package am.simple.chat.app.chat.view.fragment

import am.simple.chat.R
import am.simple.chat.app.chat.view.activiy.ChatActivityViewModel
import am.simple.chat.core.network.Output
import am.simple.chat.core.view.BaseFragment
import android.view.View
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_chat.*
import org.koin.androidx.viewmodel.ext.android.getViewModel

/**
 * Created by Ara Hakobyan on 4/4/2020.
 * Company IDT
 */
class ChatFragment : BaseFragment<ChatFragmentViewModel, ChatActivityViewModel>() {
    override fun onCreateView(): Int = R.layout.fragment_chat

    override fun initActivityViewModel() {
        activityViewModel = getViewModel()
    }

    override fun initFragmentViewModel() {
        fragmentViewModel = getViewModel()
    }

    override fun setupView() {
        getMessages()
    }

    override fun setupOnViewClicked() {
        btnSendMessage.setOnClickListener(::sendMessage)
    }

    private fun getMessages() {
        fragmentViewModel.getMessages().observe(this, Observer {
            when(it){
                is Output.Success ->showMessages(it)
                is Output.Error -> showErrorDialog(it.exception.message!!)
            }
        })
    }

    private fun sendMessage(view: View? = null) {
//        fragmentViewModel.startSocket()

    }

    private fun showMessages(data: Output<Any>) {

    }
}