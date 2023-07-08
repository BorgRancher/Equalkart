@file:Suppress("FunctionNaming", "unused")

package tech.borgranch.equalkart.presentation.browseproducts

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController

@Composable
fun BrowseProductsRoute(
    navHostController: NavHostController,
    coordinator: BrowseProductsCoordinator = rememberBrowseProductsCoordinator(
        navController = navHostController,
    ),
) {
    // State observing and declarations
    val uiState by coordinator.screenStateFlow.collectAsStateWithLifecycle(BrowseProductsState())

    // UI Actions
    val actions = rememberBrowseProductsActions(coordinator)

    // UI Rendering
    BrowseProductsScreen(uiState, actions)
}

@Composable
fun rememberBrowseProductsActions(coordinator: BrowseProductsCoordinator): BrowseProductsActions {
    return remember(coordinator) {
        BrowseProductsActions(
            onProductClick = coordinator::showProductDetails,
            onAddToCart = coordinator::onAddToCart,
            onRemoveFromCart = coordinator::onRemoveFromCart,
            onAbandonCart = coordinator::onAbandonCart,
            onClearCart = coordinator::onAbandonCart,
        )
    }
}
