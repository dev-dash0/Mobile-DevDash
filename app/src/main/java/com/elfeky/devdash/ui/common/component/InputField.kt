package com.elfeky.devdash.ui.common.component

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.elfeky.devdash.ui.theme.DevDashTheme
import com.elfeky.devdash.ui.theme.Gray

@Composable
fun InputField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholderText: String,
    modifier: Modifier = Modifier,
    isPassword: Boolean = false,
    isEmail: Boolean = false,
    singleLine: Boolean = true,
    imeAction: ImeAction = ImeAction.Next,
    trailingIcon: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null,
    textStyle: TextStyle = MaterialTheme.typography.titleMedium,
    shape: Shape = MaterialTheme.shapes.medium,
    colors: TextFieldColors = TextFieldDefaults.colors(
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent
    )
) {
    var passwordVisible by remember { mutableStateOf(false) }

    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(text = placeholderText, style = textStyle) },
        singleLine = singleLine,
        modifier = modifier,
        textStyle = textStyle,
        visualTransformation = if (isPassword && !passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = when {
                isPassword -> KeyboardType.Password
                isEmail -> KeyboardType.Email
                else -> KeyboardType.Text
            },
            imeAction = imeAction
        ),
        trailingIcon = trailingIcon ?: if (isPassword) {
            {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = if (passwordVisible) "Hide password" else "Show password"
                    )
                }
            }
        } else null,
        supportingText = supportingText,
        shape = shape,
        colors = colors
    )
}


@Preview(showBackground = true)
@Composable
fun PasswordTextFieldPreview(modifier: Modifier = Modifier) {
    DevDashTheme {
        var passwordValue by remember { mutableStateOf("") }
        InputField(
            value = passwordValue,
            onValueChange = { passwordValue = it },
            placeholderText = "Password",
            isPassword = true
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EmailTextFieldPreview(modifier: Modifier = Modifier) {
    DevDashTheme {
        var emailValue by remember { mutableStateOf("") }
        InputField(
            value = emailValue,
            onValueChange = { emailValue = it },
            placeholderText = "Email",
            isEmail = true,
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "Email",
                    tint = Gray
                )
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NormalTextFieldPreview(modifier: Modifier = Modifier) {
    DevDashTheme {
        var normalTextValue by remember { mutableStateOf("") }
        InputField(
            value = normalTextValue,
            onValueChange = { normalTextValue = it },
            placeholderText = "Name",
        )
    }
}