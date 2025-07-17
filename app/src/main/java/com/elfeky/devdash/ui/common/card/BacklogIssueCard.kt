package com.elfeky.devdash.ui.common.card

import android.content.ClipData
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.draganddrop.dragAndDropSource
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draganddrop.DragAndDropTransferData
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.elfeky.devdash.ui.common.dropdown_menu.model.toPriority
import com.elfeky.devdash.ui.common.issueList
import com.elfeky.devdash.ui.theme.DevDashTheme
import com.elfeky.domain.model.issue.Issue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BacklogIssueCard(
    issue: Issue,
    modifier: Modifier = Modifier,
    onIssueClicked: (issue: Issue) -> Unit
) {
    Card(
        modifier = modifier
            .height(IntrinsicSize.Max)
            .fillMaxWidth()
            .dragAndDropSource {
                detectTapGestures(
                    onPress = { onIssueClicked(issue) },
                    onLongPress = {
                        Log.d("BacklogSection", "Long Pressed")
                        startTransfer(
                            DragAndDropTransferData(
                                ClipData.newPlainText(
                                    "issue id",
                                    issue.id.toString()
                                )
                            )
                        )
                    }
                )
            },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerHigh.copy(
                alpha = .5f
            )
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            VerticalDivider(
                modifier = Modifier
                    .fillMaxHeight()
                    .clip(CircleShape),
                thickness = 4.dp,
                color = issue.priority.toPriority().color
            )


            Text(
                text = issue.title,
                modifier = Modifier.weight(1f),
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 0.sp
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview
@Composable
private fun BacklogIssueCardPreview() {
    DevDashTheme { BacklogIssueCard(issueList[0]) {} }
}