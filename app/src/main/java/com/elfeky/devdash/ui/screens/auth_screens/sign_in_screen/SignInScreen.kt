package com.elfeky.devdash.ui.screens.auth_screens.sign_in_screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.Composable
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.elfeky.devdash.navigation.app_navigation.AppScreen
import com.elfeky.devdash.ui.common.component.CustomButton
import com.elfeky.devdash.ui.common.component.InputField
import com.elfeky.devdash.ui.theme.DevDashTheme
import com.elfeky.devdash.ui.utils.defaultButtonColor
import com.elfeky.devdash.ui.utils.gradientBackground


@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val state = viewModel.state.value

//    val context = LocalContext.current
//    val sharedPref = context.getSharedPreferences(USER_DATA_FILE, Context.MODE_PRIVATE)
//    val savedAccessToken = sharedPref.getString(ACCESS_TOKEN_KEY, "")
//    val savedRefreshToken = sharedPref.getString(REFRESH_TOKEN_KEY, "")
//    Log.i("Tokens", "$savedAccessToken && $savedRefreshToken")

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = gradientBackground
            )
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "DevDash",
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(56.dp))
        Text(
            text = "Sign In",
            color = MaterialTheme.colorScheme.onSecondary,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Medium
        )

        if (state.isLoading) {
            Spacer(modifier = Modifier.height(12.dp))
            CircularProgressIndicator()
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

        Text(
            text = "Forget Password?",
            color = Color.LightGray,
            modifier = Modifier
                .clickable {
                    if (email.isNotBlank()) {
                        navController.navigate(
                            "${AppScreen.VerifyEmailScreen.route}/${AppScreen.ResetPasswordScreen.route}/$email"
                        )
                    }
                }
                .align(Alignment.End)
        )
        if (state.error != "") {
            Text(
                text = state.error,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.align(Alignment.Start)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        val signUpText = buildAnnotatedString {
            withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.onBackground)) {
                append("You don't have account? ")
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

        Text(
            text = signUpText,
            fontSize = 16.sp,
            modifier = Modifier
                .padding(32.dp)
                .align(Alignment.CenterHorizontally)
                .clickable { navController.navigate(AppScreen.SignUpScreen.route) }
        )

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
        if (state.loggedIn) {
            navController.navigate(AppScreen.MainScreen.route) {
                popUpTo(0) {
                    inclusive = true
                }
            }
        }
        Log.i("loginState", state.toString())
    }


}

@Preview
@Composable
private fun SignInScreenPreview() {
    DevDashTheme {
        val navController = rememberNavController()
        SignInScreen(navController = navController)
    }
}