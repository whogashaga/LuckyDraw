package com.example.luckydraw.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization.Companion.Sentences
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import com.example.luckydraw.R
import com.example.luckydraw.ui.theme.NameListScreen

@Composable
fun ItemListScreen(
    items: LiveData<List<String>>,
    onRaffleClick: () -> Unit,
    onItemAdd: (String) -> Unit,
    onItemRemove: (String) -> Unit,
) {
    val textState = remember { mutableStateOf("") }
    Scaffold(
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            ExtendedFloatingActionButton(
                modifier = Modifier.wrapContentSize(),
                onClick = { onRaffleClick.invoke() },
                shape = RoundedCornerShape(20),
                icon = { Icon(painterResource(R.drawable.baseline_change_circle_24),"raffle") },
                text = { Text(text = "Go Raffle") }
            )
        },
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Row {
                    TextField(
                        value = textState.value,
                        keyboardOptions = KeyboardOptions(
                            capitalization = Sentences,
                            imeAction = ImeAction.Done
                        ),
                        onValueChange = { str -> textState.value = str },
                        trailingIcon = {
                            Icon(
                                Icons.Default.Clear,
                                contentDescription = "clear text",
                                modifier = Modifier.clickable { textState.value = "" }
                            )
                        }
                    )
                    Button(
                        modifier = Modifier
                            .padding(4.dp)
                            .wrapContentWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
                        onClick = { onItemAdd(textState.value) },
                    ) {
                        Text("Add", color = Color.Black)
                    }
                }
                NameListScreen(items) { name ->
                    onItemRemove.invoke(name)
                }
            }
        }
    }
}