package am.simple.chat.app.chat.data.model

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by Ara Hakobyan on 4/4/2020.
 * Company IDT
 */
@Keep
@Parcelize
data class ChatModel(
    @SerializedName("items")
    val items: List<ChatItem>,

    @SerializedName("count")
    val count: Int
): Parcelable