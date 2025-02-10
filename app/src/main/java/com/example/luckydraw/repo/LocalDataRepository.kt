package com.example.luckydraw.repo

import com.example.luckydraw.model.Item
import com.example.luckydraw.model.ItemDao
import javax.inject.Inject

class LocalDataRepository @Inject constructor (private val itemDao: ItemDao) {

    suspend fun insertItem(items: Item) {
        itemDao.insert(items)
    }

    suspend fun deleteItem(name: String) {
        itemDao.delete(name)
    }

    suspend fun getItemsOrderedByDate(): List<Item> = itemDao.getItemsOrderedByDate()
}