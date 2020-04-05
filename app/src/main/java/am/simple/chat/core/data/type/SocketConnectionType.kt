package am.simple.chat.core.data.type

import am.simple.chat.core.data.type.SocketConnectionType.Companion.CONNECTED
import am.simple.chat.core.data.type.SocketConnectionType.Companion.CONNECTING
import am.simple.chat.core.data.type.SocketConnectionType.Companion.DISCONNECTED
import am.simple.chat.core.data.type.SocketConnectionType.Companion.UNDEFINED
import androidx.annotation.IntDef

/**
 * Created by Ara Hakobyan on 4/6/2020.
 * Company IDT
 */
@Target(AnnotationTarget.TYPE)
@IntDef(UNDEFINED, CONNECTING, CONNECTED, DISCONNECTED)
annotation class SocketConnectionType {
    companion object {
        const val UNDEFINED = -1
        const val CONNECTING = 0
        const val CONNECTED = 1
        const val DISCONNECTED = 2
    }
}