package am.simple.chat.app.chat.view

import am.simple.chat.R
import am.simple.chat.app.chat.ChatActivityViewModel
import am.simple.chat.core.view.BaseActivity
import android.os.Bundle
import org.koin.androidx.viewmodel.ext.android.getViewModel

class ChatActivity : BaseActivity<ChatActivityViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
    }

    override fun onCreateView(): Int? = R.layout.activity_chat

    override fun initActivityViewModel() {
        viewModel = getViewModel()
    }

    override fun setupView() {

    }

    override fun loadData() {

    }
}
