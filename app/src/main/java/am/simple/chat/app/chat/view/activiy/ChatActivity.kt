package am.simple.chat.app.chat.view.activiy

import am.simple.chat.R
import am.simple.chat.app.chat.view.activiy.ChatActivityViewModel
import am.simple.chat.core.view.BaseActivity
import android.os.Bundle
import org.koin.androidx.viewmodel.ext.android.getViewModel

class ChatActivity : BaseActivity<ChatActivityViewModel>() {

    override fun onCreateView(): Int = R.layout.activity_chat

    override fun initActivityViewModel() {
        viewModel = getViewModel()
    }

    override fun setupView() {

    }
}
