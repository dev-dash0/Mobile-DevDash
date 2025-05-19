package com.elfeky.devdash.ui.common.component.avatar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elfeky.devdash.R
import com.elfeky.devdash.ui.common.dialogs.userList
import com.elfeky.devdash.ui.theme.DevDashTheme
import com.elfeky.devdash.ui.utils.avatarModifier
import com.elfeky.devdash.ui.utils.dashBorder
import com.elfeky.domain.model.account.UserProfile

@Composable
fun OverlayAvatars(
    users: List<UserProfile>,
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.onSecondary,
    content: @Composable (() -> Unit)
) {
    val reverseRotationModifier = Modifier.graphicsLayer { rotationY = 180f }
    val overlap by remember(users.size) {
        derivedStateOf {
            when (users.size) {
                1 -> 0
                in 2..4 -> -16
                in 5..10 -> -24
                else -> -30
            }.dp
        }
    }
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier
                .padding(4.dp)
                .then(reverseRotationModifier),
            horizontalArrangement = Arrangement.spacedBy(overlap),
            verticalAlignment = Alignment.CenterVertically
        ) {

            users.forEach { user ->
                Avatar(
                    user,
                    reverseRotationModifier,
                    containerColor
                )
            }
        }
        content()
    }
}

@Preview
@Composable
private fun OverlayAvatarsPreview() {
    DevDashTheme {
        OverlayAvatars(userList) {
            IconButton(
                onClick = {}, modifier = Modifier
                    .dashBorder(2.dp, 7.dp, 4.dp)
                    .avatarModifier(MaterialTheme.colorScheme.background)
            ) {
                Icon(
                    painter = painterResource(R.drawable.user_add_ic),
                    contentDescription = "Add Assignee",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}