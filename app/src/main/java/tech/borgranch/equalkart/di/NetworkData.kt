package tech.borgranch.equalkart.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import tech.borgranch.equalkart.data.remote.EqualPricingApi
import tech.borgranch.equalkart.data.remote.EqualPricingApiService
import tech.borgranch.equalkart.data.remote.NetworkDataSource
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkData {
    @Provides
    @Singleton
    fun provideEqualPricingApi(): EqualPricingApi {
        return EqualPricingApiService.getInstance().retrofitService
    }

    @Provides
    @Singleton
    fun provideNetworkDataSource(
        equalPricingApi: EqualPricingApi,
    ): NetworkDataSource {
        return NetworkDataSource(equalPricingApi)
    }
}
