package org.scidsg.hushline


expect fun isEmailAddressValid(emailAddress: String): Boolean

expect fun getCurrentDate(): String

fun getTestURL(): String =
    if (getPlatform().name.contains("Java")) "http://127.0.0.1:3000/v1" else "http://10.0.2.2:3000/v1"