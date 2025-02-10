package com.example.luckydraw.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items_table")
data class Item(
    @PrimaryKey
    @ColumnInfo(name = "item_name")
    val name: String = "",
    @ColumnInfo(name = "added_date")
    val date: Long = 0L,
)