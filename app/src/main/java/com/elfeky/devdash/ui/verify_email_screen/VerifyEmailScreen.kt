package com.elfeky.devdash.ui.verify_email_screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.elfeky.devdash.R
import com.elfeky.devdash.navigation.Screen
import com.elfeky.devdash.ui.verify_email_screen.components.OtpTextField

@Composable
fun VerifyEmailScreen(
    navController: NavController,
    destination: String,
    email: String,
    modifier: Modifier = Modifier
) {

    var otp by remember { mutableStateOf("") }
    var otpInputFilled by remember { mutableStateOf(false) }



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
        Image(
            painter = painterResource(id = R.drawable.verify_email),
            contentDescription = "Verify Email",
            modifier = Modifier
                .size(128.dp)
                .clip(CircleShape)
        )
        Spacer(Modifier.height(32.dp))
        Text(
            text = "We have sent you an OTP",
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = "check your email for OTP code ",
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 16.sp
        )
        Spacer(Modifier.height(16.dp))
        OtpTextField(otpText = otp, otpCount = 4) { value, filled ->
            otp = value
            otpInputFilled = filled
            if (otpInputFilled) {
                Log.i("destination", destination)
                navController.navigate(destination)
            }
        }
        Spacer(Modifier.height(16.dp))

        Text(
            text = buildAnnotatedString {

                withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.onBackground)) {
                    append("Didn't get an email?")
                }

                withStyle(
                    style = SpanStyle(
                        color = Color.Blue,
                        textDecoration = TextDecoration.Underline
                    )
                ) {
                    append("Resend email")
                }
            },
            fontSize = 16.sp,
            modifier = Modifier
                .clickable(onClick = {
                    // TODO Resend Email
                })
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun VerifyEmailScreenPreview() {
    VerifyEmailScreen(
        navController = rememberNavController(),
        destination = Screen.ResetPasswordScreen.route,
        email = ""
    )
}