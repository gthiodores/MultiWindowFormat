package com.gthio.multiwindowformat.models

import kotlinx.coroutines.flow.flow

data class BillModel(
    val id: Int = 0,
    val tableId: Int = 0,
    val customer: String = "Anto",
)

val dummyBills = listOf(
    BillModel(id = 1, tableId = 1),
    BillModel(id = 2, tableId = 1),
    BillModel(id = 3, tableId = 2),
    BillModel(id = 4, tableId = 3)
)

val dummyBillsFlow = flow {
    emit(dummyBills)
}