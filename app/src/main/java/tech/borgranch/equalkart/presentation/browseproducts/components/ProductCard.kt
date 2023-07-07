@file:Suppress("FunctionNaming", "unused")

package tech.borgranch.equalkart.presentation.browseproducts.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
                modifier = Modifier.size(128.dp),
                placeHolderResource = product.imageResId,
            )
            Column(
                modifier = Modifier.padding(16.dp),
            ) {
                Text(text = product.name, style = MaterialTheme.typography.headlineMedium)
                val dec = DecimalFormat("#,###.##")

                Text(
                    text = "$ ${
                        dec.format(
                            product.price.roundToCurrency(),
                        )
                    }",
                    style = MaterialTheme.typography.headlineSmall,
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,

                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.align(Alignment.End),
                ) {
                    IconButton(
                        onClick = { actions.onRemoveFromCart }, // Minus button
                        modifier = Modifier.size(32.dp),
                    ) {
                        Icon(
                            painterResource(id = R.drawable.remove_shopping_cart),
                            contentDescription = "Minus",
                        )
                    }
                    Text(text = "$itemCount", style = MaterialTheme.typography.bodySmall)
                    IconButton(
                        onClick = { actions.onRemoveFromCart }, // Plus button
                        modifier = Modifier.size(32.dp),
                    ) {
                        Icon(
                            painterResource(id = R.drawable.add_shopping_cart),
                            contentDescription = "Plus",
                        )
                    }
                }
            }
        }
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
