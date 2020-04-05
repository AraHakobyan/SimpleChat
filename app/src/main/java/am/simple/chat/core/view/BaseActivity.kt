package am.simple.chat.core.view

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel

/**
 * Created by Ara Hakobyan on 4/4/2020.
 * Company IDT
 */
abstract class BaseActivity<ACTIVITY_VIEW_MODEL : ViewModel> : AppCompatActivity() {

    protected lateinit var viewModel: ACTIVITY_VIEW_MODEL

    override fun onCreate(savedInstanceState: Bundle?) {
        initActivityViewModel()
        initObservers()
        super.onCreate(savedInstanceState)
        loadIntentExtras()
        loadData()
        onCreateView()?.let {
            setContentView(it)
            setupView()
        }
    }

    @LayoutRes
    abstract fun onCreateView(): Int?

    abstract fun initActivityViewModel()

    abstract fun setupView()

    open fun initObservers() = Unit

    open fun loadIntentExtras() = Unit

    open fun loadData() = Unit
}