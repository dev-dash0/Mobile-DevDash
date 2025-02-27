package com.elfeky.devdash.ui.common.card

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.elfeky.devdash.ui.common.Status
import com.elfeky.devdash.ui.common.card.component.CardContainer
import com.elfeky.devdash.ui.common.card.component.CardHeader
import com.elfeky.devdash.ui.common.card.component.CardTitle
import com.elfeky.devdash.ui.theme.DevDashTheme

@Composable
fun ProjectCard(
    companyName: String,
    date: String,
    status: Status,
    issueTitle: String,
    description: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    CardContainer(onClick = onClick, modifier = modifier) {
        CardHeader(
            projectName = companyName,
            date = date
        )
        CardTitle(
            status = status,
            issueTitle = issueTitle
        )
        Text(text = description)
    }
}

@Preview
@Composable
private fun CompanyCardPreview() {
    DevDashTheme {
        ProjectCard(
            companyName = "Company Name",
            date = "12 Feb | 18 Feb",
            status = Status.InProgress,
            issueTitle = "Project Name",
            description = "SwiftTech Solutions is an innovative software development company specializing in cutting-edge mobile and web applications. We deliver scalable, high-performance solutions tailored to businesses of all sizes, leveraging modern technologies to drive digital transformation.",
            onClick = {}
        )
    }
}