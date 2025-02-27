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
fun CompanyCard(
    companyName: String,
    date: String,
    status: Status,
    issueTitle: String,
    description: String,
    modifier: Modifier = Modifier
) {
    CardContainer(modifier) {
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
        CompanyCard(
            "Company Name",
            "12 Feb | 18 Feb",
            Status.InProgress,
            "Project Name",
            "SwiftTech Solutions is an innovative software development company specializing in cutting-edge mobile and web applications. We deliver scalable, high-performance solutions tailored to businesses of all sizes, leveraging modern technologies to drive digital transformation."
        )
    }
}