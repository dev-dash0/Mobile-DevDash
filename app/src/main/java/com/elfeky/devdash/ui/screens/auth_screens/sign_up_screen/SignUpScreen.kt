package com.elfeky.devdash.ui.screens.auth_screens.sign_up_screen


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.elfeky.devdash.ui.common.component.CustomButton
import com.elfeky.devdash.ui.common.component.DateTextField
import com.elfeky.devdash.ui.common.component.InputField
import com.elfeky.devdash.ui.screens.sign_up_screen.SignUpViewModel
import com.elfeky.devdash.ui.theme.DevDashTheme
import com.elfeky.devdash.ui.utils.defaultButtonColor
import com.elfeky.devdash.ui.utils.gradientBackground
import com.elfeky.domain.model.User

@Composable
fun SignUpScreen(
    onSignUpClick: (email: String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SignUpViewModel = hiltViewModel()
) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var userName by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var dateOfBirth by remember { mutableStateOf("") }

    val isEmailValid = remember(email) {
        viewModel.validateEmail(email)
    }
    val isPasswordValid = remember(password) {
        viewModel.validatePassword(password)
    }

    val state = viewModel.state.value

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(brush = gradientBackground)
            .padding(horizontal = 24.dp, vertical = 24.dp)
            .imePadding() // Add padding for the keyboard
            .verticalScroll(rememberScrollState()),
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
        if (state.isLoading) {
            Spacer(modifier = Modifier.height(12.dp))
            CircularProgressIndicator()
        }
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
        if (!isEmailValid) {
            Text(
                text = "Email is invalid",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(top = 4.dp)
            )
        }

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
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            placeholderText = "Phone number",
            modifier = Modifier.fillMaxWidth(),
            isPhoneNumber = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        DateTextField {
            dateOfBirth = it
        }

        Spacer(modifier = Modifier.height(8.dp))

        InputField(
            value = password,
            onValueChange = { password = it },
            placeholderText = "Password",
            modifier = Modifier.fillMaxWidth(),
            isPassword = true
        )
        if (!isPasswordValid) {
            Text(
                text = "Password must contain:\n- 1 uppercase letter\n- 1 lowercase letter\n- 1 number\n- 1 special character",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        InputField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            placeholderText = "Confirm Password",
            modifier = Modifier.fillMaxWidth(),
            isPassword = true,
            imeAction = ImeAction.Done
        )

        if (confirmPassword != password) {
            Text(
                text = "Passwords do not match",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(top = 4.dp)
            )
        }
        if (state.error != "") {
            Text(
                text = state.error,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.align(Alignment.Start)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        CustomButton(
            text = "Sign Up",
            onClick = {
                viewModel.signup(
                    User(
                        email = email,
                        firstName = firstName,
                        lastName = lastName,
                        phoneNumber = phoneNumber,
                        password = password,
                        birthday = dateOfBirth,
                        username = userName
                    )
                )
            },
            buttonColor = defaultButtonColor,
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp),
            enabled = (
                    isEmailValid
                            and firstName.isNotBlank()
                            and (firstName.length >= 3)
                            and lastName.isNotBlank()
                            and (lastName.length >= 3)
                            and userName.isNotBlank()
                            and (phoneNumber.length >= 11)
                            and dateOfBirth.isNotBlank()
                            and isPasswordValid
                            and (password == confirmPassword)
                    )
        )
        if (state.signedUp) {
            onSignUpClick(email)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SignUpScreenPreview() {
    DevDashTheme {
        SignUpScreen({})
    }
}