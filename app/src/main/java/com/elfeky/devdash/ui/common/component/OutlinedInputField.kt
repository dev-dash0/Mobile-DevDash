package com.elfeky.devdash.ui.common.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.elfeky.devdash.ui.theme.DevDashTheme
import com.elfeky.devdash.ui.theme.OffWhite

@Composable
fun OutlinedInputField(
    value: String,
    placeholderText: String,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    singleLine: Boolean = false,
    imeAction: ImeAction = ImeAction.Done,
    shape: Shape = MaterialTheme.shapes.medium,
    textStyle: TextStyle = MaterialTheme.typography.titleSmall,
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors(
        focusedTextColor = OffWhite,
        unfocusedTextColor = OffWhite,
    )
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChanged,
        modifier = modifier.fillMaxWidth(),
        singleLine = singleLine,
        textStyle = textStyle,
        placeholder = { Text(placeholderText) },
        keyboardOptions = KeyboardOptions(imeAction = imeAction),
        shape = shape,
        colors = colors
    )
}


@Preview(showBackground = true)
@Composable
private fun CustomTextFieldPreview() {
    var title by remember { mutableStateOf("") }
    DevDashTheme {
        OutlinedInputField(
            value = title,
            placeholderText = "Description...........",
            onValueChanged = { title = it },
        )
    }
}