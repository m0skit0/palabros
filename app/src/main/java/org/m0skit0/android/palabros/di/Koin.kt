package org.m0skit0.android.palabros.di

import android.app.Application
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.android.ext.koin.androidContext
import org.koin.core.Koin
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.m0skit0.android.palabros.presentation.*
import org.m0skit0.android.palabros.state.PlayGridState
import org.m0skit0.android.palabros.usecase.OnKeyClickedUseCase
import org.m0skit0.android.palabros.usecase.onKeyClicked

lateinit var koin: Koin private set

@ExperimentalFoundationApi
fun Application.initializeKoin() {
    startKoin {
        androidContext(this@initializeKoin)
        modules(
            keyboardModule,
            gridModule,
            stateModule,
            useCaseModule
        )
    }.let {
        koin = it.koin
    }
}

val NAMED_KEYBOARD = named("NAMED_KEYBOARD")
val NAMED_KEYBOARD_KEY = named("NAMED_KEYBOARD_KEY")
val NAMED_KEYBOARD_KEY_TEXT = named("NAMED_KEYBOARD_KEY_TEXT")

@ExperimentalFoundationApi
private val keyboardModule = module {
    single<Keyboard>(NAMED_KEYBOARD) { { onKeyClick -> Keyboard(onKeyClick = onKeyClick) } }
    single<KeyboardKey>(NAMED_KEYBOARD_KEY) {
        @Composable { key: Char, keyPadding: Dp, textPadding: Dp, fontSize: TextUnit, onClick: (Char) -> Unit ->
            KeyboardKeyCard(
                key = key,
                cardPadding = keyPadding,
                textPadding = textPadding,
                fontSize = fontSize,
                onClick = onClick
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

val NAMED_WORD_GRID = named("NAMED_WORD_GRID")
val NAMED_PLAY_GRID = named("NAMED_PLAY_GRID")

@ExperimentalFoundationApi
private val gridModule = module {
    single<WordGrid>(NAMED_WORD_GRID) {
        @Composable {
            WordGrid()
        }
    }
    single<PlayGrid>(NAMED_PLAY_GRID) {
        @Composable { onKeyClick ->
            PlayGrid(onKeyClick = onKeyClick)
        }
    }
}

val NAMED_PLAY_GRID_STATE_FLOW = named("NAMED_PLAY_GRID_STATE_FLOW")
private val stateModule = module {
    single(NAMED_PLAY_GRID_STATE_FLOW) { MutableStateFlow(PlayGridState()) }
}

val NAMED_ON_KEY_CLICKED_USE_CASE = named("NAMED_ON_KEY_CLICKED_USE_CASE")
private val useCaseModule = module {
    single<OnKeyClickedUseCase>(NAMED_ON_KEY_CLICKED_USE_CASE) { { key -> onKeyClicked(key) } }
}
