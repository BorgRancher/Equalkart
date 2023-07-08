package tech.borgranch.equalkart.presentation.browseproducts

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController

/**
 * Screen's coordinator which is responsible for handling actions from the UI layer
 * and one-shot actions based on the new UI state
 */
class BrowseProductsCoordinator(
    val navController: NavHostController,
    val viewModel: BrowseProductsViewModel,
) {
    val screenStateFlow = viewModel.stateFlow

    init {
        viewModel.getAllProducts()
    }

    fun onAddToCart(productName: String, quantity: Int = 1) {
        viewModel.addToCart(productName, quantity)
    }

    fun onRemoveFromCart(productName: String, quantity: Int = Int.MIN_VALUE) {
        viewModel.removeFromCart(productName, quantity)
    }

    fun onAbandonCart() {
        viewModel.abandonCart()
    }

    fun showProductDetails(productName: String) {
        // navigate to product details
        println("Navigate to product details for $productName")
        navController.navigate("productDetails/$productName")
    }
}

@Composable
fun rememberBrowseProductsCoordinator(
    navController: NavHostController,
    viewModel: BrowseProductsViewModel = hiltViewModel(),
): BrowseProductsCoordinator {
    return remember(viewModel) {
        BrowseProductsCoordinator(
            navController = navController,
            viewModel = viewModel,
        )
    }
}
