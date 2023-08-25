package at.thomasgorke.android.composeexample.data.remote

import at.thomasgorke.android.composeexample.data.remote.model.AllBreedsResponse
import at.thomasgorke.android.composeexample.data.remote.model.BreedImageResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface DogApi {
    @GET("breeds/list/all")
    suspend fun getAllBreeds(): AllBreedsResponse

    @GET("breed/{breed}/images/random")
    suspend fun getBreedImageUrl(@Path("breed") breed: String): BreedImageResponse
}