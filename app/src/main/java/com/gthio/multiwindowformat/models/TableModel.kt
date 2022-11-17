package com.gthio.multiwindowformat.models

import kotlinx.coroutines.flow.flow

data class TableModel(
    val id: Int = 0,
    val name: String = "Table $id"
)

val dummyTables = listOf(
    TableModel(id = 1),
    TableModel(id = 2),
    TableModel(id = 3),
    TableModel(id = 4),
)

val dummyTablesFlow = flow {
    emit(dummyTables)
}