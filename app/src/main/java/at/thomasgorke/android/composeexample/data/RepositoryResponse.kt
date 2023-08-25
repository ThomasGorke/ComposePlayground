package at.thomasgorke.android.composeexample.data

sealed class RepositoryResponse<out T> {
    data class Success<T>(val data: T) : RepositoryResponse<T>()
    data class NetworkError(val error: Exception): RepositoryResponse<Nothing>()
    data class LocalError(val error: Exception): RepositoryResponse<Nothing>()
}