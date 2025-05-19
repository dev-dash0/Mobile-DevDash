package com.elfeky.devdash.ui.common.dialogs.company

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.elfeky.devdash.ui.common.dialogs.company.components.CompanyDialogContent
import com.elfeky.devdash.ui.common.dialogs.company.model.CompanyUiModel
import com.elfeky.devdash.ui.common.dialogs.component.DialogContainer
import com.elfeky.devdash.ui.theme.DevDashTheme

@Composable
fun CompanyDialog(
    onDismiss: () -> Unit,
    onSubmit: (CompanyUiModel) -> Unit,
    modifier: Modifier = Modifier,
    state: CompanyUiModel = CompanyUiModel("", "", "", ""),
) {
    var title by remember { mutableStateOf(state.title) }
    var websiteUrl by remember { mutableStateOf(state.websiteUrl) }
    var keywords by remember { mutableStateOf(state.keywords) }
    var description by remember { mutableStateOf(state.description) }
    var selectedImage by remember { mutableStateOf(state.logoUri) }

    DialogContainer(
        onDismiss = onDismiss,
        onConfirm = {
            onSubmit(
                CompanyUiModel(
                    title,
                    websiteUrl,
                    keywords,
                    description,
                    selectedImage
                )
            )
        },
        title = "Add your company",
        confirmEnable = title.isNotEmpty()
                && websiteUrl.isNotEmpty()
                && keywords.isNotEmpty()
                && description.isNotEmpty()
    ) {
        CompanyDialogContent(
            title = title,
            websiteUrl = websiteUrl,
            keywords = keywords,
            logo = selectedImage,
            description = description,
            onTitleChange = { title = it },
            onWebsiteUrlChange = { websiteUrl = it },
            onKeywordsChange = { keywords = it },
            onDescriptionChange = { description = it },
            modifier = modifier,
            onImageSelected = { selectedImage = it }
        )
    }
}

@Preview
@Composable
private fun CompanyDialogPreview() {
    DevDashTheme { CompanyDialog({}, {}) }
}