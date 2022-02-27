package org.m0skit0.android.palabros.presentation.playgrid

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.m0skit0.android.palabros.R

@Preview
@Composable
fun LostSnackbarPreview() {
    LostSnackbar(
        secretWord = "roble",
        onTryAgain = {},
        onDefinition = {}
    )
}

@Preview
@Composable
fun WinSnackbarPreview() {
    WinSnackbar(
        word = "roble",
        onTryAgain = {},
        onDefinition = {}
    )
}

@Composable
fun LostSnackbar(
    secretWord: String,
    onTryAgain: () -> Unit,
    onDefinition: (String) -> Unit
) {
    SnackbarBox {
        Snackbar(
            action = {
                ActionButtons(
                    word = secretWord,
                    onTryAgain = onTryAgain,
                    onDefinition = onDefinition
                )
            }
        ) {
            Text(text = stringResource(R.string.lost, secretWord))
        }
    }
}

@Composable
fun WinSnackbar(
    word: String,
    onTryAgain: () -> Unit,
    onDefinition: (String) -> Unit
) {
    SnackbarBox {
        Snackbar(
            action = {
                ActionButtons(
                    word = word,
                    onTryAgain = onTryAgain,
                    onDefinition = onDefinition
                )
            }
        ) {
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

@Composable
private fun ActionButtons(
    word: String,
    onTryAgain: () -> Unit,
    onDefinition: (String) -> Unit
) {
    Row {
        Button(
            modifier = Modifier.padding(end = 5.dp),
            onClick = { onDefinition(word) }
        ) {
            Text(text = stringResource(R.string.definition))
        }
        Button(onClick = onTryAgain) {
            Text(text = stringResource(R.string.again))
        }
    }
}
