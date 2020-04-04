package am.simple.chat.core.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * Created by Ara Hakobyan on 4/4/2020.
 * Company IDT
 */
abstract class BaseFragment<F : ViewModel, A : ViewModel> : Fragment() {

    lateinit var fragmentViewModel: F

    lateinit var activityViewModel: A

    private var fragmentView: View? = null

    private var fragmentWasCreated: Boolean = false

    private var isVisibleToUser: Boolean = true

    val baseActivity: BaseActivity<*>
        get() = requireActivity() as BaseActivity<*>

    override fun onAttach(context: Context) {
        super.onAttach(context)
        initFragmentViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadIntentExtras()
        initActivityViewModel()
        initActivityObservers()
        initFragmentObservers()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (fragmentView == null) {
            fragmentView = inflater.inflate(onCreateView(), null, false)
        }
        return fragmentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (fragmentWasCreated.not()) {
            GlobalScope.launch(Dispatchers.IO) { loadData() }
            setupView()
            setupOnViewClicked()
        }
        fragmentWasCreated = true
        fragmentView.run {  }
    }

    override fun setUserVisibleHint(isVisible: Boolean) {
        super.setUserVisibleHint(isVisible)
        isVisibleToUser = isVisible
    }

    @LayoutRes
    abstract fun onCreateView(): Int

    abstract fun initActivityViewModel()

    abstract fun initFragmentViewModel()

    abstract fun setupView()

    open fun setupOnViewClicked() = Unit

    open fun loadIntentExtras() = Unit

    open suspend fun loadData() = Unit

    open fun initActivityObservers() = Unit

    open fun initFragmentObservers() = Unit
}