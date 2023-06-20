package tech.borgranch.equalkart.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import tech.borgranch.equalkart.data.remote.responses.ProductResponse

interface ProductApi {
    @GET("backend-take-home-test-data/{product}.json")
    suspend fun getProduct(
        @Path("product") productName: String,
    ): ProductResponse
}
