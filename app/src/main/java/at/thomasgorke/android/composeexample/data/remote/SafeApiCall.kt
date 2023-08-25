package at.thomasgorke.android.composeexample.data.remote

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun <T> safeApiCall(apiCall: suspend () -> T): NetworkResponse<T> {
    return try {
        // API calls should always be executed on an IO thread
        val result = withContext(Dispatchers.IO) {
            apiCall.invoke()
        }
        NetworkResponse.Success(result)
    } catch (e: Exception) {
        NetworkResponse.Error(e)
    }
}