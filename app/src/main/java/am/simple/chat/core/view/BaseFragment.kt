package am.simple.chat.core.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

/**
 * Created by Ara Hakobyan on 4/4/2020.
 * Company IDT
 */
abstract class BaseFragment<F : BaseFragmentViewModel, A : BaseActivityViewModel> : Fragment() {

    lateinit var fragmentViewModel: F

    lateinit var activityViewModel: A

    private var fragmentView: View? = null

    private var fragmentWasCreated: Boolean = false

    val baseActivity: BaseActivity<*>
        get() = requireActivity() as BaseActivity<*>

    override fun onAttach(context: Context) {
        super.onAttach(context)
        initFragmentViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initActivityViewModel()
        initActivityObservers()
        loadIntentExtras()
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
            loadData()
            setupView()
            setupOnViewClicked()
        }
        fragmentWasCreated = true
        fragmentView.run { }
    }

    @LayoutRes
    abstract fun onCreateView(): Int

    abstract fun initActivityViewModel()

    abstract fun initFragmentViewModel()

    abstract fun setupView()

    open fun setupOnViewClicked() = Unit

    open fun loadIntentExtras() = Unit

    open fun loadData() = Unit

    open fun initActivityObservers() = Unit

    open fun initFragmentObservers() = Unit
}