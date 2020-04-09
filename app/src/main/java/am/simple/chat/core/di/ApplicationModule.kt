package am.simple.chat.core.di

import am.simple.chat.app.chat.data.network.ChatNetworkApi
import am.simple.chat.app.chat.data.ChatRepository
import am.simple.chat.app.chat.view.activiy.ChatActivityViewModel
import am.simple.chat.app.chat.view.fragment.ChatFragmentViewModel
import am.simple.chat.app.chat.view.fragment.SelectUserFragmentViewModel
import am.simple.chat.core.network.RetrofitClientInstance
import am.simple.chat.core.network.SocketClientInstance
import com.google.gson.Gson
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
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

    private val signalRModule = module {
        single { SocketClientInstance.hubConnectionInstance }
    }

    private val apiModule = module {

        fun provideChatNetworkApi(retrofit: Retrofit): ChatNetworkApi {
            return retrofit.create(ChatNetworkApi::class.java)
        }

        single {
            provideChatNetworkApi(get())
        }
    }

    private val repositoryModule = module {
        single {
            ChatRepository(get(), get())
        }
    }

    private val viewModelModule = module {
        viewModel {
            ChatActivityViewModel(get(),androidContext())
        }
        viewModel {
            ChatFragmentViewModel(androidContext())
        }
        viewModel {
            SelectUserFragmentViewModel(get())
        }
    }

    private val gsonModule = module {
        single {
            Gson()
        }
    }

    val modules = listOf(
        retrofitModule,
        signalRModule,
        apiModule,
        repositoryModule,
        viewModelModule,
        gsonModule
    )
}