package at.thomasgorke.android.composeexample.data.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

internal val remoteDataModule = module {
    factory { HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY } }
    single { provideOkHttpClient(loggingInterceptor = get()) }
    single { provideDogApi(okHttpClient = get()) }

    single<DogRemoteRepository> { DogRemoteRepositoryImpl(dogApi = get()) }
}

private fun provideOkHttpClient(
    loggingInterceptor: HttpLoggingInterceptor
): OkHttpClient = OkHttpClient.Builder()
    .addInterceptor(loggingInterceptor)
    .build()

private fun provideDogApi(
    okHttpClient: OkHttpClient
): DogApi = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .client(okHttpClient)
    .build()
    .create(DogApi::class.java)

private const val BASE_URL = "https://dog.ceo/api/"