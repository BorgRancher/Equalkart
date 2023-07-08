@file:Suppress("FunctionNaming")

package tech.borgranch.equalkart

import android.app.Application
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.HiltAndroidApp
import tech.borgranch.equalkart.navigation.EqualKartNavigation
import timber.log.Timber

@HiltAndroidApp
class EqualKartApp : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EqualKartRoot(modifier: Modifier = Modifier) {
    // Await implementation
    val navController = rememberNavController()
    val startingDestination = "browse_products"

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("EqualKart Shop") },
            )
        },
        content = { paddingValues: PaddingValues ->
            Column(modifier = Modifier.padding(paddingValues = paddingValues)) {
                EqualKartNavigation(navController, startingDestination)
            }
        },
        bottomBar = {
            // BottomNavBar(navController)
        },
        modifier = modifier,
    )
}
