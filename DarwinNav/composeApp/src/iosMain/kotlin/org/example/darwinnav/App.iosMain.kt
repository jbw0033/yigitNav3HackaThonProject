package org.example.darwinnav

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.UIKitViewController
import kotlinx.cinterop.ExperimentalForeignApi

@OptIn(ExperimentalForeignApi::class)
@Composable
fun IOsApp(
    platformNavigator: PlatformNavigator,
    platformUI: (Destination.PlatformDestination) -> platform.UIKit.UIViewController
) {
    App(platformNavigator=platformNavigator) {
        dest ->
        UIKitViewController(
            factory = {
                platformUI(dest)
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}
