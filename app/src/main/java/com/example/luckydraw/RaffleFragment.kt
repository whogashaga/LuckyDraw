package com.example.luckydraw

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.luckydraw.viewmodel.RaffleViewModel

class RaffleFragment : androidx.fragment.app.Fragment(R.layout.fragment_raffle) {

    private val viewModel: RaffleViewModel by viewModels()
    private val mainViewModel:

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_raffle, container, false)
    }
}