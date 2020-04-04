package am.simple.chat.core.di

import am.simple.chat.app.chat.data.ChatNetworkApi
import am.simple.chat.app.chat.data.ChatRepository
import am.simple.chat.core.network.RetrofitClientInstance
import com.google.gson.Gson
import org.koin.dsl.module
import retrofit2.Retrofit

/**
 * Created by Ara Hakobyan on 4/4/2020.
 * Company IDT
 */
object ApplicationModule {

    private val retrofitModule = module {
        single { RetrofitClientInstance.retrofitInstance }
    }

    private val apiModule = module {
        factory {
            val retrofitClientInstance: Retrofit = get()
            retrofitClientInstance.create(ChatNetworkApi::class.java)
        }
    }

    private val repositoryModule = module {
        single {
            ChatRepository( get())
        }
    }

    private val viewModelModule = module {

    }

    private val gsonModule = module {
        single {
            Gson()
        }
    }

    val modules = listOf(retrofitModule, apiModule, repositoryModule, viewModelModule, gsonModule)
}