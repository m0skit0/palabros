package org.m0skit0.android.palabros.di

import android.app.Application
import androidx.compose.foundation.ExperimentalFoundationApi
import com.beust.klaxon.Klaxon
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.android.ext.koin.androidContext
import org.koin.core.Koin
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.m0skit0.android.palabros.data.JsonReader
import org.m0skit0.android.palabros.data.LoadWordsRepository
import org.m0skit0.android.palabros.data.loadWordsRepository
import org.m0skit0.android.palabros.log.Logger
import org.m0skit0.android.palabros.log.log
import org.m0skit0.android.palabros.state.PlayGridState
import org.m0skit0.android.palabros.usecase.*

lateinit var koin: Koin private set

@ExperimentalFoundationApi
fun Application.initializeKoin() {
    startKoin {
        androidContext(this@initializeKoin)
        modules(
            stateModule,
            useCaseModule,
            logModule,
            dataModule
        )
    }.let {
        koin = it.koin
    }
}

val NAMED_PLAY_GRID_STATE_FLOW = named("NAMED_PLAY_GRID_STATE_FLOW")
private val stateModule = module {
    single(NAMED_PLAY_GRID_STATE_FLOW) { MutableStateFlow(PlayGridState()) }
}

val NAMED_ON_KEY_CLICKED_USE_CASE = named("NAMED_ON_KEY_CLICKED_USE_CASE")
val NAMED_RANDOM_WORD_USE_CASE = named("NAMED_RANDOM_WORD_USE_CASE")
val NAMED_LOAD_WORDS_USE_CASE = named("NAMED_LOAD_WORDS_USE_CASE")
private val useCaseModule = module {
    single<OnKeyClickedUseCase>(NAMED_ON_KEY_CLICKED_USE_CASE) { { key -> onKeyClicked(key) } }
    single<LoadWordsUseCase>(NAMED_LOAD_WORDS_USE_CASE) { { loadWordsUseCase() } }
    single<RandomWordUseCase>(NAMED_RANDOM_WORD_USE_CASE) { { randomWordUseCase() } }
}

val NAMED_LOGGER = named("NAMED_LOGGER")
private val logModule = module {
    single<Logger>(NAMED_LOGGER) { { message -> log(message) } }
}

val NAMED_LOAD_WORDS_REPOSITORY = named("NAMED_LOAD_WORDS_REPOSITORY")
val NAMED_JSON_READER = named("NAMED_JSON_READER")
private val dataModule = module {
    single<LoadWordsRepository>(NAMED_LOAD_WORDS_REPOSITORY) { { loadWordsRepository() } }
    factory<JsonReader>(NAMED_JSON_READER) { { inputStream ->  Klaxon().parseArray(inputStream) } }
}
