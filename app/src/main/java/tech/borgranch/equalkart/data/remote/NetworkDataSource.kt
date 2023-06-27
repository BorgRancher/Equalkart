package tech.borgranch.equalkart.data.remote

import tech.borgranch.equalkart.data.remote.responses.RemoteProduct

class NetworkDataSource(
    private val productApi: EqualPricingApi = EqualPricingApiService.getInstance().retrofitService,
) : BaseNetworkDataSource() {

    suspend fun getProduct(productName: String): EqualResult<RemoteProduct> {
        return safeApiCall { productApi.getProduct(productName) }
    }
}
