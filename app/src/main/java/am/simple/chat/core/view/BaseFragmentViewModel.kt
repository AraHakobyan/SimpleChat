package am.simple.chat.core.view

import am.simple.chat.app.ChatApplication
import android.content.Context

/**
 * Created by Ara Hakobyan on 4/5/2020.
 * Company IDT
 */
open class BaseFragmentViewModel(context: Context) : BaseViewModel(context.applicationContext as ChatApplication)