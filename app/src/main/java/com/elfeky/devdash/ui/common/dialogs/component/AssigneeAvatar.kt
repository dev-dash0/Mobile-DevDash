package com.elfeky.devdash.ui.common.dialogs.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.elfeky.devdash.R
import com.elfeky.devdash.ui.common.dialogs.issue.model.UserUiModel
import com.elfeky.devdash.ui.theme.BlueGray
import com.elfeky.devdash.ui.theme.Gray
import com.elfeky.devdash.ui.theme.White

@Composable
fun AssigneeAvatar(assignee: UserUiModel) {
    SubcomposeAsyncImage(
        model = assignee.avtar,
        contentDescription = "Assignee Image",
        modifier = Modifier
            .border(2.dp, White, CircleShape)
            .padding(2.dp)
            .clip(CircleShape)
            .background(BlueGray)
            .size(32.dp),
        loading = {
            Icon(
                painter = painterResource(id = R.drawable.person),
                contentDescription = "Assignee Image",
                modifier = Modifier
                    .border(2.dp, White, CircleShape)
                    .padding(2.dp)
                    .clip(CircleShape)
                    .size(32.dp),
                tint = White
            )
        },
        error = {
            Box(
                modifier = Modifier
                    .background(Gray)
                    .padding(2.dp)
                    .clip(CircleShape)
                    .size(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = assignee.name.take(2).uppercase(),
                    style = MaterialTheme.typography.labelMedium,
                    color = White,
                    textAlign = TextAlign.Center
                )
            }
        }
    )
}

@Preview
@Composable
private fun AssigneeAvatarPreview() {
    AssigneeAvatar(assignee = UserUiModel("John Doe", ""))
}