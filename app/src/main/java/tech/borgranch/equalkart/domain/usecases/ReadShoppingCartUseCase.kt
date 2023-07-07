package tech.borgranch.equalkart.domain.usecases

import tech.borgranch.equalkart.data.ShoppingCartRepository
import tech.borgranch.equalkart.domain.model.ShoppingCart
import javax.inject.Inject

class ReadShoppingCartUseCase @Inject constructor(
    private val shoppingCartRepository: ShoppingCartRepository,
) : UseCase<ShoppingCart, Any>() {
    override suspend fun run(params: Any): ShoppingCart {
        return shoppingCartRepository.shoppingCart
    }
}
