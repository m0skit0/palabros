package org.m0skit0.android.palabros.theme

import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

val WrongLetterColor = Color.Red
val MisplacedLetterColor = Color(0xfff57f17)
val CorrectLetterColor = Color(0xff4caf50)

val GridBackgroundColor = Color.Black
val GridTextColor = Color.White
val KeysBackgroundColor = Color.Black
val KeyTextColor = Color.White
val HelpSymbolColor = Color.White

val LightColors = lightColors(
    primary = Color(0xff007ac1),
    primaryVariant = Color(0xff007ac1),
    onPrimary = Color.White,
    secondary = Color(0xff007ac1),
    secondaryVariant = Color(0xff007ac1),
    onSecondary = Color.White,
    error = Color(0xffd00036)
)

val DarkColors = darkColors(
    primary = Color(0xff67daff),
    primaryVariant = Color(0xff67daff),
    onPrimary = Color.Black,
    secondary = Color(0xff67daff),
    onSecondary = Color.Black,
    error = Color(0xfff297a2)
)
