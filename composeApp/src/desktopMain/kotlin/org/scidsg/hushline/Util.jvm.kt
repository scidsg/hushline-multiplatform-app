package org.scidsg.hushline

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.regex.Pattern

actual fun isEmailAddressValid(emailAddress: String): Boolean {
    val regex = "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
            "\\@" +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
            "(" +
            "\\." +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
            ")+"

    return Pattern.compile(regex).matcher(emailAddress).matches()
}

actual fun getCurrentDate(): String {
    return SimpleDateFormat("dd-MM-yyyy HH:mm:ss z", Locale.getDefault()).format(Date())
}