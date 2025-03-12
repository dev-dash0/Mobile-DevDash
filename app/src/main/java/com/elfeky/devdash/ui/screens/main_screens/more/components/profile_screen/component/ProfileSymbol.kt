package com.elfeky.devdash.ui.screens.main_screens.more.components.profile_screen.component

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.elfeky.devdash.ui.theme.DevDashTheme

@Composable
fun ProfileSymbol(
    modifier: Modifier = Modifier,
    firstName: String,
    lastName: String,
    isUpdatingImage: MutableState<Boolean>
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(Color.LightGray),
        contentAlignment = Alignment.Center
    ) {
        AnimatedContent(targetState = isUpdatingImage.value) {
            if (it) {
                CircularProgressIndicator()
            } else {
                Text(
                    text = "${firstName[0]}${lastName[0]}",
                    color = Color.DarkGray,
                    modifier = Modifier.padding(9.dp),
                    fontSize = 34.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

    }
}

@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
private fun ProfileSymbolPreview() {
    DevDashTheme {
        ProfileSymbol(firstName = "Youssef", lastName = "Elfeky", isUpdatingImage = mutableStateOf(false))
    }
}