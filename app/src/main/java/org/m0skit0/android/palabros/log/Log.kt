package org.m0skit0.android.palabros.log

import android.util.Log

typealias Logger = (message: String) -> Unit

fun log(message: String) {
    Log.d("PALABROS", message)
}

fun log(exception: Exception) {
    Log.e("PALABROS", "Error", exception)
}
