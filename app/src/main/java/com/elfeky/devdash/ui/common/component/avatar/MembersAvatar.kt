package com.elfeky.devdash.ui.common.component.avatar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elfeky.devdash.ui.common.userList
import com.elfeky.devdash.ui.theme.DevDashTheme
import com.elfeky.domain.model.account.UserProfile

@Composable
fun MembersAvatar(
    users: List<UserProfile>,
    modifier: Modifier = Modifier,
    maxShownUsers: Int = 5
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AvatarStack(
            users.take(maxShownUsers),
            modifier = modifier
        )
        if (users.size > maxShownUsers) {
            Text(
                "+${users.size - maxShownUsers}",
                color = MaterialTheme.colorScheme.onBackground
            )
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