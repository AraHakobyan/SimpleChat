package am.simple.chat.core.network

import am.simple.chat.core.constants.NetworkConstants.Companion.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by Ara Hakobyan on 4/4/2020.
 * Company IDT
 */
object RetrofitClientInstance {

    private var retrofit: Retrofit? = null

    private var TIMEOUT_MINUTES: Long = 30

    private val client: OkHttpClient =
        OkHttpClient.Builder()
            .readTimeout(TIMEOUT_MINUTES, TimeUnit.SECONDS)
            .connectTimeout(TIMEOUT_MINUTES, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_MINUTES, TimeUnit.SECONDS)
            .build()

    val retrofitInstance: Retrofit
        get() {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit!!
        }
}