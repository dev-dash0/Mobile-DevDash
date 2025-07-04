package com.elfeky.devdash.ui.screens.details_screens.sprint.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentsBottomSheet(
    showBottomSheet: Boolean,
    modifier: Modifier = Modifier,
    sheetState: SheetState = rememberModalBottomSheetState(),
    onDismissRequest: () -> Unit
) {
    if (showBottomSheet) {
        ModalBottomSheet(
            modifier = modifier.fillMaxSize(),
            sheetState = sheetState,
            onDismissRequest = onDismissRequest
        ) {
            Text(
                "Swipe up to open sheet. Swipe down to dismiss.",
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}