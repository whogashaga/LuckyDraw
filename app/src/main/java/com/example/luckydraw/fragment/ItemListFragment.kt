package com.example.luckydraw.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.luckydraw.R
import com.example.luckydraw.ui.theme.NameListScreen
import com.example.luckydraw.viewmodel.MainViewModel

class ItemListFragment : Fragment() {

    private val vm by activityViewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_item_list, container, false)
        val composeView = view.findViewById<ComposeView>(R.id.compose_view)
        composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                setContent {
                    val textState = remember { mutableStateOf("") }
                    Scaffold(
                        floatingActionButtonPosition = FabPosition.End,
                        floatingActionButton = {
                            FloatingActionButton(
                                modifier = Modifier.wrapContentSize(),
                                onClick = {
                                    if (vm.getItemList().isNotEmpty()) vm.navigateRaffle()
                                    else makeShortToast("At least add two items into the list")
                                },
                                shape = RoundedCornerShape(20)
                            ) {
                                Text(text = "Raffle")
                            }
                        },
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(it)
                        ) {
                            Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                                Row {
                                    TextField(
                                        value = textState.value,
                                        onValueChange = { str -> textState.value = str }
                                    )
                                    Button(
                                        modifier = Modifier.padding(4.dp).wrapContentWidth(),
                                        colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
                                        onClick = { vm.addItem(textState.value) },
                                    ) {
                                        Text("Add", color = Color.Black)
                                    }
                                }
                                NameListScreen(vm.items) { name ->
                                    vm.removeItems(name)
                                }
                            }
                        }
                    }
                }
            }
        }
        observeLiveData()
        return view
    }

    private fun observeLiveData() {
        vm.removeSuccessMsg.observe(viewLifecycleOwner) { msg ->
            makeShortToast(getString(R.string.success_removed, msg))
        }

        vm.alreadyExistMsg.observe(viewLifecycleOwner) { msg ->
            val text =
                if (msg.isEmpty()) getString(R.string.already_exist)
                else getString(R.string.already_exist, msg)
            makeShortToast(text)
        }
        vm.isAddedEmpty.observe(viewLifecycleOwner) { isEmpty ->
            if (!isEmpty) return@observe
            makeShortToast(getString(R.string.waring_empty))
        }

    }

}

fun Fragment.makeShortToast(text: String) {
    Toast.makeText(
        this.requireContext(),
        text, Toast.LENGTH_SHORT
    ).show()
}