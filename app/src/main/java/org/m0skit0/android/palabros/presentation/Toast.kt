package org.m0skit0.android.palabros.presentation

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

// TODO Change to snackbars
fun Context.toast(@StringRes id: Int, vararg args: String) {
    Toast.makeText(this, getString(id, args), Toast.LENGTH_SHORT).show()
}
