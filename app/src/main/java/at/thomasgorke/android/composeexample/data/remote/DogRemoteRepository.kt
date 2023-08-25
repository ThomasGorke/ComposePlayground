package at.thomasgorke.android.composeexample.data.remote

import at.thomasgorke.android.composeexample.utils.toSnakeCase

interface DogRemoteRepository {
    suspend fun getAllBreeds(): NetworkResponse<List<String>>
    suspend fun getBreedImageUrl(breed: String): NetworkResponse<String>
}

class DogRemoteRepositoryImpl(
    private val dogApi: DogApi
) : DogRemoteRepository {

    override suspend fun getAllBreeds(): NetworkResponse<List<String>> =
        safeApiCall { dogApi.getAllBreeds() }.let { response ->
            when (response) {
                is NetworkResponse.Error -> response
                is NetworkResponse.Success -> response.data.message.let { it ->
                    val breedList = mutableListOf<String>()

                    it.forEach { (group, subs) ->
                        when (subs.isEmpty()) {
                            true -> breedList += group.toSnakeCase()
                            false -> subs.forEach { sub ->
                                breedList += "${sub.toSnakeCase() } ${group.toSnakeCase()}"
                            }
                        }
                    }

                    breedList.sorted().wrap()
                }
            }
        }

    override suspend fun getBreedImageUrl(breed: String): NetworkResponse<String> =
        safeApiCall { dogApi.getBreedImageUrl(breed = breed) }.let { response ->
            when (response) {
                is NetworkResponse.Error -> response
                is NetworkResponse.Success -> response.data.message.replace("\\", "").wrap()
            }
        }

    private fun <T> T.wrap(): NetworkResponse.Success<T> = NetworkResponse.Success(data = this)
}