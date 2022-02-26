package org.m0skit0.android.palabros.presentation.playgrid

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.m0skit0.android.palabros.R
import org.m0skit0.android.palabros.presentation.CardDialog
import org.m0skit0.android.palabros.presentation.PalabrosButton

@Composable
fun HelpDialog(onDismiss: () -> Unit) {
    CardDialog(onDismissRequest = onDismiss) {
        HelpDialogContent(onDismiss)
    }
}

@Preview
@Composable
private fun HelpDialogContentPreview() {
    HelpDialogContent {}
}

@Composable
private fun HelpDialogContent(onDismiss: () -> Unit) {
    Column(Modifier.padding(10.dp)) {
        HelpTitle()
        Divider(
            modifier = Modifier
                .padding(top = 5.dp, bottom = 10.dp)
                .width(300.dp),
            color = Color.White
        )
        HelpTypeRow(drawableRes = R.drawable.red, descriptionRes = R.string.help_red)
        HelpTypeRow(drawableRes = R.drawable.yellow, descriptionRes = R.string.help_yellow)
        HelpTypeRow(drawableRes = R.drawable.green, descriptionRes = R.string.help_green)
        OkButton(onDismiss)
    }
}

@Composable
private fun HelpTitle() {
    Text(
        style = MaterialTheme.typography.h4,
        text = stringResource(id = R.string.help),
    )
}

@Composable
private fun HelpTypeRow(
    @DrawableRes drawableRes: Int,
    @StringRes descriptionRes: Int,
) {
    Row {
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

@Composable
private fun ColumnScope.OkButton(onDismiss: () -> Unit) {
    PalabrosButton(
        modifier = Modifier.align(Alignment.End),
        onClick = onDismiss,
        content = { Text(text = stringResource(android.R.string.ok)) },
    )
}
