package org.example.habit

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform