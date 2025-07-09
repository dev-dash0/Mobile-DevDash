package com.elfeky.devdash.ui.common.component.avatar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.elfeky.devdash.R
import com.elfeky.devdash.ui.common.userList
import com.elfeky.devdash.ui.theme.DevDashTheme
import com.elfeky.devdash.ui.utils.dashBorder
import com.elfeky.domain.model.account.UserProfile

@Composable
fun AvatarStack(
    users: List<UserProfile>,
    modifier: Modifier = Modifier,
    avatarSize: Dp = 32.dp,
    overlapFactor: Float = 0.4f,
    maxVisibleAvatars: Int = Int.MAX_VALUE,
    containerColor: Color = MaterialTheme.colorScheme.onSecondary,
    content: @Composable (() -> Unit)? = null
) {
    if (users.isEmpty()) {
        return
    }

    val visiblePainters = users.take(maxVisibleAvatars)
    val overlapAmountPx = with(LocalDensity.current) { (avatarSize * overlapFactor).toPx() }
    val avatarSizePx = with(LocalDensity.current) { avatarSize.toPx() }
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Layout(
            content = {
                visiblePainters.forEachIndexed { index, painter ->
                    Box(modifier = Modifier.layoutId("avatar_$index")) {
                        Avatar(
                            user = users[index],
                            size = avatarSize,
                            backgroundColor = containerColor
                        )
                    }
                }
            }
        ) { measurables, constraints ->
            if (measurables.isEmpty()) {
                return@Layout layout(0, 0) {}
            }

            val placeables = measurables.map {
                it.measure(Constraints.fixed(avatarSizePx.toInt(), avatarSizePx.toInt()))
            }

            val stackWidth = if (placeables.isNotEmpty()) {
                avatarSizePx + (placeables.size - 1) * (avatarSizePx - overlapAmountPx)
            } else {
                0f
            }.coerceAtLeast(0f)

            val stackHeight = avatarSizePx

            layout(stackWidth.toInt(), stackHeight.toInt()) {
                var currentX = 0f
                placeables.forEach { placeable ->
                    placeable.placeRelative(currentX.toInt(), 0)
                    currentX += (avatarSizePx - overlapAmountPx)
                }
            }
        }

        content?.invoke()
    }
}

@Preview
@Composable
private fun OverlayAvatarsPreview() {
    DevDashTheme {
        AvatarStack(userList.take(3)) {
            IconButton(
                onClick = {},
                modifier = Modifier
                    .dashBorder(2.dp, 7.dp, 4.dp)
                    .padding(2.dp)
                    .size(32.dp)
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