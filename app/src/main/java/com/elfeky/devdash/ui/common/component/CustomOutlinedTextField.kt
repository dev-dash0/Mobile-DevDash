package com.elfeky.devdash.ui.common.component

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.elfeky.devdash.ui.theme.DevDashTheme
import com.elfeky.devdash.ui.theme.Gray
import com.elfeky.devdash.ui.theme.White

@Composable
fun CustomOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    isPassword: Boolean = false,
    isEmail: Boolean = false,
    maxLines: Int = 1,
    imeAction: ImeAction = ImeAction.Next,
    trailingIcon: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null
) {
    var passwordVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label, style = MaterialTheme.typography.bodyLarge) },
        singleLine = true,
        modifier = modifier,
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
                        contentDescription = if (passwordVisible) "Hide password" else "Show password",
                        tint = Gray
                    )
                }
            }
        } else null,
        supportingText = supportingText,
        maxLines = maxLines,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.onBackground,
            unfocusedContainerColor = White,
            focusedContainerColor = White,
            focusedLabelColor = MaterialTheme.colorScheme.onBackground,
        ),
        shape = MaterialTheme.shapes.medium,
    )
}


@Preview(showBackground = true)
@Composable
fun PasswordTextFieldPreview(modifier: Modifier = Modifier) {
    DevDashTheme {
        var passwordValue by remember { mutableStateOf("") }
        CustomOutlinedTextField(
            value = passwordValue,
            onValueChange = { passwordValue = it },
            label = "Password",
            isPassword = true
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EmailTextFieldPreview(modifier: Modifier = Modifier) {
    DevDashTheme {
        var emailValue by remember { mutableStateOf("") }
        CustomOutlinedTextField(
            value = emailValue,
            onValueChange = { emailValue = it },
            label = "Email",
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
        CustomOutlinedTextField(
            value = normalTextValue,
            onValueChange = { normalTextValue = it },
            label = "Name",
        )
    }
}