package com.gthio.multiwindowformat.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gthio.multiwindowformat.models.BillModel
import com.gthio.multiwindowformat.models.TableModel

@Composable
fun ModifyBillsView(
    viewModel: ModifyBillsViewModel = viewModel(),
    sizeClass: WindowWidthSizeClass,
) {
    val tables by viewModel.tables.collectAsState(initial = listOf())
    val bills by viewModel.billList.collectAsState(initial = listOf())
    val uiState by viewModel.uiState.collectAsState(initial = ModifyBillUiState())

    if (sizeClass == WindowWidthSizeClass.Compact) {
        ModifyBillsMobileView(
            onEvent = viewModel::onEvent,
            currentRoute = uiState.route,
            tables = tables,
            selectedTables = uiState.selectedTables,
            selectedBills = uiState.selectedBills,
            bills = bills
        )
    } else {
        ModifyBillsTabletView(
            onEvent = viewModel::onEvent,
            tables = tables,
            selectedTables = uiState.selectedTables,
            selectedBills = uiState.selectedBills,
            bills = bills
        )
    }
}

@Composable
private fun ModifyBillsMobileView(
    onEvent: (ModifyBillsEvent) -> Unit,
    currentRoute: ModifyBillsRoute,
    tables: List<TableModel>,
    selectedTables: List<Int>,
    selectedBills: List<BillModel>,
    bills: List<BillModel>,
) {
    when (currentRoute) {
        ModifyBillsRoute.SelectBills -> {
            Column {
                SelectBillsView(
                    onEvent = onEvent,
                    modifier = Modifier.weight(1f),
                    bills = bills,
                    selectedBills = selectedBills,
                )
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    onClick = {
                        onEvent(ModifyBillsEvent.Navigate(ModifyBillsRoute.SelectTables))
                    }
                ) {
                    Text("To Tables")
                }
            }
        }
        ModifyBillsRoute.SelectTables -> {
            Column {
                SelectTableView(
                    onEvent = onEvent,
                    selectedTables = selectedTables,
                    tables = tables,
                    modifier = Modifier.weight(1f)
                )
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    onClick = {
                        onEvent(ModifyBillsEvent.Navigate(ModifyBillsRoute.SelectBills))
                    }
                ) {
                    Text("To Bills")
                }
            }
        }
    }
}

@Composable
private fun ModifyBillsTabletView(
    onEvent: (ModifyBillsEvent) -> Unit,
    tables: List<TableModel>,
    selectedTables: List<Int>,
    selectedBills: List<BillModel>,
    bills: List<BillModel>,
) {
    Row {
        SelectTableView(
            onEvent = onEvent,
            selectedTables = selectedTables,
            tables = tables,
            modifier = Modifier.weight(0.6f)
        )
        SelectBillsView(
            onEvent = onEvent,
            modifier = Modifier.weight(0.4f),
            bills = bills,
            selectedBills = selectedBills,
        )
    }
}