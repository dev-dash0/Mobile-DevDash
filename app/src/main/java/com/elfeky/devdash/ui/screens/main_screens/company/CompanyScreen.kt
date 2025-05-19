package com.elfeky.devdash.ui.screens.main_screens.company

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun CompanyScreen(
    modifier: Modifier = Modifier,
    onCompanyClick: (id: Int) -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val tenantId = 101

        Text("Company ID : $tenantId")
        Button(onClick = { onCompanyClick(tenantId) }) {
            Text("navigate")
        }
    }
}