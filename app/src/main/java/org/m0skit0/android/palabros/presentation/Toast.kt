package org.m0skit0.android.palabros.presentation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun Toast(message: String) {
    Toast.makeText(LocalContext.current, message, Toast.LENGTH_LONG).show()
}
