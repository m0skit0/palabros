package org.m0skit0.android.palabros.usecase

import android.content.Context
import android.content.Intent
import android.net.Uri
import org.m0skit0.android.palabros.di.koin

typealias DefinitionUseCase = (String) -> Unit

fun definition(
    context: Context = koin.get(),
    word: String,
    uriProvider: (String) -> Uri? = { url -> Uri.parse(url) },
    intentProvider: (String, Uri) -> Intent = { action, uri -> Intent(action, uri) }
) {
    val url = "https://dle.rae.es/${word}"
    uriProvider(url)?.let { webpage ->
        intentProvider(Intent.ACTION_VIEW, webpage).let {
            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(it)
        }
    }
}
