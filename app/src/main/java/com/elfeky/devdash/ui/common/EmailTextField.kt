package com.elfeky.devdash.ui.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun EmailTextField(modifier: Modifier = Modifier, onValueChange: (String) -> Unit) {


    var email by remember { mutableStateOf("") }

    OutlinedTextField(
        value = email,
        onValueChange = {
            email = it
            onValueChange(it)
        },
        label = {
            Text(
                text = "Enter your email address",
            )
        },
        maxLines = 1,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.onBackground,
            unfocusedContainerColor = Color.White,
            focusedContainerColor = Color.White,
            focusedLabelColor = MaterialTheme.colorScheme.onBackground,
        ),
        shape = RoundedCornerShape(7.dp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        modifier = modifier,
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.Email,
                contentDescription = "email icon",
                modifier = Modifier.size(24.dp),
                tint = Color.Gray
            )
        }


    )
}