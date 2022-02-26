package org.m0skit0.android.palabros.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun PalabrosTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = LightColors,
        typography = PalabrosTypography,
        content = content,
    )
}
