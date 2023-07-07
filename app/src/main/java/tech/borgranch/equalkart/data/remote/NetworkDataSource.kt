package tech.borgranch.equalkart.data.remote

import tech.borgranch.equalkart.data.remote.responses.RemoteProduct

class NetworkDataSource(
    private val productApi: EqualPricingApi = EqualPricingApiService.getInstance().retrofitService,
) : BaseNetworkDataSource() {

    suspend fun getProduct(productName: String): EqualResult<RemoteProduct> {
        return safeApiCall { productApi.getProduct(productName) }
    }

    suspend fun getProducts(): EqualResult<List<RemoteProduct>> {
        val availableProducts =
            listOf("cheerios", "cornflakes", "frosties", "shreddies", "weetabix")
        val products = mutableListOf<RemoteProduct>()
        for (product in availableProducts) {
            val productResponse = getProduct(product)
            if (productResponse is EqualResult.Success) {
                products.add(productResponse.data!!)
            }
        }
        return EqualResult.Success(products)
    }
}
