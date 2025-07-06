package com.elfeky.devdash.ui.screens.details_screens.company.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.elfeky.devdash.ui.common.component.TagFlowLayout
import com.elfeky.devdash.ui.common.dialogs.component.LabelledContentHorizontal
import com.elfeky.devdash.ui.common.dialogs.component.LabelledContentVertical
import com.elfeky.devdash.ui.common.userList
import com.elfeky.devdash.ui.theme.DevDashTheme
import com.elfeky.domain.model.tenant.Tenant

@Composable
fun CompanyInfoPage(
    tenant: Tenant?,
    modifier: Modifier = Modifier,
    onRemoveMemberClick: (Int) -> Unit,
    onCopyTextClicked: (text: String) -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (tenant?.role == "Admin") {
            LabelledContentHorizontal("Code:") {
                CopyableText(
                    tenant.tenantCode,
                    modifier = Modifier.weight(.75f),
                    onCopyClick = { onCopyTextClicked(tenant.tenantCode) }
                )
            }
        }

        LabelledContentHorizontal("URL:") {
            CopyableText(
                tenant?.tenantUrl ?: "",
                modifier = Modifier.weight(.75f),
                onCopyClick = { onCopyTextClicked(tenant?.tenantUrl ?: "") }
            )
        }

        LabelledContentHorizontal("Members:") {
            Row(modifier = Modifier.weight(.75f), horizontalArrangement = Arrangement.End) {
                MembersMenu(
                    tenant?.joinedUsers ?: emptyList(),
                    tenant?.role == "Admin",
                    onRemoveMemberClick
                )
            }
        }

        LabelledContentVertical("Tags:") {
            tenant?.keywords?.let {
                TagFlowLayout(
                    tags = it.split(","),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
            }
        }

        LabelledContentVertical("Description:") {
            tenant?.let {
                Text(
                    it.description,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = .8f),
                    fontSize = 14.sp,
                )
            }
        }
    }
}

@Preview
@Composable
private fun CompanyInfoPagePreview() {
    val tenant by remember {
        mutableStateOf(
            Tenant(
                name = "Preview Company Name",
                image = null,
                tenantCode = "PRE-001",
                tenantUrl = "https://previewcompany.com",
                joinedUsers = userList,
                owner = userList[0],
                keywords = "Technology,Software,Startup,FinTech",
                description = "a dynamic and forward-thinking organization dedicated to delivering innovative solutions that empower businesses to achieve their full potential. We specialize in partnering with clients to understand their unique challenges and opportunities, leveraging our expertise in [mention a general area",
                id = 5,
                ownerID = 1,
                role = null
            )
        )
    }

    DevDashTheme {
        CompanyInfoPage(tenant, onRemoveMemberClick = {}, onCopyTextClicked = {})
    }
}