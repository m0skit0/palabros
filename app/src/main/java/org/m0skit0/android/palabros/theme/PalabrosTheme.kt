package org.m0skit0.android.palabros.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun PalabrosTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        content = content,
        colors = if (isSystemInDarkTheme()) DarkColors else LightColors,
        typography = PalabrosTypography
    )
}
