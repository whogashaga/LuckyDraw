package com.example.luckydraw.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.luckydraw.R
import com.example.luckydraw.ui.compose.ItemListScreen
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
                ItemListScreen(
                    items = vm.items,
                    onRaffleClick = {
                        if (vm.getItemList().isNotEmpty()) vm.navigateRaffle()
                        else makeShortToast(getString(R.string.at_least_two_items))
                    },
                    onItemAdd = { item -> vm.addItem(item) },
                    onItemRemove = { item -> vm.removeItem(item) }
                )
            }
        }
        observeLiveData()
        return view
    }

    private fun observeLiveData() {
        vm.removeSuccessMsg.observe(viewLifecycleOwner) { msg ->
            if (msg.isEmpty()) return@observe
//            makeShortToast(getString(R.string.success_removed, msg))
        }

        vm.alreadyExistMsg.observe(viewLifecycleOwner) { msg ->
            if (msg.isEmpty()) return@observe
            makeShortToast(getString(R.string.already_exist, msg))
        }
        vm.isAddedEmpty.observe(viewLifecycleOwner) { isEmpty ->
            if (!isEmpty) return@observe
            makeShortToast(getString(R.string.waring_empty))
        }
    }

    override fun onStart() {
        super.onStart()
        vm.cleanAllMsg()
    }

}

fun Fragment.makeShortToast(text: String) {
    Toast.makeText(
        this.requireContext(),
        text, Toast.LENGTH_SHORT
    ).show()
}