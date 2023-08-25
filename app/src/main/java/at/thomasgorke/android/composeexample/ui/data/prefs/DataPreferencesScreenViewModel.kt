package at.thomasgorke.android.composeexample.ui.data.prefs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.thomasgorke.android.composeexample.data.Repository
import at.thomasgorke.android.composeexample.data.RepositoryResponse
import at.thomasgorke.android.composeexample.data.local.model.RgbColor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DataPreferencesScreenViewModel(
    private val repository: Repository
) : ViewModel() {

    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getRgbColorFlow()
                .onEach { response ->
                    if (response is RepositoryResponse.Success) {
                        _state.update {
                            it.copy(
                                red = response.data.r,
                                green = response.data.g,
                                blue = response.data.b
                            )
                        }
                    }
                }
                .launchIn(this)
        }
    }

    fun updateRed(newValue: Int) {
        viewModelScope.launch {
            repository.updateRgb(
                state.value.copy(red = newValue).toRgbColor()
            )
        }
    }

    fun updateGreen(newValue: Int) {
        viewModelScope.launch {
            repository.updateRgb(
                state.value.copy(green = newValue).toRgbColor()
            )
        }
    }

    fun updateBlue(newValue: Int) {
        viewModelScope.launch {
            repository.updateRgb(
                state.value.copy(blue = newValue).toRgbColor()
            )
        }
    }

    data class State(
        val red: Int = 0,
        val green: Int = 0,
        val blue: Int = 0
    )

    private fun State.toRgbColor() = RgbColor(r = red, g = green, b = blue)
}