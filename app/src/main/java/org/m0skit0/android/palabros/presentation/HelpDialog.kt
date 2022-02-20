package org.m0skit0.android.palabros.presentation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.m0skit0.android.palabros.R

@Preview
@Composable
fun HelpDialogPreview() {
    HelpDialog {}
}

@Composable
fun HelpDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = stringResource(id = R.string.help)) },
        text = {
            HelpContent()
        },
        confirmButton = { Button(onClick = onDismiss, content = { Text(text = stringResource(android.R.string.ok)) }) },
    )
}

@Preview
@Composable
fun HelpContentPreview() {
    HelpContent()
}

@Composable
fun HelpContent() {
    Column(verticalArrangement = Arrangement.Center) {
        HelpTypeRow(drawableRes = R.drawable.red, descriptionRes = R.string.help_red)
        HelpTypeRow(drawableRes = R.drawable.yellow, descriptionRes = R.string.help_yellow)
        HelpTypeRow(drawableRes = R.drawable.green, descriptionRes = R.string.help_green)
    }
}

@Composable
fun HelpTypeRow(
    @DrawableRes drawableRes: Int,
    @StringRes descriptionRes: Int,
) {
    Row(modifier = Modifier.padding(top = 20.dp)) {
        Image(
            modifier = Modifier.height(40.dp),
            painter = painterResource(id = drawableRes),
            contentDescription = ""
        )
        Text(
            modifier = Modifier
                .padding(start = 10.dp)
                .align(Alignment.CenterVertically),
            text = stringResource(id = descriptionRes)
        )
    }
}
