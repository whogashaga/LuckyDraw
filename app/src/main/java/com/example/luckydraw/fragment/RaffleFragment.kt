package com.example.luckydraw.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.luckydraw.R
import com.example.luckydraw.ui.compose.LuckyDrawScreen
import com.example.luckydraw.ui.theme.LuckyDrawTheme
import com.example.luckydraw.viewmodel.MainViewModel

class RaffleFragment : Fragment(R.layout.fragment_raffle) {

    private val vm: MainViewModel by activityViewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_raffle, container, false)
        val composeView = view.findViewById<ComposeView>(R.id.compose_view)
        composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                LuckyDrawTheme(darkTheme = false) {
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)) {
                        LuckyDrawScreen(
                            items = vm.getItemList(),
                            "The Winner is ",
                            { selectedIndex, _ ->
                                val name = vm.getItemList().get(selectedIndex)
                                makeShortToast("Congratulations !\n$name")
                            },
                            { vm.navigateHome() }
                        )
                    }
                }
            }
        }
        return view
    }

}