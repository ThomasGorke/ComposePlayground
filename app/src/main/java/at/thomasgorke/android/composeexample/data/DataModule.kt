package at.thomasgorke.android.composeexample.data

import at.thomasgorke.android.composeexample.data.local.localDataModule
import at.thomasgorke.android.composeexample.data.remote.remoteDataModule
import com.google.gson.Gson
import org.koin.dsl.module

internal val repoModule = module {
    single { Gson() }
    single<Repository> {
        RepositoryImpl(
            contactDataSource = get(),
            demoPreferences = get(),
            dogRemoteRepository = get()
        )
    }
}

internal val dataModule = repoModule + localDataModule + remoteDataModule