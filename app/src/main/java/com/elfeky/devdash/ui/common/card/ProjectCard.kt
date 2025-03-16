package com.elfeky.devdash.ui.common.card

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elfeky.devdash.ui.common.Status
import com.elfeky.devdash.ui.common.card.component.CardContainer
import com.elfeky.devdash.ui.common.card.component.CardTitle
import com.elfeky.devdash.ui.theme.DevDashTheme

@Composable
fun ProjectCard(
    date: String,
    status: Status,
    issueTitle: String,
    description: String,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    CardContainer(
        onClick = onClick,
        onLongClick = onLongClick,
        modifier = modifier,
        verticalSpaceBetweenItems = 16.dp
    ) {
        CardTitle(
            status = status,
            issueTitle = issueTitle,
            date = date
        )
        Text(text = description, Modifier.padding(horizontal = 8.dp))
    }
}

@Preview
@Composable
private fun CompanyCardPreview() {
    DevDashTheme {
        ProjectCard(
            date = "12 Feb | 18 Feb",
            status = Status.InProgress,
            issueTitle = "Project Name",
            description = "SwiftTech Solutions is an innovative software development company specializing in cutting-edge mobile and web applications. We deliver scalable, high-performance solutions tailored to businesses of all sizes, leveraging modern technologies to drive digital transformation.",
            onClick = {},
            onLongClick = {}
        )
    }
}