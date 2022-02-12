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
import org.m0skit0.android.palabros.presentation.*

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
val NAMED_WORD_GRID = named("NAMED_WORD_GRID")
val NAMED_PLAY_GRID = named("NAMED_PLAY_GRID")

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
    single<WordGrid>(NAMED_WORD_GRID) {
        @Composable {
            WordGrid()
        }
    }
    single<PlayGrid>(NAMED_PLAY_GRID) {
        @Composable {
            PlayGrid()
        }
    }
}
