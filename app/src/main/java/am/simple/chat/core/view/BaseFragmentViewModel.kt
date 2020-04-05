package am.simple.chat.core.view

import am.simple.chat.app.ChatApplication
import am.simple.chat.core.data.model.ErrorModel
import android.content.Context
import androidx.lifecycle.MutableLiveData

/**
 * Created by Ara Hakobyan on 4/5/2020.
 * Company IDT
 */
open class BaseFragmentViewModel(context: Context) : BaseViewModel(context.applicationContext as ChatApplication) {
    val errorLiveData = MutableLiveData<ErrorModel>()
}