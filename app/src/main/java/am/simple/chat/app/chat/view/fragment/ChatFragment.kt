package am.simple.chat.app.chat.view.fragment

import am.simple.chat.R
import am.simple.chat.app.chat.data.model.ChatModel
import am.simple.chat.app.chat.view.activiy.ChatActivityViewModel
import am.simple.chat.core.network.Output
import am.simple.chat.core.view.BaseFragment
import androidx.lifecycle.Observer
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

    }

    override fun loadData() {
        fragmentViewModel.getMessages().observe(this, Observer {
            when(it){
                is Output.Success ->showMessages(it.output.data)
                is Output.Error -> showErrorDialog(it.exception.message!!)
            }
        })
    }

    private fun showMessages(data: ChatModel) {

    }
}