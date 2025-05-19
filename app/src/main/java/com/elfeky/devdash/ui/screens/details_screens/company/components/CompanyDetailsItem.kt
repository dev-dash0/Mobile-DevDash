package com.elfeky.devdash.ui.screens.details_screens.company.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elfeky.devdash.ui.common.component.TagFlowLayout
import com.elfeky.devdash.ui.common.component.avatar.Avatar
import com.elfeky.devdash.ui.common.component.avatar.MembersAvatar
import com.elfeky.devdash.ui.common.dialogs.component.LabelledContentHorizontal
import com.elfeky.devdash.ui.common.dialogs.component.LabelledContentVertical
import com.elfeky.devdash.ui.common.dialogs.userList
import com.elfeky.devdash.ui.theme.DevDashTheme
import com.elfeky.domain.model.account.UserProfile

@Composable
fun CompanyDetailsItem(
    code: String,
    url: String,
    members: List<UserProfile>,
    owner: UserProfile,
    tags: List<String>,
    onShowAllMembers: (List<UserProfile>) -> Unit,
    onShowAllOwners: (UserProfile) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        LabelledContentHorizontal("Code:") {
            CopyableText(code, modifier = Modifier.weight(.75f))
        }
        LabelledContentHorizontal("URL:") {
            CopyableText(url, modifier = Modifier.weight(.75f))
        }
        LabelledContentHorizontal("Members:") {
            MembersAvatar(
                users = members,
                modifier = Modifier
                    .weight(.75f)
                    .clickable { onShowAllMembers(members) }
            )
        }
        LabelledContentHorizontal("Owner:") {
            Row(modifier = Modifier.weight(.75f)) {
                Avatar(
                    user = owner,
                    modifier = Modifier.clickable { onShowAllOwners(owner) }
                )
            }
        }
        LabelledContentVertical("Tags:") {
            TagFlowLayout(tags = tags)
        }
    }
}


@Preview
@Composable
fun PreviewCompanyDetailsItem() {
    val sampleOwner = userList[0]

    val sampleTags =
        listOf("Technology", "Software", "Startup", "FinTech", "Mobile", "Web Development", "AI")
    DevDashTheme {
        CompanyDetailsItem(
            code = "XYZ789",
            url = "https://www.example.com",
            members = userList,
            owner = sampleOwner,
            tags = sampleTags,
            onShowAllMembers = {},
            onShowAllOwners = {}
        )
    }
}
