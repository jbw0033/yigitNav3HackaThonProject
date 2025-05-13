package org.example.darwinnav

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.UIKitViewController
import androidx.compose.ui.window.ComposeUIViewController
import platform.UIKit.UIViewController

fun MainViewController(
    platformNavigator:PlatformNavigator,
    swiftUI: (Destination.PlatformDestination) -> UIViewController
) = ComposeUIViewController { App(platformNavigator) { destination ->
    Column {
        Text(text = "destination $destination from swift")
        UIKitViewController(
            factory = {
                swiftUI(destination)
            },
            modifier = Modifier.fillMaxSize()
        )
    }
} }