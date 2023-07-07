package tech.borgranch.equalkart.domain.usecases

import tech.borgranch.equalkart.data.ProductRepository
import tech.borgranch.equalkart.data.ShoppingCartRepository
import tech.borgranch.equalkart.data.remote.EqualResult
import tech.borgranch.equalkart.domain.model.Product
import tech.borgranch.equalkart.domain.model.ShoppingCart
import timber.log.Timber
import javax.inject.Inject

class AddToCartUseCase @Inject constructor(
    private val shoppingCartRepository: ShoppingCartRepository,
    private val productRepository: ProductRepository,
) : UseCase<ShoppingCart, AddToCartUseCase.Params>() {
    override suspend fun run(params: Params): ShoppingCart {
        val productResult = productRepository.getProduct(params.productName)
        return when (productResult is EqualResult.Success) {
            true -> {
                shoppingCartRepository.addItem(
                    product = Product.fromCachedProduct(productResult.data!!),
                    quantity = params.quantity,
                )
                shoppingCartRepository.shoppingCart
            }

            else -> {
                Timber.e(message = productResult.message)
                shoppingCartRepository.shoppingCart
            }
        }
    }

    data class Params(
        val productName: String,
        val quantity: Int = 1,
    )
}
