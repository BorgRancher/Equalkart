
package tech.borgranch.equalkart.data.remote

import kotlinx.coroutines.delay
import retrofit2.Response
import java.io.IOException
import kotlin.math.pow

open class BaseApiClient {
    companion object {
        private const val MAX_RETRIES = 5
        private const val MAX_BACKOFF_MS = 10000L
        private const val INITIAL_BACKOFF_MS = 500L
    }

    private val recoverableNetworkExceptions = setOf(
        IOException::class.java,
        java.net.SocketTimeoutException::class.java,
        java.net.UnknownHostException::class.java,
        java.net.ConnectException::class.java,
        java.net.HttpRetryException::class.java,
        java.net.ProtocolException::class.java,
        java.net.SocketException::class.java,
        java.net.URISyntaxException::class.java,
        java.net.UnknownServiceException::class.java,
        java.net.MalformedURLException::class.java,
    )

    /**
     * Safe API call to handle network exceptions and retry the call up to MAX_RETRIES times
     * @param apiCall suspend function that makes the API call
     * @return RemoteResult<T> object
     */
    @Suppress("TooGenericExceptionCaught")
    open suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): RemoteResult<T> {
        var retries = 0
        while (retries <= MAX_RETRIES) {
            try {
                val response = apiCall()
                response.body()?.let {
                    return RemoteResult.Success(it)
                }
                return error("${response.code()} - ${response.message()}")
            } catch (e: Exception) {
                if (recoverableNetworkExceptions.contains(e::class.java)) {
                    if (retries == MAX_RETRIES) {
                        return error("Network error")
                    } else {
                        retries++
                        delay(calculateBackoff(retries))
                    }
                } else {
                    return error(e.message ?: e.toString())
                }
            }
        }
        return error("Failed to execute API call after maximum retries")
    }

    private fun <T> error(errorMessage: String): RemoteResult<T> {
        return RemoteResult.Error(errorMessage)
    }

    private fun calculateBackoff(retries: Int): Long {
        val backoffMs = INITIAL_BACKOFF_MS * (2.0).pow(retries.toDouble()).toLong()
        return minOf(backoffMs, MAX_BACKOFF_MS)
    }
}
