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
import com.gthio.multiwindowformat.models.TableModel

@Composable
fun SelectTableView(
    onEvent: (ModifyBillsEvent) -> Unit,
    modifier: Modifier = Modifier,
    selectedTables: List<Int>,
    tables: List<TableModel>,
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text("Daftar Meja", style = MaterialTheme.typography.titleSmall)

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(items = tables) { item ->
                CheckableItem(
                    onCheckedChange = { selected ->
                        onEvent(ModifyBillsEvent.TableSelected(item.id, selected))
                    },
                    checked = selectedTables.contains(item.id),
                ) {
                    Text(item.name)
                }
            }
        }
    }
}