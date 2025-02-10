package com.example.luckydraw.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items_table")
data class Item(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "item_id")
    val id: Int = 0,
    @ColumnInfo(name = "item_name")
    val name: String = "",
    @ColumnInfo(name = "added_date")
    val date: Long = 0L,

    )