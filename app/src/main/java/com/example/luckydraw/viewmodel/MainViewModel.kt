package com.example.luckydraw.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.luckydraw.Navigation

class MainViewModel() : ViewModel() {
    private val _items = MutableLiveData<List<String>>()
    val items: LiveData<List<String>> = _items
    val alreadyExistMsg by lazy { MutableLiveData<String>() }
    val isAddedEmpty by lazy { MutableLiveData<Boolean>() }
    val removeSuccessMsg by lazy { MutableLiveData<String>() }

    private val _navigate = MutableLiveData<Navigation>()
    val navigation: LiveData<Navigation> = _navigate

    init {
        _items.value = mutableListOf("Basketball", "Volleyball", "Tennis", "Swim", "Snowboarding")
    }

    fun addItem(item: String) {
        if (item.isEmpty()) {
            isAddedEmpty.value = true
            return
        }
        _items.value = (items.value?.toMutableList() ?: mutableListOf()).also { items ->
            if (item in items) {
                alreadyExistMsg.value = item
            } else {
                items.add(0, item)
            }
        }
    }

    fun removeItems(item: String) {
        val currentList = items.value.orEmpty().toMutableList()
        if (currentList.remove(item)) {
            _items.value = currentList
            removeSuccessMsg.value = item
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

    fun cleanAllMsg() {
        removeSuccessMsg.value = ""
        alreadyExistMsg.value = ""
        isAddedEmpty.value = false
    }
}