package org.example.darwinnav

import androidx.compose.runtime.Composable
import kotlinx.datetime.Instant

sealed interface Destination {
    data object Home: Destination
    data class Logo(
        val depth: Int
    ): Destination
    interface PlatformDestination: Destination
    data class PlatformWithButton(
        val depth: Int) : PlatformDestination
}

interface RootNavHandler {
    fun navigeteTo(destination: Destination)
    fun goBack()
}

class PlatformNavigator {
    var delegate: RootNavHandler? = null
    fun navigateTo(destination: Destination) = delegate?.navigeteTo(destination)
    fun goBack() = delegate?.goBack()
}