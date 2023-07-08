package tech.borgranch.equalkart.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import tech.borgranch.equalkart.data.ProductRepository
import tech.borgranch.equalkart.data.ShoppingCartRepository
import tech.borgranch.equalkart.data.local.LocalDataSource
import tech.borgranch.equalkart.data.remote.NetworkDataSource
import tech.borgranch.equalkart.domain.usecases.AbandonCartUseCase
import tech.borgranch.equalkart.domain.usecases.AddToCartUseCase
import tech.borgranch.equalkart.domain.usecases.GetAllProductsUseCase
import tech.borgranch.equalkart.domain.usecases.RemoveFromCartUseCase

@Module
@InstallIn(ViewModelComponent::class)
object ProductUseCases {
    @Provides
    @ViewModelScoped
    fun provideProductRepository(
        networkDataSource: NetworkDataSource,
        localDataSource: LocalDataSource,
    ): ProductRepository {
        return ProductRepository(networkDataSource, localDataSource)
    }

    @Provides
    @ViewModelScoped
    fun provideShoppingCartRepository(
        localDataSource: LocalDataSource,
        networkDataSource: NetworkDataSource,
    ): ShoppingCartRepository {
        return ShoppingCartRepository(localDataSource, networkDataSource)
    }

    @Provides
    @ViewModelScoped
    fun provideGetAllProductsUseCase(
        productRepository: ProductRepository,
    ): GetAllProductsUseCase {
        return GetAllProductsUseCase(productRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideAddToCartUseCase(
        shoppingCartRepository: ShoppingCartRepository,
        productRepository: ProductRepository,
    ): AddToCartUseCase {
        return AddToCartUseCase(shoppingCartRepository, productRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideRemoveFromCartUseCase(
        shoppingCartRepository: ShoppingCartRepository,
        productRepository: ProductRepository,
    ): RemoveFromCartUseCase {
        return RemoveFromCartUseCase(shoppingCartRepository, productRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideAbandonCartUseCase(
        shoppingCartRepository: ShoppingCartRepository,
    ): AbandonCartUseCase {
        return AbandonCartUseCase(shoppingCartRepository)
    }
}
