package am.simple.chat.app.chat.data

import am.simple.chat.app.chat.data.network.ChatNetworkApi

/**
 * Created by Ara Hakobyan on 4/4/2020.
 * Company IDT
 */
class ChatRepository(private val chatApi: ChatNetworkApi) {

    suspend fun getMessages() = chatApi.getMessages()

}