package at.thomasgorke.android.composeexample

import at.thomasgorke.android.composeexample.utils.Snacker
import at.thomasgorke.android.composeexample.utils.SnackerImpl
import at.thomasgorke.android.composeexample.utils.SnackerViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val appModule = module {
    single<Snacker> { SnackerImpl() }
    viewModel { SnackerViewModel(snacker = get()) }
}