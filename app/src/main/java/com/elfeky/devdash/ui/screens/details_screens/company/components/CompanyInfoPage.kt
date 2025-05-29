package com.elfeky.devdash.ui.screens.details_screens.company.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.elfeky.devdash.ui.common.component.TagFlowLayout
import com.elfeky.devdash.ui.common.dialogs.component.LabelledContentHorizontal
import com.elfeky.devdash.ui.common.dialogs.component.LabelledContentVertical
import com.elfeky.devdash.ui.screens.details_screens.company.CompanyDetailsUiState

@Composable
fun CompanyInfoPage(
    state: CompanyDetailsUiState,
    isOwner: Boolean,
    modifier: Modifier = Modifier,
    onRemoveMemberClick: (Int) -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (isOwner) {
            LabelledContentHorizontal("Code:") {
                CopyableText(state.tenant?.tenantCode ?: "", modifier = Modifier.weight(.75f))
            }
        }

        LabelledContentHorizontal("URL:") {
            CopyableText(state.tenant?.tenantUrl ?: "", modifier = Modifier.weight(.75f))
        }

        LabelledContentHorizontal("Members:") {
            Row(modifier = Modifier.weight(.75f), horizontalArrangement = Arrangement.End) {
                MembersMenu(state.tenant?.joinedUsers ?: emptyList(), isOwner, onRemoveMemberClick)
            }
        }

        LabelledContentVertical("Tags:") {
            state.tenant?.keywords?.let { TagFlowLayout(tags = it.split(",")) }
        }

        LabelledContentVertical("Description:") {
            state.tenant?.let {
                Text(
                    it.description,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = .8f),
                    fontSize = 14.sp,
                )
            }
        }
    }
}