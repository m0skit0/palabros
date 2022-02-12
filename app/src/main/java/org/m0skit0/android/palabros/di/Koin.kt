package org.m0skit0.android.palabros.di

import android.app.Application
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import org.koin.android.ext.koin.androidContext
import org.koin.core.Koin
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.m0skit0.android.palabros.presentation.Keyboard
import org.m0skit0.android.palabros.presentation.KeyboardKey
import org.m0skit0.android.palabros.presentation.KeyboardKeyCard
import org.m0skit0.android.palabros.presentation.KeyboardKeyText

private lateinit var koin: Koin
fun koin(): Koin = koin

@ExperimentalFoundationApi
fun Application.initializeKoin() {
    startKoin {
        androidContext(this@initializeKoin)
        modules(
            composableModule
        )
    }
}

val NAMED_KEYBOARD = named("NAMED_KEYBOARD")
val NAMED_KEYBOARD_KEY = named("NAMED_KEYBOARD_KEY")
val NAMED_KEYBOARD_KEY_TEXT = named("NAMED_KEYBOARD_KEY_TEXT")

@ExperimentalFoundationApi
private val composableModule = module {
    single<Keyboard>(NAMED_KEYBOARD) { { Keyboard() } }
    single<KeyboardKey>(NAMED_KEYBOARD_KEY) {
        @Composable { key: Char, keyPadding: Dp, textPadding: Dp, fontSize: TextUnit ->
            KeyboardKeyCard(
                key = key,
                cardPadding = keyPadding,
                textPadding = textPadding,
                fontSize = fontSize
            )
        }
    }
    single<KeyboardKeyText>(NAMED_KEYBOARD_KEY_TEXT) {
        @Composable { key: Char, textPadding: Dp, fontSize: TextUnit ->
            KeyboardKeyText(
                key = key,
                textPadding = textPadding,
                fontSize = fontSize,
            )
        }
    }
}
