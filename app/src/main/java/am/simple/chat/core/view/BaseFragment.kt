package am.simple.chat.core.view

import am.simple.chat.R
import am.simple.chat.core.utils.EMPTY
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel

/**
 * Created by Ara Hakobyan on 4/4/2020.
 * Company IDT
 */
abstract class BaseFragment<F : BaseFragmentViewModel, A : ViewModel> : Fragment() {

    lateinit var fragmentViewModel: F

    lateinit var activityViewModel: A

    private var fragmentView: View? = null

    private var fragmentWasCreated: Boolean = false

    lateinit var dialogBuilder: AlertDialog.Builder
    lateinit var alertDialog: AlertDialog

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
        initSocketConnection()
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

    private fun showErrorDialog(
        onPosBtnClicked: (() -> Unit)? = null,
        onNegButtonClicked: (() -> Unit)? = null,
        errorMsgInfo: String? = resources.getString(R.string.something_whent_wrong),
        posBtnText: String = resources.getString(R.string.ok),
        negBtnText: String = EMPTY,
        isCancelable: Boolean = false
    ) {
        dialogBuilder
            .setMessage(errorMsgInfo ?: resources.getString(R.string.something_whent_wrong))
            .setCancelable(isCancelable)
        onNegButtonClicked?.let {
            dialogBuilder.setNegativeButton(negBtnText) { _: DialogInterface?, _: Int ->
                onNegButtonClicked.invoke()
                alertDialog.dismiss()
            }
        }
        dialogBuilder.setPositiveButton(posBtnText) { _: DialogInterface?, _: Int ->
            onPosBtnClicked?.invoke()
            alertDialog.dismiss()
        }
        dialogBuilder.show()
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

    open fun initFragmentObservers() {
        fragmentViewModel.errorLiveData.observe(this, Observer {
            showErrorDialog(errorMsgInfo = it?.message)
        })
    }

    abstract fun initSocketConnection()
}