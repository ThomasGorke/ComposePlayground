package at.thomasgorke.android.composeexample.ui.data.networking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.thomasgorke.android.composeexample.data.Repository
import at.thomasgorke.android.composeexample.data.RepositoryResponse
import at.thomasgorke.android.composeexample.utils.SnackbarContent
import at.thomasgorke.android.composeexample.utils.Snacker
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DataRetrofitScreenViewModel(
    private val repository: Repository,
    private val snacker: Snacker
) : ViewModel() {

    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()

    init {
        loadData()
    }

    fun reload() {
        loadData()
    }

    fun loadImageOfBreed(name: String) {
        viewModelScope.launch {
            val adaptedName = name.lowercase().replace(" ", "-")
            repository.getBreedImageUrl(adaptedName).let { response ->
                println(response)
                when (response) {
                    is RepositoryResponse.Success -> _state.update {
                        it.copy(imageUrl = response.data, hasError = false)
                    }

                    else -> {
                        // Maybe show snackbar if no image
                        snacker.trigger(SnackbarContent("No image found for $name"))
                        _state.update { it.copy(imageUrl = "", hasError = false) }
                    }
                }
            }
        }
    }

    fun closeDialog() {
        _state.update { it.copy(imageUrl = "") }
    }

    private fun loadData() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, hasError = false, imageUrl = "") }

            // Hint: it is not necessary to wrap the call in an async block as there is only this one call. but if you want to execute more parallel calls this is the way to go
            val breedRequest = async {
                delay(300) // should not be done in real apps
                repository.getAllBreeds()
            }

            breedRequest.await().let { response ->
                when (response) {
                    is RepositoryResponse.Success -> _state.update {
                        it.copy(breeds = response.data, isLoading = false, hasError = false)
                    }

                    else -> _state.update { State(hasError = true) }
                }
            }
        }
    }

    data class State(
        val breeds: List<String> = emptyList(),
        val isLoadingImage: Boolean = false,
        val imageUrl: String = "",
        val isLoading: Boolean = true,
        val hasError: Boolean = false
    )
}