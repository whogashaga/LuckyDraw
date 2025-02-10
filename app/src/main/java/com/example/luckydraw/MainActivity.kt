package com.example.luckydraw

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation.findNavController
import com.example.luckydraw.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val vm: MainViewModel by viewModels()
    private val navController by lazy { findNavController(this, R.id.nav_host) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        vm.navigation.observe(this) { type ->
            when (type) {
                Navigation.Raffle -> navController.navigate(R.id.to_raffle)
                else -> navController.navigateUp()
            }
        }
    }

}

fun Activity.makeShortToast(text: String) {
    Toast.makeText(
        this,
        text, Toast.LENGTH_SHORT
    ).show()
}
