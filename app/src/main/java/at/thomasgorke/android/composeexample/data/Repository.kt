package at.thomasgorke.android.composeexample.data

import at.thomasgorke.android.composeexample.data.local.db.ContactDataSource
import at.thomasgorke.android.composeexample.data.local.db.entity.ContactEntity
import at.thomasgorke.android.composeexample.data.local.model.RgbColor
import at.thomasgorke.android.composeexample.data.local.prefs.DemoPreferences
import at.thomasgorke.android.composeexample.data.model.Contact
import at.thomasgorke.android.composeexample.data.remote.DogRemoteRepository
import at.thomasgorke.android.composeexample.data.remote.NetworkResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.lastOrNull
import kotlinx.coroutines.flow.map

interface Repository {
    fun getRgbColorFlow(): Flow<RepositoryResponse<RgbColor>>
    suspend fun updateRgb(rgbColor: RgbColor)

    suspend fun getAllBreeds(): RepositoryResponse<List<String>>
    suspend fun getBreedImageUrl(breedName: String): RepositoryResponse<String>

    fun getAllContactsFlow(): Flow<RepositoryResponse<List<Contact>>>
    suspend fun createNewContact()
}

class RepositoryImpl(
    private val contactDataSource: ContactDataSource,
    private val demoPreferences: DemoPreferences,
    private val dogRemoteRepository: DogRemoteRepository
) : Repository {

    override fun getRgbColorFlow(): Flow<RepositoryResponse<RgbColor>> =
        demoPreferences.getRgbColorFlow().map { it.wrapSuccess() }

    override suspend fun updateRgb(rgbColor: RgbColor) {
        demoPreferences.setRgb(rgbColor)
    }

    override suspend fun getAllBreeds(): RepositoryResponse<List<String>> =
        dogRemoteRepository.getAllBreeds().convert()

    override suspend fun getBreedImageUrl(breedName: String): RepositoryResponse<String> =
        dogRemoteRepository.getBreedImageUrl(breed = breedName).convert()

    override suspend fun createNewContact() = contactDataSource.getAllContacts().let { result ->
            result.lastOrNull()?.let {
                contactDataSource.createNewContact(createContactEntityWithId(it.id + 1))
            } ?: contactDataSource.createNewContact(createContactEntityWithId(0))
        }

    override fun getAllContactsFlow(): Flow<RepositoryResponse<List<Contact>>> =
        contactDataSource
            .getAllContactsFlow()
            .map { list ->
                list.map { item ->
                    Contact(
                        id = item.id,
                        name = "${item.firstName} ${item.lastName}",
                        email = item.email
                    )
                }.wrapSuccess()
            }

    private fun createContactEntityWithId(id: Long) = ContactEntity(
        id = 0,
        firstName = "Firstname $id",
        lastName = "Lastname $id",
        email = "firstname$id.lastname$id@demo.at"
    )

    private fun <T> NetworkResponse<T>.convert(): RepositoryResponse<T> = when(this) {
        is NetworkResponse.Error -> RepositoryResponse.NetworkError(this.error)
        is NetworkResponse.Success -> RepositoryResponse.Success(this.data)
    }

    private fun <T> T.wrapSuccess(): RepositoryResponse<T> = RepositoryResponse.Success(this)
}