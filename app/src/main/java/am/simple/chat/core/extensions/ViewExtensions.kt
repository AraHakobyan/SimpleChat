package am.simple.chat.core.extensions

import androidx.appcompat.widget.AppCompatEditText

/**
 * Created by Ara Hakobyan on 4/6/2020.
 * Company IDT
 */
fun AppCompatEditText?.asString(): String? {
    return this?.text?.toString()
}