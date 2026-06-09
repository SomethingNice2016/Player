package ua.kucher.player.android

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import ua.kucher.player.data.dataModule
import ua.kucher.player.local.localAndroidModule
import ua.kucher.player.local.localModule
import ua.kucher.player.viewModelModule

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(applicationContext)
            modules(
                localAndroidModule,
                localModule,
                dataModule,
                viewModelModule
            )
        }
    }
}