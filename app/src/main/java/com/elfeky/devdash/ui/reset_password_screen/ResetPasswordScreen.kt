package com.elfeky.devdash.ui.reset_password_screen

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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.elfeky.devdash.R
import com.elfeky.devdash.navigation.Screen
import com.elfeky.devdash.ui.common.PasswordTextField

@Composable
fun ResetPasswordScreen(
    modifier: Modifier = Modifier, navController: NavController
) {

    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.secondary, MaterialTheme.colorScheme.primary
                    ),
                    start = Offset(0f, 0f),
                    end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
                )
            )
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
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )
        Spacer(Modifier.height(32.dp))

        PasswordTextField(modifier = Modifier.fillMaxWidth(), onValueChange = { password = it })

        Spacer(modifier = Modifier.height(8.dp))

        PasswordTextField(
            modifier = Modifier.fillMaxWidth(),
            label = "Confirm password",
            onValueChange = { confirmPassword = it }
        )

        Spacer(Modifier.height(32.dp))

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp),
            onClick = {
                // TODO Reset Password Button
                navController.navigate(Screen.DoneScreen.route)
            },
            colors = ButtonColors(
                contentColor = Color.White,
                containerColor = MaterialTheme.colorScheme.onSurface,
                disabledContentColor = Color.White,
                disabledContainerColor = Color.Gray,
            ),
            // Condition to make Button enabled
            enabled = (

                    password.isNotBlank() and confirmPassword.isNotBlank() and (password == confirmPassword)),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = "Change Password"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ResetPasswordScreenPreview() {
    ResetPasswordScreen(navController = rememberNavController())
}