package com.example.luckydraw.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: Item)

    @Query("DELETE FROM items_table WHERE item_name = :name")
    suspend fun delete(name: String)

    @Query("SELECT * FROM items_table ORDER BY added_date DESC")
    suspend fun getItemsOrderedByDate(): List<Item>
}