package com.elfeky.devdash.ui.common.component.avatar

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.elfeky.devdash.R
import com.elfeky.devdash.ui.common.dialogs.userList
import com.elfeky.devdash.ui.theme.DevDashTheme
import com.elfeky.devdash.ui.theme.White
import com.elfeky.devdash.ui.utils.avatarModifier
import com.elfeky.domain.model.account.UserProfile

@Composable
fun Avatar(
    user: UserProfile,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.onSecondary
) {
    SubcomposeAsyncImage(
        model = user.imageUrl,
        contentDescription = "Assignee Image",
        modifier = modifier
            .border(2.dp, White, CircleShape)
            .avatarModifier(backgroundColor),
        loading = {
            Icon(
                painter = painterResource(id = R.drawable.person_ic),
                contentDescription = "Assignee Image",
                tint = White
            )
        },
        error = {
            Box(contentAlignment = Alignment.Center) {
                Text(
                    text = "${user.firstName.uppercase()[0]}${user.lastName.uppercase()[0]}",
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
private fun AvatarPreview() {
    DevDashTheme { Avatar(user = userList[0]) }
}