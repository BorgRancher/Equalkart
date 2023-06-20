package tech.borgranch.equalkart.data.remote

sealed class RemoteResult<T>(
    val data: T? = null,
    val message: String? = null,
) {
    class Success<T>(data: T) : RemoteResult<T>(data)
    class NetworkError<T>(message: String, data: T? = null) : RemoteResult<T>(data, message)
    class Error<T>(message: String, data: T? = null) : RemoteResult<T>(data, message)
}
