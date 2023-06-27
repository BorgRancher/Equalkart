
package tech.borgranch.equalkart.data.remote

import kotlinx.coroutines.delay
import retrofit2.Response
import java.io.IOException
import java.net.ConnectException
import java.net.HttpRetryException
import java.net.ProtocolException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import kotlin.math.pow

open class BaseNetworkDataSource {
    companion object {
        private const val MAX_RETRIES = 5
        private const val MAX_BACKOFF_MS = 10000L
        private const val INITIAL_BACKOFF_MS = 500L
    }

    private val recoverableNetworkExceptions = setOf(
        IOException::class.java,
        SocketTimeoutException::class.java,
        UnknownHostException::class.java,
        ConnectException::class.java,
        HttpRetryException::class.java,
        ProtocolException::class.java,
        SocketException::class.java,
    )

    /**
     * Safe API call to handle network exceptions and retry the call up to MAX_RETRIES times
     * @param apiCall suspend function that makes the API call
     * @return RemoteResult<T> object
     */
    @Suppress("TooGenericExceptionCaught", "NestedBlockDepth", "ReturnCount")
    open suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): EqualResult<T> {
        var retries = 0
        while (retries <= MAX_RETRIES) {
            try {
                val response = apiCall()
                response.body()?.let {
                    return EqualResult.Success(data = it)
                }
                return error("${response.code()} - ${response.message()}")
            } catch (e: Exception) {
                if (isRecoverableNetworkException(e)) {
                    if (retries == MAX_RETRIES) {
                        return error("Network error: ${e.message ?: e.toString()}")
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

    private fun <T> error(errorMessage: String): EqualResult<T> {
        return EqualResult.Error(errorMessage)
    }

    private fun calculateBackoff(retries: Int): Long {
        val backoffMs = INITIAL_BACKOFF_MS * (2.0).pow(retries.toDouble()).toLong()
        return minOf(backoffMs, MAX_BACKOFF_MS)
    }

    private fun isRecoverableNetworkException(e: Exception): Boolean {
        return recoverableNetworkExceptions.any { it.isAssignableFrom(e::class.java) }
    }
}
