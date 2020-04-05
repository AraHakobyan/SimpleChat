package am.simple.chat.core.data.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

/**
 * Created by Ara Hakobyan on 4/4/2020.
 * Company IDT
 */
@Keep
data class ResponseModel<T>(
    @SerializedName("data")
    val data: T,

    @SerializedName("error")
    val error: ErrorModel
)