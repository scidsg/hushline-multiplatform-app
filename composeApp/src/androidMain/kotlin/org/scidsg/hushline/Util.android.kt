package org.scidsg.hushline

import android.icu.text.SimpleDateFormat
import android.util.Patterns
import java.util.Date
import java.util.Locale

actual fun isEmailAddressValid(emailAddress: String): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()
}

actual fun getCurrentDate(): String {
    return SimpleDateFormat("dd-MM-yyyy HH:mm:ss z", Locale.getDefault()).format(Date())
}