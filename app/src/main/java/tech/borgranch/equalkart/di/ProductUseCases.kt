package tech.borgranch.equalkart.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import tech.borgranch.equalkart.data.ProductRepository
import tech.borgranch.equalkart.data.local.LocalDataSource
import tech.borgranch.equalkart.data.remote.NetworkDataSource
import tech.borgranch.equalkart.domain.usecases.GetAllProductsUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProductUseCases {
    @Provides
    @Singleton
    fun provideProductRepository(
        networkDataSource: NetworkDataSource,
        localDataSource: LocalDataSource,
    ): ProductRepository {
        return ProductRepository(networkDataSource, localDataSource)
    }

    @Provides
    fun provideGetAllProductsUseCase(
        productRepository: ProductRepository,
    ): GetAllProductsUseCase {
        return GetAllProductsUseCase(productRepository)
    }
}
