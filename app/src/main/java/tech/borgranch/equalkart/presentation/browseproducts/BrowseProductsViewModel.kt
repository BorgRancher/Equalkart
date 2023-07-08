package tech.borgranch.equalkart.presentation.browseproducts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import tech.borgranch.equalkart.domain.model.Product
import tech.borgranch.equalkart.domain.model.ShoppingCart
import tech.borgranch.equalkart.domain.usecases.AbandonCartUseCase
import tech.borgranch.equalkart.domain.usecases.AddToCartUseCase
import tech.borgranch.equalkart.domain.usecases.GetAllProductsUseCase
import tech.borgranch.equalkart.domain.usecases.ReadShoppingCartUseCase
import tech.borgranch.equalkart.domain.usecases.RemoveFromCartUseCase
import javax.inject.Inject

@HiltViewModel
class BrowseProductsViewModel @Inject constructor(
    private val getAllProductsUseCase: GetAllProductsUseCase,
    private val addToCartUseCase: AddToCartUseCase,
    private val removeFromCartUseCase: RemoveFromCartUseCase,
    private val readShoppingCartUseCase: ReadShoppingCartUseCase,
    private val abandonCartUseCase: AbandonCartUseCase,
) : ViewModel() {

    private val _stateFlow: MutableStateFlow<BrowseProductsState> =
        MutableStateFlow(BrowseProductsState())

    val stateFlow: StateFlow<BrowseProductsState> = _stateFlow.asStateFlow()

    fun getAllProducts() = viewModelScope.launch {
        _stateFlow.value = _stateFlow.value.copy(
            isLoading = true,

        )
        val params = GetAllProductsUseCase.Params(
            query = _stateFlow.value.searchQuery ?: "",
        )
        getAllProductsUseCase(params, onResult = { products -> onProductsLoaded(products) })
        readShoppingCartUseCase(Any(), onResult = { cart -> onCartUpdated(cart) })

        _stateFlow.value = _stateFlow.value.copy(
            isLoading = false,
        )
    }

    fun addToCart(productName: String, quantity: Int = 1) {
        viewModelScope.launch {
            val params = AddToCartUseCase.Params(
                productName = productName,
                quantity = quantity,
            )
            addToCartUseCase(params, onResult = { cart -> onCartUpdated(cart) })
        }
    }

    fun removeFromCart(productName: String, quantity: Int = Int.MIN_VALUE) {
        viewModelScope.launch {
            val params = RemoveFromCartUseCase.Params(
                productName = productName,
                quantity = quantity,
            )
            removeFromCartUseCase(params, onResult = { cart -> onCartUpdated(cart) })
        }
    }

    private fun onCartUpdated(cart: ShoppingCart) {
        _stateFlow.value = _stateFlow.value.copy(
            shoppingCart = cart,
        )
    }

    private fun onProductsLoaded(products: List<Product>) {
        _stateFlow.value = _stateFlow.value.copy(
            isLoading = false,
            products = products,
        )
    }

    fun abandonCart() {
        viewModelScope.launch {
            abandonCartUseCase(Any(), onResult = { cart -> onCartUpdated(cart) })
        }
    }
}
