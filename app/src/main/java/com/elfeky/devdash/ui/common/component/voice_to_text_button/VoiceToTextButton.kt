package com.elfeky.devdash.ui.common.component.voice_to_text_button

import android.Manifest
import android.app.Application
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Mic
import androidx.compose.material.icons.rounded.Stop
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

@Composable
fun VoiceToTextButton(
    modifier: Modifier = Modifier,
    onResult: (String) -> Unit
) {

    val application = LocalContext.current.applicationContext as Application
    val voiceToTextParser = remember { VoiceToTextParser(application) }

    var canRecord by remember { mutableStateOf(false) }
    val recordAudioLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            canRecord = isGranted
        }
    )

    LaunchedEffect(key1 = recordAudioLauncher) {
        recordAudioLauncher.launch(Manifest.permission.RECORD_AUDIO)
    }

    val state by voiceToTextParser.state.collectAsState()
    if (state.spokenText.isNotEmpty()){
        onResult(state.spokenText)
    }

    Icon(
        imageVector =
        if (state.isSpeaking) Icons.Rounded.Stop
        else Icons.Rounded.Mic,
        contentDescription = "voice to text",
        tint = if (state.isSpeaking) Color.Red else Color.Green,
        modifier = modifier.clickable(onClick = {
            if (state.isSpeaking) {
                voiceToTextParser.stopListening()

            } else {
                voiceToTextParser.startListening()
            }
        })
    )

}