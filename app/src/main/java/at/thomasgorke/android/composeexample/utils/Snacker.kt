package at.thomasgorke.android.composeexample.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


data class SnackbarContent(
    val title: String
)

interface Snacker {
    val snackEffect: SharedFlow<SnackbarContent>
    suspend fun trigger(snackbarContent: SnackbarContent)
}

class SnackerImpl : Snacker {

    private val _snackEffect = MutableSharedFlow<SnackbarContent>()

    override val snackEffect: SharedFlow<SnackbarContent>
        get() = _snackEffect

    override suspend fun trigger(snackbarContent: SnackbarContent) {
        _snackEffect.emit(snackbarContent)
    }
}

class SnackerViewModel(
    private val snacker: Snacker
) : ViewModel() {

    private val _snackEffect = MutableSharedFlow<SnackbarContent>()
    val snackEffect: SharedFlow<SnackbarContent> = _snackEffect

    init {
        viewModelScope.launch {
            snacker.snackEffect
                .onEach { _snackEffect.emit(it) }
                .launchIn(this)
        }
    }
}
