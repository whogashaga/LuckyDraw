# LuckyDraw 

[![Download Demo](https://github.com/whogashaga/LuckyDraw/blob/main/app/src/main/res/mipmap-xxhdpi/ic_launcher_round.webp)](https://drive.google.com/file/d/1Qm0fM9UA2Ju2YdFJcWKex2HaaJ1bin3C/view?usp=sharing)
[**Demo download**](https://drive.google.com/file/d/1Qm0fM9UA2Ju2YdFJcWKex2HaaJ1bin3C/view?usp=sharing)


## Introduction

Lucky Draw is a demonstration application that showcases Android development skills. The app features a streamlined and user-friendly interface for managing and randomly selecting names from a list, simulating scenarios like raffles or team assignments. This project highlights key Android development concepts, including navigation, UI design, state management, and dynamic interactions.

## Features

The app leverages the MVVM (Model-View-ViewModel) architecture to ensure efficient and maintainable code. A ViewModel dynamically manages the list of items, enabling real-time updates to the List View through LiveData observation. Additionally, a unit callback mechanism detects user interactions, allowing the ViewModel to execute corresponding actions seamlessly. This design demonstrates a robust approach to handling UI logic and data flow in Android development.

- Dynamic List View with Compose LazyColumn.
```Kotlin
LazyColumn {
    itemsIndexed(itemList) { _, text ->
        Box(modifier = Modifier.animateItem(fadeInSpec, placementSpec, fadeOutSpec)) {
            ListItem(item = text, onDelete = { onDelete.invoke(text) })
        }
    }
}
```

- ViewModel is used to dynamically manage the list of items.
```Kotlin
class MainViewModel() : ViewModel() {
    private val _items = MutableLiveData<List<String>>()
    val items: LiveData<List<String>> = _items
    fun addItem(item: String) {
        _items.value = (items.value?.toMutableList() ?: mutableListOf()).also { items ->
            items.add(0, item)
        }
    }

  fun removeItem(item: String) {
        val currentList = items.value.orEmpty().toMutableList()
        if (currentList.remove(item))
            _items.value = currentList
    }
}
```

- The MVVM pattern is implemented to observe LiveData for real-time updates to the List View. Additionally, a unit callback mechanism captures user interactions, enabling the ViewModel to execute corresponding actions.
```Kotlin
setContent {
    ItemListScreen(
        items = viewModel.items,
        onRaffleClick = {
            if (viewModel.getItemList().isNotEmpty()) viewModel.navigateRaffle()
            else makeShortToast(getString(R.string.at_least_two_items))
        },
        onItemAdd = { item -> viewModel.addItem(item) },
        onItemRemove = { item -> viewModel.removeItem(item) }
    )
}
```
  
- Jetpack Navigation is utilized to do the fragment transitions. 



## License

Copyright (c) Kerry Chen (Yen-Chun). All rights reserved.

Licensed under the [MIT](LICENSE) license.
