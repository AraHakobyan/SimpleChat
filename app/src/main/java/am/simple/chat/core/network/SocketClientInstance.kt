package am.simple.chat.core.network

import am.simple.chat.core.constants.NetworkConstants
import com.microsoft.signalr.HubConnection
import com.microsoft.signalr.HubConnectionBuilder


/**
 * Created by Ara Hakobyan on 4/5/2020.
 * Company IDT
 */
object SocketClientInstance {
    private var hubConnection: HubConnection? = null
    val hubConnectionInstance: HubConnection
        get() {
            if (hubConnection == null) {
                hubConnection = HubConnectionBuilder.create(NetworkConstants.BASE_URL + "hubs/test")
                    .build()
            }
            return hubConnection!!

        }
}