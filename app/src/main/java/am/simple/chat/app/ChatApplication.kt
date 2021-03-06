package am.simple.chat.app

import am.simple.chat.core.di.ApplicationModule
import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/**
 * Created by Ara Hakobyan on 4/4/2020.
 * Company IDT
 */
class ChatApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidLogger()
            androidContext(this@ChatApplication)
            modules(ApplicationModule.modules)
        }
    }
}