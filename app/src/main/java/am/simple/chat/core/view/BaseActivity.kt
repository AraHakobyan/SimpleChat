package am.simple.chat.core.view

import am.simple.chat.R
import am.simple.chat.core.utils.hasInternetConnection
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by Ara Hakobyan on 4/4/2020.
 * Company IDT
 */
abstract class BaseActivity<ACTIVITY_VIEW_MODEL : BaseActivityViewModel> : AppCompatActivity() {

    protected lateinit var viewModel: ACTIVITY_VIEW_MODEL

    override fun onCreate(savedInstanceState: Bundle?) {
        initActivityViewModel()
        initObservers()
        super.onCreate(savedInstanceState)
        loadIntentExtras()
        loadData()
        (viewModel.viewModelScope.launch(Dispatchers.IO) {
            if (hasInternetConnection(this@BaseActivity)) {
                initSocketConnection()
            }
        })
        setContentView(onCreateView())
        setupView()
    }

    fun navigate(id: Int, bundle: Bundle? = null, extras: FragmentNavigator.Extras? = null) {
        if (findNavController(R.id.nav_fragment).currentDestination?.id == id) return
        findNavController(R.id.nav_fragment).navigate(id, bundle, null, extras)
    }

    @LayoutRes
    abstract fun onCreateView(): Int

    abstract fun initActivityViewModel()

    abstract fun setupView()

    open fun initObservers() = Unit

    open fun loadIntentExtras() = Unit

    open fun loadData() = Unit

    open fun initSocketConnection() = Unit
}