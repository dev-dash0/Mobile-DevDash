package com.elfeky.devdash.ui.common.component.avatar

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elfeky.devdash.ui.common.dialogs.userList
import com.elfeky.devdash.ui.theme.DevDashTheme
import com.elfeky.devdash.ui.theme.White
import com.elfeky.devdash.ui.utils.avatarModifier
import com.elfeky.domain.model.account.UserProfile

@Composable
fun MembersAvatar(
    users: List<UserProfile>,
    modifier: Modifier = Modifier,
    maxShownUsers: Int = 5
) {
    OverlayAvatars(
        users.take(maxShownUsers),
        modifier = modifier
    ) {
        if (users.size - maxShownUsers != 0) {
            Box(
                modifier = Modifier
                    .padding(start = 12.dp)
                    .border(1.dp, White, CircleShape)
                    .avatarModifier(MaterialTheme.colorScheme.onSecondary),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "+${users.size - maxShownUsers}",
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(4.dp)
                )
            }
        }
    }
}

@Preview
@Composable
private fun MembersAvatarPreview() {
    DevDashTheme {
        MembersAvatar(userList)
    }
}