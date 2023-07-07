@file:Suppress("FunctionNaming", "unused")

package tech.borgranch.equalkart.presentation.browseproducts

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun BrowseProductsRoute(
    coordinator: BrowseProductsCoordinator = rememberBrowseProductsCoordinator(),
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
        )
    }
}
