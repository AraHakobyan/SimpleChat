package am.simple.chat.core.constants

/**
 * Created by Ara Hakobyan on 4/4/2020.
 * Company IDT
 */
class NetworkConstants {
    companion object {
        const val BASE_URL = "https://api.grupy.app/"
        const val GET_MESSAGES = "chat-rooms/messages"

        const val SOCKET_SEND_MESSAGE = "OnMessage"
        const val SOCKET_CONNECT = "OnConnected"
        const val SOCKET_RECEIVE_MESSAGE_FROM_OTHER_USER = "OnMessage"
        const val SOCKET_NOTIFY_MESSAGE_RECEIVED = "OnReceived"
        const val SOCKET_CLIENT_RECEIVE_THE_MESSAGE = "OnReceived"
    }
}