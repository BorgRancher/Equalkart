@file:Suppress("MagicNumber")

package tech.borgranch.equalkart.data.remote

import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import tech.borgranch.equalkart.BuildConfig
import tech.borgranch.equalkart.data.remote.responses.RemoteProduct
import java.util.concurrent.TimeUnit

interface EqualPricingApi {
    @GET("backend-take-home-test-data/{product}.json")
    suspend fun getProduct(
        @Path("product") productName: String,
    ): Response<RemoteProduct>
}

class EqualPricingApiService {

    companion object {
        const val BASE_URL = "https://equalexperts.github.io/"
        private var instance: EqualPricingApiService? = null
        private val lock = Any()
        fun getInstance(): EqualPricingApiService {
            return instance ?: synchronized(lock) {
                val service = EqualPricingApiService()
                instance = service
                service
            }
        }
    }

    val retrofitService: EqualPricingApi by lazy {

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(
                OkHttpClient.Builder().apply {
                    if (BuildConfig.DEBUG) {
                        val loggingInterceptor = LoggingInterceptor().provideLoggingInterceptor()
                        addInterceptor(loggingInterceptor)
                    }
                    connectTimeout(30, TimeUnit.SECONDS)
                    readTimeout(30, TimeUnit.SECONDS)
                    writeTimeout(30, TimeUnit.SECONDS)
                }.build(),
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(EqualPricingApi::class.java)
    }
}
