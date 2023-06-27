package tech.borgranch.equalkart.data.remote

sealed class EqualResult<T>(
    val data: T? = null,
    val message: String? = null,
) {
    class Success<T>(data: T) : EqualResult<T>(data)
    class NetworkError<T>(message: String, data: T? = null) : EqualResult<T>(data, message)
    class Error<T>(message: String, data: T? = null) : EqualResult<T>(data, message)
}
