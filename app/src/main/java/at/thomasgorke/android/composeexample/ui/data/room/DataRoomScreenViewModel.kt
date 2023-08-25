package at.thomasgorke.android.composeexample.ui.data.room

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.thomasgorke.android.composeexample.data.Repository
import at.thomasgorke.android.composeexample.data.RepositoryResponse
import at.thomasgorke.android.composeexample.data.model.Contact
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DataRoomScreenViewModel(
    private val repository: Repository
) : ViewModel() {

    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()

    init {
        observeContacts()
    }

    fun insertNewContact() {
        viewModelScope.launch {
            repository.createNewContact()
        }
    }

    private fun observeContacts() {
        viewModelScope.launch {
            repository.getAllContactsFlow()
                .onEach { result ->
                    when (result) {
                        is RepositoryResponse.Success -> _state.update { it.copy(contacts = result.data) }
                        else -> _state.update { it.copy(contacts = emptyList()) }
                    }
                }
                .launchIn(this)
        }
    }

    data class State(
        val contacts: List<Contact> = emptyList()
    )
}