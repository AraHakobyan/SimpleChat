package am.simple.chat.core.data.model

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
data class ErrorModel(
    @SerializedName("code")
    val code: String,

    @SerializedName("message")
    val message: String? = null
): Parcelable