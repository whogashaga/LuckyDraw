package com.example.luckydraw.ui.theme

import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import com.example.luckydraw.model.Item

//@Preview(showSystemUi = true)
@Composable
fun NameListScreen(
    items: LiveData<List<Item>>,
    onDelete: (Item) -> Unit
) {
    val itemList: List<Item> by items.observeAsState(initial = emptyList())
    val fadeInSpec: FiniteAnimationSpec<Float> = spring(stiffness = 10f)
    val placementSpec: FiniteAnimationSpec<IntOffset> = spring(
        stiffness = Spring.StiffnessMediumLow,
        visibilityThreshold = IntOffset.VisibilityThreshold
    )
    val fadeOutSpec: FiniteAnimationSpec<Float> = spring(stiffness = Spring.StiffnessMediumLow)
    LazyColumn(modifier = Modifier.clipToBounds()) {
        itemsIndexed(itemList) { _, item ->
            Box(modifier = Modifier.animateItem(fadeInSpec, placementSpec, fadeOutSpec)) {
                ListItem(item = item, onDelete = { onDelete.invoke(item) })
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun ListItem(item: Item = Item(), onDelete: () -> Unit = {}) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(MaterialTheme.colorScheme.surface),
    ) {
        Text(
            text = item.name,
            modifier = Modifier.weight(1f),
            fontSize = 20.sp
        )

        IconButton(
            onClick = onDelete
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete",
                tint = MaterialTheme.colorScheme.error
            )
        }
    }
}