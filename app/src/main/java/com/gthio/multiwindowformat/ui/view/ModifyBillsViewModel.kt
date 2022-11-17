package com.gthio.multiwindowformat.ui.view

import androidx.lifecycle.ViewModel
import com.gthio.multiwindowformat.models.BillModel
import com.gthio.multiwindowformat.models.dummyBillsFlow
import com.gthio.multiwindowformat.models.dummyTablesFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

// TODO: Move route out of ui state
class ModifyBillsViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ModifyBillUiState())
    val uiState = _uiState.asStateFlow()

    val tables = dummyTablesFlow

    val billList = _uiState.combine(dummyBillsFlow) { state, bills ->
        bills.filter { bill -> bill.tableId in state.selectedTables }
    }.onEach { availableBills ->
        _uiState.update { old ->
            old.copy(selectedBills = old.selectedBills.filter { it in availableBills })
        }
    }

    fun onEvent(event: ModifyBillsEvent) {
        when (event) {
            is ModifyBillsEvent.BillsSelected -> handleSelectBills(event.bill, event.add)
            is ModifyBillsEvent.TableSelected -> handleSelectTables(event.id, event.add)
            is ModifyBillsEvent.Navigate -> handleNavigate(event.route)
        }
    }

    private fun handleSelectBills(bill: BillModel, shouldAdd: Boolean) {
        _uiState.update { old ->
            val bills = when (shouldAdd) {
                true -> old.selectedBills + bill
                false -> old.selectedBills - bill
            }
            val updated = if (old.route != ModifyBillsRoute.SelectBills) {
                old.copy(route = ModifyBillsRoute.SelectBills)
            } else
                old
            updated.copy(selectedBills = bills)
        }
    }

    private fun handleSelectTables(id: Int, shouldAdd: Boolean) {
        _uiState.update { old ->
            val tables = when (shouldAdd) {
                true -> old.selectedTables + id
                false -> old.selectedTables - id
            }
            val updated = if (old.route != ModifyBillsRoute.SelectTables) {
                old.copy(route = ModifyBillsRoute.SelectTables)
            } else
                old
            updated.copy(selectedTables = tables)
        }
    }

    private fun handleNavigate(route: ModifyBillsRoute) {
        _uiState.update { old -> old.copy(route = route) }
    }
}

data class ModifyBillUiState(
    val selectedTables: List<Int> = listOf(),
    val selectedBills: List<BillModel> = listOf(),
    val route: ModifyBillsRoute = ModifyBillsRoute.SelectTables
)

sealed interface ModifyBillsRoute {
    object SelectBills : ModifyBillsRoute
    object SelectTables : ModifyBillsRoute
}

sealed interface ModifyBillsEvent {
    data class TableSelected(val id: Int, val add: Boolean) : ModifyBillsEvent
    data class BillsSelected(val bill: BillModel, val add: Boolean) : ModifyBillsEvent
    data class Navigate(val route: ModifyBillsRoute) : ModifyBillsEvent
}