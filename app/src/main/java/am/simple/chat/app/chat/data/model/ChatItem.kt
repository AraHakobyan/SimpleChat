package am.simple.chat.app.chat.data.model

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by Ara Hakobyan on 4/5/2020.
 * Company IDT
 */
@Keep
@Parcelize
data class ChatItem(
    @SerializedName("id")
    val id: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("userId")
    val userId: Int
): Parcelable