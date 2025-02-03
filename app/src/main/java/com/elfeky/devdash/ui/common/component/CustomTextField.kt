package com.elfeky.devdash.ui.common.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.elfeky.devdash.ui.theme.DevDashTheme
import com.elfeky.devdash.ui.theme.OffWhite
import com.elfeky.devdash.ui.theme.White

@Composable
fun CustomTextField(
    value: String,
    placeholderText: String,
    onTextChange: (String) -> Unit,
    textColor: Color,
    placeholderColor: Color,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.titleLarge,
    imeAction: ImeAction = ImeAction.Next
) {
    var isFocused by remember { mutableStateOf(false) }

    TextField(
        value = value,
        onValueChange = onTextChange,
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged { isFocused = it.isFocused },
        textStyle = textStyle.copy(color = textColor),
        placeholder = {
            if (!isFocused)
                Text(
                    placeholderText,
                    style = textStyle.copy(color = placeholderColor)
                )
        },
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            cursorColor = White
        ),
        keyboardOptions = KeyboardOptions(imeAction = imeAction)
    )
}


@Preview
@Composable
private fun CustomTextFieldPreview() {
    var title by remember { mutableStateOf("") }
    DevDashTheme {
        CustomTextField(
            value = title,
            placeholderText = "Untitled Issue",
            onTextChange = { title = it },
            textColor = White,
            placeholderColor = OffWhite
        )
    }
}