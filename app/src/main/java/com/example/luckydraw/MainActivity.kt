package com.example.luckydraw

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.luckydraw.ui.theme.NameListScreen
import com.example.luckydraw.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeLiveData()
        setContent {
            val textState = remember { mutableStateOf("") }
            Scaffold(
                floatingActionButton = {
                    FloatingActionButton(
                        modifier = Modifier.wrapContentSize(),
                        onClick = {
                            this@MainActivity.makeShortToast("Fragment Coming Soon")
                        },
                        shape = RoundedCornerShape(20)
                    ) {
                        Text(text = "Raffle")
//                        Icon(Icons.Filled.PlayArrow, contentDescription = "Go Raffle")
                    }
                },
                floatingActionButtonPosition = FabPosition.End
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
                ) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        Row {
                            TextField(
                                value = textState.value,
                                onValueChange = { str -> textState.value = str }
                            )
                            Button(
                                modifier = Modifier.padding(4.dp).wrapContentWidth(),
                                colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
                                onClick = {
                                    this@MainActivity.makeShortToast("${textState.value} added")
                                    viewModel.addItem(textState.value)
                                },
                            ) {
                                Text("Add", color = Color.Black)
                            }
                        }
                        NameListScreen(viewModel.items) { name ->
                            viewModel.removeItems(name)
                        }
                    }
                }
            }
        }
    }

    private fun observeLiveData() {
        viewModel.successMsg.observe(this) { msg ->
            Toast.makeText(
                this, getString(R.string.success_removed, msg),
                Toast.LENGTH_SHORT
            ).show()
        }

        viewModel.errorMsg.observe(this) { msg ->
            val text =
                if (msg.isEmpty()) getString(R.string.waring_empty)
                else getString(R.string.already_exist, msg)
            Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
        }
    }

}

fun Activity.makeShortToast(text: String) {
    Toast.makeText(
        this,
        text, Toast.LENGTH_SHORT
    ).show()
}
