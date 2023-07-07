@file:Suppress("FunctionNaming", "unused")

package tech.borgranch.equalkart.presentation.browseproducts.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.skydoves.landscapist.components.rememberImageComponent
import com.skydoves.landscapist.glide.GlideImage
import com.skydoves.landscapist.glide.GlideImageState
import com.skydoves.landscapist.placeholder.shimmer.ShimmerPlugin
import tech.borgranch.equalkart.R

@Composable
fun KartImage(
    imageUrl: String,
    modifier: Modifier = Modifier,
    placeHolderResource: Int = R.drawable.product_image_bitmap,
) {
    GlideImage(
        imageModel = { imageUrl },
        previewPlaceholder = placeHolderResource,
        requestOptions = {
            RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
        },
        component = rememberImageComponent {
            +ShimmerPlugin(
                baseColor = MaterialTheme.colorScheme.background,
                highlightColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f),
                durationMillis = 500,
                dropOff = 0.65f,
                tilt = 20f,
            )
        },
        success = { success: GlideImageState.Success, _: Painter ->
            success.imageBitmap?.let { imageBitmap ->
                Image(
                    bitmap = imageBitmap,
                    contentDescription = stringResource(R.string.app_name),
                    contentScale = ContentScale.Inside,
                    alignment = Alignment.Center,
                )
            }
        },
        failure = {
            Image(
                painter = painterResource(id = R.drawable.broken_image),
                contentDescription = "Unable to load image",
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center),
            )
        },
        modifier = modifier.fillMaxSize(),
    )
}

@Composable
@Preview(name = "KartImage", showBackground = true)
private fun KartImagePreview() {
    KartImage(
        imageUrl = "https://images.unsplash.com/photo-1622837137190-4f2b3b5b5b9f",
        modifier = Modifier.size(200.dp),
    )
}
