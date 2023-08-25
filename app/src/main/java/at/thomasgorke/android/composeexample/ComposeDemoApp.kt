package at.thomasgorke.android.composeexample

import android.app.Application
import at.thomasgorke.android.composeexample.data.dataModule
import at.thomasgorke.android.composeexample.ui.uiModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class ComposeDemoApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(applicationContext)
            modules(appModule + dataModule + uiModules)
        }
    }
}