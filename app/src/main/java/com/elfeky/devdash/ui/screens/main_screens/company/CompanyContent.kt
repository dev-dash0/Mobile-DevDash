package com.elfeky.devdash.ui.screens.main_screens.company

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.elfeky.devdash.ui.common.card.CompanyCard
import com.elfeky.devdash.ui.common.companyList
import com.elfeky.devdash.ui.common.component.FilterChipRow
import com.elfeky.devdash.ui.common.component.SwipeToDismissItem
import com.elfeky.devdash.ui.theme.DevDashTheme
import com.elfeky.domain.model.tenant.Tenant
import kotlinx.coroutines.flow.flowOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompanyContent(
    state: CompanyReducer.State,
    companies: LazyPagingItems<Tenant>,
    onEvent: (CompanyReducer.Event) -> Unit,
    modifier: Modifier = Modifier,
    onDeleteCompany: (id: Int) -> Unit,
    onCompanyClick: (id: Int) -> Unit
) {
    val filters = listOf("All", "Owned", "Joined")

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        FilterChipRow(
            choices = filters,
            onChoiceSelected = { onEvent(CompanyReducer.Event.Update.Filter(it)) },
            initialSelectedIndex = state.selectedFilterIndex,
            modifier = Modifier.fillMaxWidth()
        )

        Crossfade(state.isLoading) { isLoading ->
            if (isLoading) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    val filteredItems = companies.itemSnapshotList.items.filter { company ->
                        when (state.selectedFilterIndex) {
                            0 -> true
                            1 -> company.role == "Admin"
                            2 -> company.role != "Admin"
                            else -> true
                        }
                    }

                    items(filteredItems, key = { it.id }) { company ->
                        val isPinned by remember(state.pinnedCompanies, company) {
                            derivedStateOf { state.pinnedCompanies.any { it.id == company.id } }
                        }

                        SwipeToDismissItem(
                            isPinned = isPinned,
                            onSwipeToDelete = { onDeleteCompany(company.id) },
                            onSwipeToPin = {
                                onEvent(
                                    CompanyReducer.Event.TogglePinStatus(
                                        company.id,
                                        isPinned
                                    )
                                )
                            }
                        ) {
                            CompanyCard(company = company, onClick = onCompanyClick)
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun CompanyScreenPreview() {
    val companies = flowOf(PagingData.from(companyList))
    DevDashTheme {
        CompanyContent(
            state = CompanyReducer.State(
                isLoading = false,
                companies = companies,
                pinnedCompanies = companyList.take(3),
                selectedFilterIndex = 0,
                error = null
            ),
            companies = companies.collectAsLazyPagingItems(),
            onEvent = {},
            onDeleteCompany = {}
        ) {}
    }
}