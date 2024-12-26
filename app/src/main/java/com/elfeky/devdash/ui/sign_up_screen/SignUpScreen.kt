package com.elfeky.devdash.ui.sign_up_screen

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.elfeky.devdash.navigation.app_navigation.AppScreen
import com.elfeky.devdash.ui.common.EmailTextField
import com.elfeky.devdash.ui.common.NormalTextField
import com.elfeky.devdash.ui.common.PasswordTextField

@Composable
fun SignUpScreen(modifier: Modifier = Modifier, navController: NavController) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var userName by remember { mutableStateOf("") }



    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.secondary,
                        MaterialTheme.colorScheme.primary
                    ),
                    start = Offset(0f, 0f),
                    end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
                )
            )
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "SignUp",
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(16.dp))
        Text(
            text = "Let’s create an account ",
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(32.dp))

        EmailTextField(modifier = Modifier.fillMaxWidth(), onValueChange = { email = it })

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            NormalTextField(
                modifier = Modifier.weight(1f),
                label = "First name",
                onValueChange = { firstName = it }
            )
            Spacer(modifier = Modifier.width(4.dp))
            NormalTextField(
                modifier = Modifier.weight(1f),
                label = "Last ame",
                onValueChange = { lastName = it }
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        NormalTextField(
            modifier = Modifier.fillMaxWidth(),
            label = "User name",
            onValueChange = { userName = it }
        )

        Spacer(modifier = Modifier.height(8.dp))

        PasswordTextField(modifier = Modifier.fillMaxWidth(), onValueChange = { password = it })

        Spacer(modifier = Modifier.height(8.dp))

        PasswordTextField(
            modifier = Modifier.fillMaxWidth(),
            label = "Confirm password",
            onValueChange = { confirmPassword = it }
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp),
            onClick = {
                // TODO Sign Up Button
                navController.navigate(AppScreen.VerifyEmailScreen.route+"/${AppScreen.MainScreen.route}/$email")
            },
            colors = ButtonColors(
                contentColor = Color.White,
                containerColor = MaterialTheme.colorScheme.onSurface,
                disabledContentColor = Color.White,
                disabledContainerColor = Color.Gray,
            ),
            // Condition to make Button enabled
            enabled = (
                    email.isNotBlank()
                            and password.isNotBlank()
                            and firstName.isNotBlank()
                            and lastName.isNotBlank()
                            and userName.isNotBlank()
                            and confirmPassword.isNotBlank()
                            and (password == confirmPassword)
                    ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = "Sign Up"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SignUpScreenPreview() {
    SignUpScreen(navController = rememberNavController())
}