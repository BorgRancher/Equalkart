@file:Suppress("FunctionNaming", "unused")

package tech.borgranch.equalkart.presentation.browseproducts

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import tech.borgranch.equalkart.domain.model.ShoppingCart
import tech.borgranch.equalkart.presentation.browseproducts.components.PreviewData
import tech.borgranch.equalkart.presentation.browseproducts.components.ProductCard

@Composable
fun BrowseProductsScreen(
    state: BrowseProductsState = BrowseProductsState(),
    actions: BrowseProductsActions = BrowseProductsActions(),
) {
    val lazyListState: LazyListState = rememberLazyListState()
    LazyColumn(
        state = lazyListState,
    ) {
        items(state.products.size) { productIndex ->
            val product = state.products[productIndex]
            val itemCount = state.shoppingCart.getItemCount(product)
            ProductCard(
                product = product,
                itemCount = itemCount,
                actions = actions,
            )
        }
    }
}

@Composable
@Preview(name = "BrowseProducts", showBackground = true)
private fun BrowseProductsScreenPreview() {
    val previewData = PreviewData()
    val state = BrowseProductsState(
        isLoading = false,
        products = previewData.products.values.toList(),
        shoppingCart = ShoppingCart(),
    )
    BrowseProductsScreen(state = state)
}
