package tech.borgranch.equalkart.domain.usecases

import tech.borgranch.equalkart.data.ShoppingCartRepository
import javax.inject.Inject

class CheckoutCartUseCase @Inject constructor(
    private val shoppingCartRepository: ShoppingCartRepository,
) : UseCase<Long, Unit>() {
    override suspend fun run(params: Unit): Long {
        return shoppingCartRepository.checkoutCart()
    }
}
