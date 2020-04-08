package am.simple.chat.app.chat.data.model

import androidx.annotation.IntDef

/**
 * Created by Ara Hakobyan on 4/8/2020.
 * Company IDT
 */
@IntDef
annotation class ChatUiType {
    companion object {
        const val CHAT_TYPE_BY_ME = 1
        const val CHAT_TYPE_OTHER = 2
    }
}