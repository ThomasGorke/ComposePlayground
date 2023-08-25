package at.thomasgorke.android.composeexample.data.local

import androidx.room.Room
import at.thomasgorke.android.composeexample.data.local.db.ContactDataSource
import at.thomasgorke.android.composeexample.data.local.db.ContactDataSourceImpl
import at.thomasgorke.android.composeexample.data.local.db.DemoAppDatabase
import at.thomasgorke.android.composeexample.data.local.prefs.DemoPreferences
import at.thomasgorke.android.composeexample.data.local.prefs.DemoPreferencesImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

internal val localDataModule = module {
    single<DemoPreferences> { DemoPreferencesImpl(context = get(), gson = get()) }

    single {
        Room.databaseBuilder(
            androidContext(),
            DemoAppDatabase::class.java,
            "demo-app-db"
        ).build()
    }

    single<ContactDataSource> { ContactDataSourceImpl(contactDao = get<DemoAppDatabase>().contactDao()) }
}