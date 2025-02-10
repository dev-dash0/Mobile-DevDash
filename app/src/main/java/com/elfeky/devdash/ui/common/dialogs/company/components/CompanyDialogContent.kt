package com.elfeky.devdash.ui.common.dialogs.company.components

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elfeky.devdash.ui.common.component.InputField
import com.elfeky.devdash.ui.theme.DevDashTheme

@Composable
fun CompanyDialogContent(
    title: String,
    websiteUrl: String,
    keywords: String,
    description: String,
    onTitleChange: (String) -> Unit,
    onWebsiteUrlChange: (String) -> Unit,
    onKeywordsChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onImageSelected: (Uri) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        VerticalItem("Company name*") {
            InputField(
                value = title,
                onValueChange = onTitleChange,
                placeholderText = "e.g Linear",
                modifier = Modifier.fillMaxWidth()
            )
        }

        VerticalItem("Website URL*") {
            InputField(
                value = websiteUrl,
                onValueChange = onWebsiteUrlChange,
                placeholderText = "www.example.com",
                modifier = Modifier.fillMaxWidth()
            )
        }

        ImagePicker(onImageSelected)

        VerticalItem("Keywords*") {
            InputField(
                value = keywords,
                onValueChange = onKeywordsChange,
                placeholderText = "",
                supportingText = {
                    Text(text = "Add 1-10 keywords that help users find your company. For example, B2B, SaaS, marketplace, design...")
                },
                modifier = Modifier.fillMaxWidth()
            )
        }

        VerticalItem("Description*") {
            InputField(
                value = description,
                onValueChange = onDescriptionChange,
                placeholderText = "",
                supportingText = { Text(text = "Write a few sentences about the company...") },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview
@Composable
private fun CompanyDialogContentPreview() {
    var title by remember { mutableStateOf("") }
    var websiteUrl by remember { mutableStateOf("") }
    var keywords by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    DevDashTheme {
        CompanyDialogContent(
            title,
            websiteUrl,
            keywords,
            description,
            { title = it },
            { websiteUrl = it },
            { keywords = it },
            { description = it },
            {}
        )
    }
}