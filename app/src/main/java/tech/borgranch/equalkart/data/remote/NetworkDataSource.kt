package tech.borgranch.equalkart.data.remote

class NetworkDataSource(
    private val productApi: ProductApi,
) {

    suspend fun getProduct(productName: String): RemoteResult<String> {
        return try {
            val response = productApi.getProduct(productName)
            RemoteResult.Success(data = response.name)
        } catch (e: Exception) {
            RemoteResult.NetworkError(message = e.message ?: "Unknown error")
        }
    }
}
