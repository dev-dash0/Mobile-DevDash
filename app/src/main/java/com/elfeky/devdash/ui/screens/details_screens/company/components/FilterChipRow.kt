package com.elfeky.devdash.ui.screens.details_screens.company.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elfeky.devdash.ui.theme.DevDashTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterChipRow(
    choices: List<String>,
    onChoiceSelected: (index: Int) -> Unit,
    modifier: Modifier = Modifier,
    initialSelectedIndex: Int = 0,
    contentPadding: PaddingValues = PaddingValues(horizontal = 16.dp),
    horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(16.dp)
) {
    var selectedChoiceIndex by remember { mutableIntStateOf(initialSelectedIndex) }

    LazyRow(
        modifier = modifier,
        contentPadding = contentPadding,
        horizontalArrangement = horizontalArrangement
    ) {
        itemsIndexed(choices) { index, choice ->
            val selected = selectedChoiceIndex == index
            FilterChip(
                selected = selected,
                onClick = {
                    selectedChoiceIndex = index
                    onChoiceSelected(selectedChoiceIndex)
                },
                label = {
                    Text(choice)
                },
                shape = CircleShape,
                colors = FilterChipDefaults.filterChipColors(
                    containerColor = MaterialTheme.colorScheme.surface.copy(alpha = .6f),
                    labelColor = MaterialTheme.colorScheme.onBackground,
                    selectedContainerColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 68f),
                    selectedLabelColor = MaterialTheme.colorScheme.onBackground,
                ),
            )
        }
    }
}

@Preview
@Composable
fun MyScreenWithChoices() {
    val options = listOf("Option 1", "Option 2", "Option 3", "Option 4", "Option 5", "Option 6")
    var selectedOption by remember { mutableStateOf<String?>(null) }

    DevDashTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text("Choose an option:")
            Spacer(Modifier.height(8.dp))

            FilterChipRow(
                choices = options,
                onChoiceSelected = { index ->
                    selectedOption = options[index]
                },
                initialSelectedIndex = 0
            )

            Spacer(Modifier.height(16.dp))

            Text("Selected: ${selectedOption ?: "None"}")
        }
    }
}
