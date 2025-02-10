package com.elfeky.devdash.ui.common.dialogs.company

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.elfeky.devdash.ui.common.component.DialogContainer
import com.elfeky.devdash.ui.common.dialogs.company.components.CompanyDialogContent
import com.elfeky.devdash.ui.common.dialogs.model.CompanyDataModel
import com.elfeky.devdash.ui.theme.DevDashTheme

@Composable
fun CompanyDialog(
    onDismiss: () -> Unit,
    onSubmit: (CompanyDataModel) -> Unit,
    modifier: Modifier = Modifier
) {
    var title by remember { mutableStateOf("") }
    var websiteUrl by remember { mutableStateOf("") }
    var keywords by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedImage by remember { mutableStateOf<Uri?>(null) }

    DialogContainer(
        onCancel = onDismiss,
        onConfirm = {
            onSubmit(
                CompanyDataModel(
                    title,
                    websiteUrl,
                    keywords,
                    description,
                    selectedImage
                )
            )
        },
        title = "Add your company"
    ) {
        CompanyDialogContent(
            title = title,
            websiteUrl = websiteUrl,
            keywords = keywords,
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