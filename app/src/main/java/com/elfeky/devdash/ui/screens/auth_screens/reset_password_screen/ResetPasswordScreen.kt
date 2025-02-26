package com.elfeky.devdash.ui.screens.auth_screens.reset_password_screen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elfeky.devdash.R
import com.elfeky.devdash.ui.common.component.CustomButton
import com.elfeky.devdash.ui.common.component.InputField
import com.elfeky.devdash.ui.theme.DevDashTheme
import com.elfeky.devdash.ui.utils.defaultButtonColor
import com.elfeky.devdash.ui.utils.gradientBackground

@Composable
fun ResetPasswordScreen(onConfirmClick: () -> Unit, modifier: Modifier = Modifier) {
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    val context = LocalContext.current
    var showToast by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(brush = gradientBackground)
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.reset_password),
            contentDescription = "Reset Password",
            modifier = Modifier
                .size(128.dp)
                .clip(CircleShape)
        )
        Spacer(Modifier.height(32.dp))
        Text(
            text = "Reset your password",
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
        )
        Spacer(Modifier.height(32.dp))

        InputField(
            value = password,
            onValueChange = { password = it },
            placeholderText = "Password",
            modifier = Modifier.fillMaxWidth(),
            isPassword = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        InputField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            placeholderText = "Confirm Password",
            modifier = Modifier.fillMaxWidth(),
            isPassword = true,
            imeAction = ImeAction.Done
        )

        Spacer(Modifier.height(32.dp))

        CustomButton(
            text = "Change Password",
            onClick = {
                onConfirmClick()
                showToast = true
            },
            buttonColor = defaultButtonColor,
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp),
            enabled = (password.isNotBlank() && confirmPassword.isNotBlank() && (password == confirmPassword))
        )
    }
    if (showToast) {
        Toast.makeText(context, "Password changed successfully", Toast.LENGTH_SHORT).show()
        showToast = false
    }
}

@Preview(showBackground = true)
@Composable
private fun ResetPasswordScreenPreview() {
    DevDashTheme { ResetPasswordScreen({}) }
}