package am.simple.chat.app.chat.data.network

import am.simple.chat.app.chat.data.model.ChatModel
import am.simple.chat.core.data.model.ResponseModel
import am.simple.chat.core.constants.NetworkConstants
import retrofit2.Response
import retrofit2.http.GET

/**
 * Created by Ara Hakobyan on 4/4/2020.
 * Company IDT
 */
interface ChatNetworkApi {
    @GET(NetworkConstants.GET_MESSAGES)
    suspend fun getMessages(): Response<ResponseModel<ChatModel>>

}