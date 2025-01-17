package com.example.luckydraw.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.luckydraw.Navigation

class MainViewModel() : ViewModel() {
    private val _items = MutableLiveData<List<String>>()
    val items: LiveData<List<String>> = _items
    val errorMsg by lazy { MutableLiveData<String>() }
    val successMsg by lazy { MutableLiveData<String>() }

    private val _navigate = MutableLiveData<Navigation>()
    val navigation: LiveData<Navigation> = _navigate

    init {
//        val list = mutableListOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12")
        _items.value = listOf()
    }

    fun addItem(item: String) {
        _items.value = (items.value?.toMutableList() ?: mutableListOf()).also { items ->
            if (item in items) {
                errorMsg.value = item
            } else {
                items.add(0, item)
            }
        }
    }

    fun removeItems(item: String) {
        val currentList = items.value.orEmpty().toMutableList()
        if (currentList.remove(item)) {
            _items.value = currentList
            successMsg.value = item
        }
    }

    fun getItemList(): List<String> {
        return items.value ?: listOf()
    }

    fun navigateHome() {
        _navigate.value = Navigation.Home
    }

    fun navigateRaffle() {
        _navigate.value = Navigation.Raffle
    }
}