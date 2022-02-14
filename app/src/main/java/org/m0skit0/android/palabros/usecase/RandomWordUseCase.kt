package org.m0skit0.android.palabros.usecase

import android.content.Context
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.m0skit0.android.palabros.data.LoadWordsRepository
import org.m0skit0.android.palabros.di.NAMED_LOAD_WORDS_REPOSITORY
import org.m0skit0.android.palabros.di.koin

typealias RandomWordUseCase = suspend (Context) -> String

suspend fun randomWordUseCase(
    context: Context,
    jsonReader: LoadWordsRepository = koin.get(NAMED_LOAD_WORDS_REPOSITORY),
    dispatcher: CoroutineDispatcher = Dispatchers.IO
): String = withContext(dispatcher) { jsonReader(context).random() }
