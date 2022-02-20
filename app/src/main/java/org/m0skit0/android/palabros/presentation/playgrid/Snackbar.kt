package org.m0skit0.android.palabros.presentation.playgrid

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import org.m0skit0.android.palabros.R

@Preview
@Composable
fun LostSnackbarPreview() {
    LostSnackbar(secretWord = "roble") {}
}

@Preview
@Composable
fun WinSnackbarPreview() {
    WinSnackbar {}
}

@Composable
fun LostSnackbar(
    secretWord: String,
    onTryAgain: () -> Unit
) {
    SnackbarBox {
        Snackbar(
            action = {
                Button(onClick = onTryAgain) {
                    Text(text = stringResource(R.string.again))
                }
            }) {
            Text(text = stringResource(R.string.lost, secretWord))
        }
    }
}

@Composable
fun WinSnackbar(
    onTryAgain: () -> Unit
) {
    SnackbarBox {
        Snackbar(
            action = {
                Button(onClick = onTryAgain) {
                    Text(text = stringResource(R.string.again))
                }
            }) {
            Text(text = stringResource(R.string.win))
        }
    }
}

@Composable
private fun SnackbarBox(snackbar: @Composable BoxScope.() -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = BottomCenter,
        content = snackbar
    )
}
