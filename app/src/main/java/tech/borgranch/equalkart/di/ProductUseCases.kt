package tech.borgranch.equalkart.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import tech.borgranch.equalkart.data.ProductRepository
import tech.borgranch.equalkart.data.ShoppingCartRepository
import tech.borgranch.equalkart.data.local.LocalDataSource
import tech.borgranch.equalkart.data.remote.NetworkDataSource
import tech.borgranch.equalkart.domain.usecases.AbandonCartUseCase
import tech.borgranch.equalkart.domain.usecases.AddToCartUseCase
import tech.borgranch.equalkart.domain.usecases.GetAllProductsUseCase
import tech.borgranch.equalkart.domain.usecases.RemoveFromCartUseCase
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
    @Singleton
    fun provideShoppingCartRepository(
        localDataSource: LocalDataSource,
        networkDataSource: NetworkDataSource,
    ): ShoppingCartRepository {
        return ShoppingCartRepository(localDataSource, networkDataSource)
    }

    @Provides
    fun provideGetAllProductsUseCase(
        productRepository: ProductRepository,
    ): GetAllProductsUseCase {
        return GetAllProductsUseCase(productRepository)
    }

    @Provides
    fun provideAddToCartUseCase(
        shoppingCartRepository: ShoppingCartRepository,
        productRepository: ProductRepository,
    ): AddToCartUseCase {
        return AddToCartUseCase(shoppingCartRepository, productRepository)
    }

    @Provides
    fun provideRemoveFromCartUseCase(
        shoppingCartRepository: ShoppingCartRepository,
        productRepository: ProductRepository,
    ): RemoveFromCartUseCase {
        return RemoveFromCartUseCase(shoppingCartRepository, productRepository)
    }

    @Provides
    fun provideAbandonCartUseCase(
        shoppingCartRepository: ShoppingCartRepository,
    ): AbandonCartUseCase {
        return AbandonCartUseCase(shoppingCartRepository)
    }
}
