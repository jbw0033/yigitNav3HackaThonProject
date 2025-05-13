package org.example.darwinnav

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform