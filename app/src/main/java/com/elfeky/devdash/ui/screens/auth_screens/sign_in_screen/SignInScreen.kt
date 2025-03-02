package com.elfeky.devdash.ui.screens.auth_screens.sign_in_screen


import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.elfeky.devdash.ui.common.component.CustomButton
import com.elfeky.devdash.ui.common.component.InputField
import com.elfeky.devdash.ui.theme.DevDashTheme
import com.elfeky.devdash.ui.utils.defaultButtonColor

@Composable
fun SignInScreen(
    onSignInSuccess: () -> Unit,
    onSignUpClick: () -> Unit,
    onForgotPasswordClick: (email: String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel()
) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val loginState = viewModel.state.value

    LaunchedEffect(loginState.loggedIn) {
        if (loginState.loggedIn) {
            onSignInSuccess()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Sign In",
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(56.dp))

        Text(
            text = "Welcome Back",
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Medium
        )

        if (loginState.isLoading) {
            Spacer(modifier = Modifier.height(12.dp))
            CircularProgressIndicator(color = MaterialTheme.colorScheme.tertiary)
        }

        Spacer(modifier = Modifier.height(64.dp))

        InputField(
            value = email,
            onValueChange = { email = it },
            placeholderText = "Email",
            modifier = Modifier.fillMaxWidth(),
            isEmail = true,
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "Email"
                )
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        InputField(
            value = password,
            onValueChange = { password = it },
            placeholderText = "Password",
            modifier = Modifier.fillMaxWidth(),
            isPassword = true,
            imeAction = ImeAction.Done
        )

        TextButton(
            onClick = { onForgotPasswordClick(email) },
            modifier = Modifier.align(Alignment.End),
            enabled = email.isNotBlank()
        ) {
            Text(
                text = "Forgot Password?",
                color = Color.LightGray
            )
        }

        if (loginState.error.isNotBlank()) {
            Text(
                text = loginState.error,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.align(Alignment.Start)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        val signUpText = buildAnnotatedString {
            withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.onBackground)) {
                append("Don't have an account? ") // Improved wording
            }
            withStyle(
                style = SpanStyle(
                    color = MaterialTheme.colorScheme.tertiary,
                    textDecoration = TextDecoration.Underline
                )
            ) {
                append("Sign Up")
            }
        }

        TextButton(
            onClick = onSignUpClick,
            modifier = Modifier
                .padding(32.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = signUpText,
                fontSize = 16.sp,
            )
        }

        CustomButton(
            text = "Sign In",
            onClick = {
                viewModel.login(email = email, password = password)
            },
            buttonColor = defaultButtonColor,
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp),
            enabled = email.isNotBlank() && password.isNotBlank()
        )
        Log.i("loginState", loginState.toString())
    }
}

@Preview
@Composable
private fun SignInScreenPreview() {
    DevDashTheme {
        SignInScreen({}, {}, {})
    }
}