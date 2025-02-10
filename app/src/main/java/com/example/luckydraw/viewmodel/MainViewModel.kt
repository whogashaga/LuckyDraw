package com.example.luckydraw.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.luckydraw.Navigation
import com.example.luckydraw.model.Item
import com.example.luckydraw.repo.LocalDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val localRepo: LocalDataRepository
) : ViewModel() {

    private val _items = MutableLiveData<List<Item>>()
    val items: LiveData<List<Item>> = _items
    val alreadyExistMsg by lazy { MutableLiveData<String>() }
    val isAddedEmpty by lazy { MutableLiveData<Boolean>() }
    val removeSuccessMsg by lazy { MutableLiveData<String>() }

    private val _navigate = MutableLiveData<Navigation>()
    val navigation: LiveData<Navigation> = _navigate

    init {
        viewModelScope.launch {
            localRepo.getItemsOrderedByDate().let { history ->
                if (history.isNotEmpty()) {
                    _items.value = history
                } else {
                    addItem(Item(name = "Basketball", date = System.currentTimeMillis()))
                    addItem(Item(name = "Volleyball", date = System.currentTimeMillis()))
                    addItem(Item(name = "Tennis", date = System.currentTimeMillis()))
                    addItem(Item(name = "Football", date = System.currentTimeMillis()))
                    addItem(Item(name = "Bowling", date = System.currentTimeMillis()))
                    addItem(Item(name = "Swimming", date = System.currentTimeMillis()))
                    addItem(Item(name = "Soccer", date = System.currentTimeMillis()))
                    addItem(Item(name = "Golf", date = System.currentTimeMillis()))
                    addItem(Item(name = "Skiing", date = System.currentTimeMillis()))
                    addItem(Item(name = "Polo", date = System.currentTimeMillis()))
                    addItem(Item(name = "Badminton", date = System.currentTimeMillis()))
                    addItem(Item(name = "Baseball", date = System.currentTimeMillis()))
                    addItem(Item(name = "Softball", date = System.currentTimeMillis()))
                }
            }
        }
    }

    fun addItem(item: Item) {
        if (item.name.isEmpty()) {
            isAddedEmpty.value = true
            return
        }
        if (item.name in getItemList().map { it.name }) {
            alreadyExistMsg.value = item.name
            return
        }
        viewModelScope.launch {
            localRepo.insertItem(item)
            _items.value = localRepo.getItemsOrderedByDate()
        }
    }

    fun removeItem(item: Item) = viewModelScope.launch {
        localRepo.deleteItem(item.name)
        removeSuccessMsg.value = item.name
        _items.value = localRepo.getItemsOrderedByDate()
    }

    fun getItemList(): List<Item> {
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