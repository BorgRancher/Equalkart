@file:Suppress("FunctionNaming", "unused")

package tech.borgranch.equalkart.presentation.browseproducts

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import tech.borgranch.equalkart.domain.model.Product
import tech.borgranch.equalkart.domain.model.ShoppingCart

/**
 * UI State that represents BrowseProductsScreen
 **/
data class BrowseProductsState(
    val isLoading: Boolean = false,
    val products: List<Product> = emptyList(),
    val isSearchActive: Boolean = false,
    val searchQuery: String = "",
    val searchResults: List<Product> = emptyList(),
    val isSearchError: Boolean = false,
    val shoppingCart: ShoppingCart = ShoppingCart(),
    val error: String = "",
)

/**
 * BrowseProducts Actions emitted from the UI Layer
 * passed to the coordinator to handle
 **/
data class BrowseProductsActions(
    val onProductClick: (String) -> Unit = {},
    val onSearchQueryChanged: (String) -> Unit = {},
    val onSearch: (String) -> Unit = {},
    val onSearchDismiss: () -> Unit = {},
    val onAddToCart: (String, Int) -> Unit = { s: String, i: Int -> },
    val onRemoveFromCart: (String, Int) -> Unit = { s: String, i: Int -> },
    val onAbandonCart: () -> Unit = {},
    val onClearCart: () -> Unit = {},
    val onCheckout: () -> Unit = {},
)

/**
 * Compose Utility to retrieve actions from nested components
 **/
val LocalBrowseProductsActions = staticCompositionLocalOf<BrowseProductsActions> {
    error("{NAME} Actions Were not provided, make sure ProvideBrowseProductsActions is called")
}

@Composable
fun ProvideBrowseProductsActions(actions: BrowseProductsActions, content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalBrowseProductsActions provides actions) {
        content.invoke()
    }
}
