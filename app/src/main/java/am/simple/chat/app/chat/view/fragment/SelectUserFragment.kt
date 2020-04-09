package am.simple.chat.app.chat.view.fragment

import am.simple.chat.R
import am.simple.chat.app.chat.view.activiy.ChatActivityViewModel
import am.simple.chat.core.constants.FIRST_USER
import am.simple.chat.core.constants.SECOND_USER
import am.simple.chat.core.view.BaseFragment
import android.view.View
import kotlinx.android.synthetic.main.select_user_fragment.*
import org.koin.androidx.viewmodel.ext.android.getViewModel

/**
 * Created by Ara Hakobyan on 4/8/2020.
 * Company IDT
 */
class SelectUserFragment : BaseFragment<SelectUserFragmentViewModel, ChatActivityViewModel>() {
    override fun onCreateView(): Int = R.layout.select_user_fragment

    override fun initActivityViewModel() {
        activityViewModel = activity?.getViewModel() ?: return
    }

    override fun initFragmentViewModel() {
        fragmentViewModel = getViewModel()
    }

    override fun setupView() = Unit

    override fun setupOnViewClicked() {
        super.setupOnViewClicked()
        firstUser.setOnClickListener(::selectUser)
        secondUser.setOnClickListener(::selectUser)
    }

    private fun selectUser(view: View) {
        activityViewModel.userIdLiveData.value = when (view.id) {
            R.id.firstUser -> FIRST_USER
            else -> SECOND_USER
        }
    }
}