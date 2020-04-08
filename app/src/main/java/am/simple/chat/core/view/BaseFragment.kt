package am.simple.chat.core.view

import am.simple.chat.R
import am.simple.chat.core.data.type.ErrorType
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

/**
 * Created by Ara Hakobyan on 4/4/2020.
 * Company IDT
 */
abstract class BaseFragment<F : BaseFragmentViewModel, A : BaseActivityViewModel> : Fragment() {

    lateinit var fragmentViewModel: F

    lateinit var activityViewModel: A

    private var fragmentView: View? = null

    private var fragmentWasCreated: Boolean = false

    private lateinit var dialogBuilder: AlertDialog.Builder
    private lateinit var alertDialog: AlertDialog

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

    private fun showErrorDialog(
        errorMsgInfo: String? = resources.getString(R.string.something_went_wrong),
        posBtnText: String = resources.getString(R.string.ok)
    ) {
        dialogBuilder = AlertDialog.Builder(this.context!!)
            .setMessage(errorMsgInfo ?: resources.getString(R.string.something_went_wrong))
            .setCancelable(false)
            .setPositiveButton(posBtnText) { _: DialogInterface?, _: Int ->
                alertDialog.dismiss()
            }
        alertDialog = dialogBuilder.create()
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
        activityViewModel.errorLiveData.observe(this, Observer {
            showErrorDialog(errorMsgInfo = when(it.code){
                ErrorType.ERROR_TYPE_NO_INTERNET_CONNECTION -> getString(R.string.no_internet_connection)
                else -> it.message
            })
        })
    }
}