package com.elfeky.devdash.ui.common.component

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.elfeky.devdash.ui.theme.White
import com.elfeky.devdash.ui.utils.secondaryButtonColor

@Composable
fun CustomButton(
    text: String,
    onClick: () -> Unit,
    buttonColor: ButtonColors,
    shape: Shape = MaterialTheme.shapes.small,
    textColor: Color = White,
    textStyle: TextStyle = MaterialTheme.typography.titleMedium,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = shape,
        colors = buttonColor
    ) {
        Text(
            text = text,
            color = textColor,
            style = textStyle
        )
    }
}

@Preview
@Composable
private fun CustomTextButtonPreview() {
    CustomButton("Create", {}, secondaryButtonColor)
}