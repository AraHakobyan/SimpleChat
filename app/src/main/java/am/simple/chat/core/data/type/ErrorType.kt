package am.simple.chat.core.data.type

import androidx.annotation.StringDef

/**
 * Created by Ara Hakobyan on 4/5/2020.
 * Company IDT
 */
@StringDef
annotation class ErrorType {
    companion object {
        const val ERROR_TYPE_NO_INTERNET_CONNECTION = "ERROR_TYPE_NO_INTERNET_CONNECTION"
        const val ERROR_TYPE_FAIL_RESPONSE = "ERROR_TYPE_FAIL_RESPONSE"
        const val ERROR_TYPE_UNDEFINED = "ERROR_TYPE_UNDEFINED"
    }
}