package am.simple.chat.app.chat.view.fragment

import am.simple.chat.R
import am.simple.chat.app.chat.data.model.ChatItem
import am.simple.chat.app.chat.data.model.OnReceivedTestModel
import am.simple.chat.app.chat.view.activiy.ChatActivityViewModel
import am.simple.chat.app.chat.view.adapter.MessagesAdapter
import am.simple.chat.core.data.type.SocketConnectionType
import am.simple.chat.core.extensions.asString
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
        activityViewModel = activity?.getViewModel()!!
    }

    override fun initFragmentViewModel() {
        fragmentViewModel = getViewModel()
    }

    override fun initActivityObservers() {
        super.initActivityObservers()
        activityViewModel.socketConnectionLiveData.observe(this, Observer(::socketConnectionChanged))
        activityViewModel.messageSeenLiveData.observe(this, Observer(::notifyMessageSeen))
        activityViewModel.newMessageLiveData.observe(this, Observer(::notifyNewMessageReceived))
        activityViewModel.messagesLiveData.observe(this, Observer(::messagesListChanged))
    }

    private fun notifyNewMessageReceived(data: ChatItem) {
        messagesAdapter.add(data)
        updateRecyclerView()
    }

    private fun notifyMessageSeen(data: OnReceivedTestModel) {
        val lastReceivedMessage =
            activityViewModel.messagesLiveData.value?.findLast { it.id.isEmpty() }
        lastReceivedMessage?.isSeen = true
        updateRecyclerView()
    }

    private fun socketConnectionChanged(connectionState: Int) {
        when (connectionState) {
            SocketConnectionType.CONNECTED -> btnSendMessage.visibility = View.VISIBLE
            SocketConnectionType.UNDEFINED -> btnSendMessage.visibility = View.GONE
        }
    }

    private fun messagesListChanged(messages: ArrayList<ChatItem>) {
        messagesAdapter.items = messages
        updateRecyclerView()
    }

    override fun setupView() {
        initRecyclerView()
    }

    private fun initRecyclerView() {
        messagesAdapter = MessagesAdapter(userId = activityViewModel.userIdLiveData.value!!)
        messagesRV.run {
            layoutManager = LinearLayoutManager(this@ChatFragment.context)
            setHasFixedSize(true)
            adapter = messagesAdapter
        }
    }

    private fun updateRecyclerView() {
        messagesAdapter.notifyDataSetChanged()
        messagesAdapter.items?.size?.let {
            messagesRV.scrollToPosition( it - 1)
        }
    }

    override fun setupOnViewClicked() {
        btnSendMessage.setOnClickListener(::sendMessage)
    }

    private fun sendMessage(view: View? = null) {
        activityViewModel.sendMessage(editMessage.asString() ?: return)
    }
}