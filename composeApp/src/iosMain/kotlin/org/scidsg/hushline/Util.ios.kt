package org.scidsg.hushline

import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDate
import platform.Foundation.NSDateFormatter
import platform.Foundation.NSRangeFromString
import platform.Foundation.NSRegularExpression
import platform.Foundation.matchesInString

@OptIn(ExperimentalForeignApi::class)
actual fun isEmailAddressValid(emailAddress: String): Boolean {
    val regex = "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
            "\\@" +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
            "(" +
            "\\." +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
            ")+"
    //NSRegularExpression(pattern = regex, options = 0u, error = null)
    return NSRegularExpression().matchesInString(regex, 0u, NSRangeFromString(regex)).isNotEmpty()
    //return true //TODO implement platform specific //Pattern.compile(regex).matcher(emailAddress).matches()
}

actual fun getCurrentDate(): String {
    val date = NSDate()
    val formatter = NSDateFormatter()
    formatter.dateFormat = "dd-MM-yyyy HH:mm:ss z"
    return formatter.stringFromDate(date)
    //TODO implement platform specific //SimpleDateFormat("dd-MM-yyyy HH:mm:ss z", Locale.getDefault()).format(Date())
}