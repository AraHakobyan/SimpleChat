package am.simple.chat.app.chat.view.fragment

import am.simple.chat.R
import am.simple.chat.app.chat.data.model.ChatItem
import am.simple.chat.app.chat.view.activiy.ChatActivityViewModel
import am.simple.chat.app.chat.view.adapter.MessagesAdapter
import am.simple.chat.core.data.type.SocketConnectionType
import am.simple.chat.core.extensions.asString
import am.simple.chat.core.network.Output
import am.simple.chat.core.view.BaseFragment
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_chat.*
import org.koin.androidx.viewmodel.ext.android.getViewModel

/**
 * Created by Ara Hakobyan on 4/4/2020.
 * Company IDT
 */
class ChatFragment : BaseFragment<ChatFragmentViewModel, ChatActivityViewModel>() {

    private lateinit var messagesAdapter: MessagesAdapter
    override fun onCreateView(): Int = R.layout.fragment_chat

    override fun initActivityViewModel() {
        activityViewModel = getViewModel()
    }

    override fun initFragmentViewModel() {
        fragmentViewModel = getViewModel()
    }

    override fun initFragmentObservers() {
        super.initFragmentObservers()
        fragmentViewModel.socketConnectionLiveData.observe(this, Observer {
            when(it) {
                SocketConnectionType.CONNECTED -> btnSendMessage.visibility = View.VISIBLE
                SocketConnectionType.UNDEFINED -> fragmentViewModel.startSocket()
            }
        })

        fragmentViewModel.messagesLiveData.observe(this, Observer {
            messagesAdapter.run {
                items = it
                notifyDataSetChanged()
            }
        })
    }

    override fun setupView() {
        getMessages()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        messagesAdapter = MessagesAdapter()
        messagesRV.run {
            layoutManager = LinearLayoutManager(this@ChatFragment.context)
            setHasFixedSize(true)
            adapter = messagesAdapter
        }
    }

    override fun setupOnViewClicked() {
        btnSendMessage.setOnClickListener(::sendMessage)
    }

    private fun getMessages() {
        fragmentViewModel.getMessages().observe(this, Observer {
            when(it){
                is Output.Success ->showMessages(it.output.data.items)
                is Output.Error -> fragmentViewModel.errorLiveData.value = it.errorModel
            }
        })
    }

    private fun sendMessage(view: View? = null) {
        fragmentViewModel.sendMessage(editMessage.asString() ?: return)
    }

    private fun showMessages(data: ArrayList<ChatItem>) {
        fragmentViewModel.messagesLiveData.value = data
    }

    override fun initSocketConnection() {
        fragmentViewModel.startSocket()
    }
}