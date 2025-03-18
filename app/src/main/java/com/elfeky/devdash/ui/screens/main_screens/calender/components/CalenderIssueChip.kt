package com.elfeky.devdash.ui.screens.main_screens.calender.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.elfeky.devdash.ui.utils.cardGradientBackground
import com.elfeky.domain.model.dashboard.CalenderIssue
import com.elfeky.devdash.R
import com.elfeky.devdash.ui.screens.main_screens.calender.CalenderViewModel
import com.elfeky.devdash.ui.theme.Orange

@Composable
fun CalenderIssueChip(
    modifier: Modifier = Modifier,
    calenderIssue: CalenderIssue,
    viewModel: CalenderViewModel = hiltViewModel()
) {
    Card(
        modifier = modifier,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(cardGradientBackground)
                .padding(horizontal = 16.dp, vertical = 24.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.status),
                        contentDescription = "status",
                        tint = Color.Blue
                    )
                    Spacer(Modifier.width(12.dp))
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "${calenderIssue.tenantName}: ${calenderIssue.projectName}",
                                color = MaterialTheme.colorScheme.onBackground,
                                fontStyle = FontStyle.Italic
                            )
                            Text(
                                text = "${viewModel.formatDate(calenderIssue.startDate)} | ${
                                    viewModel.formatDate(
                                        calenderIssue.deadline
                                    )
                                }",
                                color = Color.LightGray
                            )
                        }
                        Spacer(Modifier.height(12.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = calenderIssue.title,
                                color = MaterialTheme.colorScheme.onBackground,
                                fontWeight = FontWeight.Bold
                            )
                            Row {
                                Icon(
                                    imageVector = ImageVector.vectorResource(R.drawable.flag_ic),
                                    contentDescription = "Priority",
                                    tint = Orange
                                )
                                Text(
                                    text = calenderIssue.priority,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}