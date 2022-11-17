package com.gthio.multiwindowformat.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gthio.multiwindowformat.models.BillModel

@Composable
fun SelectBillsView(
    onEvent: (ModifyBillsEvent) -> Unit,
    modifier: Modifier = Modifier,
    selectedBills: List<BillModel>,
    bills: List<BillModel>,
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text("Daftar Bill", style = MaterialTheme.typography.titleSmall)

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(items = bills) { item ->
                CheckableItem(
                    onCheckedChange = { selected ->
                        onEvent(ModifyBillsEvent.BillsSelected(item, selected))
                    },
                    checked = selectedBills.contains(item),
                ) {
                    Text("Table ${item.tableId} - Bill ${item.id}")
                }
            }
        }
    }
}