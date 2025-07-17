package com.elfeky.devdash.ui.common.card

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elfeky.devdash.ui.common.card.component.CardContainer
import com.elfeky.devdash.ui.common.companySearchList
import com.elfeky.devdash.ui.theme.DevDashTheme
import com.elfeky.domain.model.search.TenantSearch

@Composable
fun CompanySearchCard(tenant: TenantSearch, modifier: Modifier = Modifier) {
    CardContainer(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(16.dp),
        verticalSpaceBetweenItems = 8.dp
    ) {
        Text(
            text = tenant.name,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Medium
        )

        Text(
            tenant.description,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Preview
@Composable
private fun CompanySearchCardPreview() {
    DevDashTheme { CompanySearchCard(companySearchList[0]) }
}