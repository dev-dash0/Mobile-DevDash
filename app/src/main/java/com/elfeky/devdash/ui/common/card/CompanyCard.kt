package com.elfeky.devdash.ui.common.card

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.elfeky.devdash.ui.common.companyList
import com.elfeky.devdash.ui.theme.DevDashTheme
import com.elfeky.devdash.ui.utils.ImageUtils.base64ToBitmap
import com.elfeky.devdash.ui.utils.glassGradient
import com.elfeky.domain.model.tenant.Tenant

@Composable
fun CompanyCard(
    company: Tenant,
    onClick: (id: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val bitmapImage by remember(company.image) { mutableStateOf(base64ToBitmap(company.image)) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(250.dp)
            .clickable(onClick = { onClick(company.id) }),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomStart) {
            AsyncImage(
                model = ImageRequest
                    .Builder(LocalContext.current)
                    .data(bitmapImage ?: company.image)
                    .crossfade(true)
                    .listener(
                        onError = { _, result ->
                            Log.e("CoilError", "Failed to load image", result.throwable)
                        }
                    )
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize()
            )
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(glassGradient)
            )
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = company.name,
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Medium
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "URL: ${company.tenantUrl}",
                        color = MaterialTheme.colorScheme.onBackground.copy(.8f),
                        style = MaterialTheme.typography.titleMedium
                    )

                    Text(
                        text = company.description,
                        color = MaterialTheme.colorScheme.onBackground.copy(.8f),
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun CompanyCardPreview() {
    DevDashTheme {
        CompanyCard(companyList[0], {})
    }
}