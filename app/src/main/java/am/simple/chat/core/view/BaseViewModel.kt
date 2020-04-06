package am.simple.chat.core.view

import am.simple.chat.app.ChatApplication
import am.simple.chat.core.data.model.ErrorModel
import am.simple.chat.core.data.type.ErrorType
import am.simple.chat.core.data.type.ErrorType.Companion.ERROR_TYPE_UNDEFINED
import am.simple.chat.core.network.Output
import am.simple.chat.core.utils.hasInternetConnection
import kotlinx.coroutines.Dispatchers
import retrofit2.Response
import java.lang.Exception
import androidx.lifecycle.*

/**
 * Created by Ara Hakobyan on 4/5/2020.
 * Company IDT
 */
open class BaseViewModel(private val context: ChatApplication) : AndroidViewModel(context){

    fun <T : Any> executeBackendCall(call: suspend () -> Response<T>): LiveData<Output<T>> = liveData(Dispatchers.IO) {

        try {
            if (hasInternetConnection(context)) {
                val result: Output<T> = safeApiResult(call)
                emit(result)
            } else {
                emit(Output.Error(ErrorModel(ErrorType.ERROR_TYPE_NO_INTERNET_CONNECTION)))
            }
        } catch (e: Exception) {
            emit(Output.Error(ErrorModel(ErrorType.ERROR_TYPE_FAIL_RESPONSE)))
        }
    }

    private suspend fun <T : Any> safeApiResult(call: suspend () -> Response<T>): Output<T> {
        val response = call.invoke()
        if (response.isSuccessful) {
            return when (val responseBody = response.body()) {
                is T -> Output.Success(responseBody)

                else -> Output.Error(ErrorModel(ERROR_TYPE_UNDEFINED))
            }
        }
        return Output.Error(ErrorModel(ERROR_TYPE_UNDEFINED))
    }
}