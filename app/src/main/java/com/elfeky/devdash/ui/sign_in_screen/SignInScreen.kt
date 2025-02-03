package com.elfeky.devdash.ui.sign_in_screen

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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.elfeky.devdash.navigation.app_navigation.AppScreen
import com.elfeky.devdash.ui.common.component.CustomButton
import com.elfeky.devdash.ui.common.component.CustomOutlinedTextField
import com.elfeky.devdash.ui.theme.DevDashTheme
import com.elfeky.devdash.ui.theme.Gray
import com.elfeky.devdash.ui.utils.defaultButtonColor
import com.elfeky.devdash.ui.utils.gradientBackground

@Composable
fun SignInScreen(modifier: Modifier = Modifier, navController: NavController) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

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
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(56.dp))
        Text(
            text = "SignIn",
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(64.dp))

        CustomOutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = "Email",
            modifier = Modifier.fillMaxWidth(),
            isEmail = true,
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "Email",
                    tint = Gray
                )
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        CustomOutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = "Password",
            modifier = Modifier.fillMaxWidth(),
            isPassword = true
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
        Spacer(modifier = Modifier.height(8.dp))

        val signUpText = buildAnnotatedString {
            withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.onBackground)) {
                append("You don't have account? ")
            }
            withStyle(
                style = SpanStyle(
                    color = Color.Blue,
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
            onClick = { /*TODO Sign In Button*/ },
            buttonColor = defaultButtonColor,
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp),
            enabled = email.isNotBlank() && password.isNotBlank()
        )
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