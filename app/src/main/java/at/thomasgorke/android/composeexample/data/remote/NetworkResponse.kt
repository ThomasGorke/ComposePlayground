package at.thomasgorke.android.composeexample.data.remote

sealed class NetworkResponse<out T> {
    data class Success<T>(val data: T) : NetworkResponse<T>()
    data class Error(val error: Exception) : NetworkResponse<Nothing>()
}
