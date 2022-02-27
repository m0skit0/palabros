package org.m0skit0.android.palabros.usecase

import android.content.Context
import android.content.Intent
import android.net.Uri
import org.m0skit0.android.palabros.di.koin

typealias DefinitionUseCase = (String) -> Unit

fun definition(
    context: Context = koin.get(),
    word: String
) {
    val url = "https://dle.rae.es/${word}"
    Uri.parse(url).let { webpage ->
        Intent(Intent.ACTION_VIEW, webpage).let {
            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(it)
        }
    }
}
