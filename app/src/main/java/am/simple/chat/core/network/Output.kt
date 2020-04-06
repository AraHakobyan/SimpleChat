package am.simple.chat.core.network

import am.simple.chat.core.data.model.ErrorModel
import androidx.annotation.Keep

/**
 * Created by Ara Hakobyan on 4/5/2020.
 * Company IDT
 */
@Keep
sealed class Output<out T : Any> {
    @Keep
    data class Success<out T : Any>(val output: T) : Output<T>()

    @Keep
    data class Error(val errorModel: ErrorModel) : Output<Nothing>()
}