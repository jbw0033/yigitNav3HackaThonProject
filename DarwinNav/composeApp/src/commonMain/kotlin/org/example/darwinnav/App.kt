package org.example.darwinnav

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation3.NavDisplay
import androidx.navigation3.Record
import androidx.navigation3.rememberNavWrapperManager
import darwinnav.composeapp.generated.resources.Res
import darwinnav.composeapp.generated.resources.compose_multiplatform
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(
    platformNavigator: PlatformNavigator,
    platformUI: @Composable (Destination.PlatformDestination) -> Unit
) {
    MaterialTheme {
        val backstack = remember {
            mutableStateListOf<Destination>(Destination.Home)
        }
        val rootNavHandler = remember {
            object : RootNavHandler {
                override fun navigeteTo(destination: Destination) {
                    backstack.add(destination)
                }

                override fun goBack() {
                    if (backstack.isNotEmpty()) {
                        backstack.removeLast()
                    }
                }
            }.also {
                platformNavigator.delegate = it
            }
        }
        val manager = rememberNavWrapperManager(emptyList())
        Column {
            AnimatedVisibility(
                visible = backstack.size > 1
            ) {
                Button(
                    onClick = { backstack.removeLast() }
                ) {
                    Text("Back")
                }
            }
            NavDisplay(
                backstack = backstack,
                wrapperManager = manager,
                onBack = {
                    backstack.removeLastOrNull()
                }
            ) { key ->
                when(key) {
                    Destination.Home -> {
                        Record("Home") {
                            Button(onClick = { backstack.add(
                                Destination.Logo(1)
                            ) }) {
                                Text("Click me!")
                            }
                        }
                    }
                    is Destination.Logo -> {
                        Record(key) {
                            val greeting = remember { Greeting().greet() }
                            Column(Modifier.fillMaxWidth()
                                   , horizontalAlignment = Alignment.CenterHorizontally) {
                                Image(painterResource(Res.drawable.compose_multiplatform), null)
                                Text("Compose: $greeting at depth ${key.depth}")
                                Row {
                                    Button(
                                        onClick = {
                                            backstack.add(
                                                Destination.Logo(
                                                    depth = key.depth + 1
                                                )
                                            )
                                        }
                                    ) {
                                        Text("add another")
                                    }
                                    Button(
                                        onClick = {
                                            backstack.add(
                                                Destination.PlatformWithButton(
                                                    depth = key.depth + 1)
                                            )
                                        }
                                    ) {
                                        Text("add swift UI")
                                    }
                                }
                            }
                        }
                    }
                    is Destination.PlatformDestination -> {
                        Record(key) {
                            platformUI(key)
                        }
                    }
                    else -> {
                        Record("no-content") {
                            Text("missing content")
                        }
                    }
                }
            }
        }
    }
}