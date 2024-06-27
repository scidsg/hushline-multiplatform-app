package org.scidsg.hushline

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform