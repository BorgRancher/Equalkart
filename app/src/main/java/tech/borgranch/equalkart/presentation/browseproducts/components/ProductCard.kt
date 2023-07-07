@file:Suppress("FunctionNaming", "unused")

package tech.borgranch.equalkart.presentation.browseproducts.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import tech.borgranch.equalkart.R
import tech.borgranch.equalkart.domain.model.Product
import tech.borgranch.equalkart.domain.usecases.AddToCartUseCase
import tech.borgranch.equalkart.domain.usecases.RemoveFromCartUseCase
import tech.borgranch.equalkart.presentation.browseproducts.BrowseProductsActions
import tech.borgranch.equalkart.utility.roundToCurrency
import java.text.DecimalFormat

@Composable
fun ProductCard(product: Product, itemCount: Int = 0, actions: BrowseProductsActions) {
    Card(
        modifier = Modifier.padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            KartImage(
                imageUrl = product.image,
                modifier = Modifier.size(128.dp).padding(8.dp),
                placeHolderResource = product.imageResId,
            )
            Column(
                modifier = Modifier.padding(16.dp).weight(1f),

            ) {
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.headlineMedium,
                )
                val dec = DecimalFormat("#,###.##")
                val formattedPrice = "$ ${
                    dec.format(
                        product.price.roundToCurrency(),
                    )
                }"
                Text(
                    text = formattedPrice,
                    style = MaterialTheme.typography.headlineSmall,
                )
                Row(
                    modifier = Modifier.padding(top = 8.dp).align(alignment = Alignment.End),
                ) {
                    QuantityControl(
                        itemCount = itemCount,
                        onIncrease = {
                            actions.onAddToCart(
                                AddToCartUseCase.Params(
                                    product = product,
                                    quantity = 1,
                                ),
                            )
                        },
                        onDecrease = {
                            actions.onRemoveFromCart(
                                RemoveFromCartUseCase.Params(product = product, quantity = 1),
                            )
                        },
                    )
                }
            }
        }
    }
}

@Composable
fun QuantityControl(
    itemCount: Int,
    onIncrease: () -> Unit = {},
    onDecrease: () -> Unit = {},
) {
    Image(
        painter = painterResource(id = R.drawable.baseline_add_24),

        contentDescription = "Increase",
        modifier = Modifier
            .size(24.dp)
            .clickable { onIncrease() },
    )
    Text(
        text = itemCount.toString(),
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier.padding(horizontal = 8.dp),
    )
    if (itemCount > 0) {
        Image(
            painter = painterResource(id = R.drawable.baseline_remove_24),
            contentDescription = "Decrease",
            modifier = Modifier
                .size(24.dp)
                .clickable { onDecrease() },
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun ProductCardPreview() {
    ProductCard(
        product = Product(
            name = "Product Name",
            price = 10.55,
            image = "",
            imageResId = R.drawable.product_image_bitmap,
        ),
        itemCount = 1,
        actions = BrowseProductsActions(),
    )
}
