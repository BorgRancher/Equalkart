package tech.borgranch.equalkart.domain.usecases

import tech.borgranch.equalkart.data.ProductRepository
import tech.borgranch.equalkart.data.remote.EqualResult
import tech.borgranch.equalkart.domain.model.Product
import javax.inject.Inject

class GetAllProductsUseCase @Inject constructor(
    private val productRepository: ProductRepository,
) : UseCase<List<Product>, GetAllProductsUseCase.Params>() {

    override suspend fun run(params: Params): List<Product> {
        return when (val result = productRepository.getProducts()) {
            is EqualResult.Success -> {
                result.data!!.map { Product.fromCachedProduct(it) }
            }

            is EqualResult.NetworkError -> {
                emptyList()
            }

            is EqualResult.Error -> {
                emptyList()
            }
        }
    }

    data class Params(
        val query: String = "",
    )
}
