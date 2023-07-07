package tech.borgranch.equalkart.presentation.browseproducts

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import tech.borgranch.equalkart.domain.model.Product
import tech.borgranch.equalkart.domain.usecases.GetAllProductsUseCase
import javax.inject.Inject

@HiltViewModel
class BrowseProductsViewModel @Inject constructor(
    private val getAllProductsUseCase: GetAllProductsUseCase,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _stateFlow: MutableStateFlow<BrowseProductsState> =
        MutableStateFlow(BrowseProductsState())

    val stateFlow: StateFlow<BrowseProductsState> = _stateFlow.asStateFlow()

    fun getAllProducts() = viewModelScope.launch {
        _stateFlow.value = _stateFlow.value.copy(
            isLoading = true,

        )
        val params = GetAllProductsUseCase.Params(
            query = _stateFlow.value.searchQuery,
        )
        getAllProductsUseCase(params, onResult = { products -> onProductsLoaded(products) })
    }

    private fun onProductsLoaded(products: List<Product>) {
        _stateFlow.value = _stateFlow.value.copy(
            isLoading = false,
            products = products,
        )
    }
}
