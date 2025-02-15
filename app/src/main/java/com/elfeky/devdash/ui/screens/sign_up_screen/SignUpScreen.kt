package com.elfeky.devdash.ui.screens.sign_up_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.elfeky.devdash.navigation.app_navigation.AppScreen
import com.elfeky.devdash.ui.common.component.CustomButton
import com.elfeky.devdash.ui.common.component.InputField
import com.elfeky.devdash.ui.theme.DevDashTheme
import com.elfeky.devdash.ui.utils.defaultButtonColor
import com.elfeky.devdash.ui.utils.gradientBackground

@Composable
fun SignUpScreen(modifier: Modifier = Modifier, navController: NavController) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var userName by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(brush = gradientBackground)
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Sign Up",
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(16.dp))
        Text(
            text = "Let’s create an account ",
            color = MaterialTheme.colorScheme.onSecondary,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Medium
        )
        Spacer(Modifier.height(32.dp))

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

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            InputField(
                value = firstName,
                onValueChange = { firstName = it },
                placeholderText = "First name",
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(4.dp))

            InputField(
                value = lastName,
                onValueChange = { lastName = it },
                placeholderText = "Last name",
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        InputField(
            value = userName,
            onValueChange = { userName = it },
            placeholderText = "User name",
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

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

        Spacer(modifier = Modifier.height(32.dp))

        CustomButton(
            text = "Sign Up",
            onClick = {
                // TODO Sign Up Button
                navController.navigate(AppScreen.VerifyEmailScreen.route + "/${AppScreen.MainScreen.route}/$email")
            },
            buttonColor = defaultButtonColor,
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp),
            enabled = (
                    email.isNotBlank()
                            and password.isNotBlank()
                            and firstName.isNotBlank()
                            and lastName.isNotBlank()
                            and userName.isNotBlank()
                            and confirmPassword.isNotBlank()
                            and (password == confirmPassword)
                    )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SignUpScreenPreview() {
    DevDashTheme {
        SignUpScreen(navController = rememberNavController())
    }
}