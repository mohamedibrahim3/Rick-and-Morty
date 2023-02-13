package com.example.retrofitexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.retrofitexample.databinding.ActivityMainBinding
import com.example.retrofitexample.databinding.RvItemBinding
import com.example.retrofitexample.network.ApiClient
import com.example.retrofitexample.network.CharacterResponse
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Response
import com.example.retrofitexample.network.Character as Character1


class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }
    var binding: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        viewModel.charactersLiveData.observe(
            this,
        ) { state ->
            processCharactersResponse(state)
        }
        fun onDestroy() {
            super.onDestroy()
            binding = null
        }
    }
    private fun processCharactersResponse(state: ScreenState<List<Character1>?>) {
        when (state) {
            is ScreenState.Success -> {
                binding?.progressBar?.visibility = View.GONE
                if (state.data != null) {
                    val adapter = MainAdapter(state.data)
                    binding?.charactersRV?.layoutManager =
                        StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                    binding?.charactersRV?.adapter = adapter
                }
            }
            is ScreenState.Error -> {
                binding?.progressBar?.visibility = View.GONE
                val view = binding?.progressBar?.rootView
                if (view != null) {
                    Snackbar.make(view,state.message!!,Snackbar.LENGTH_LONG).show()
                }
            }
            is ScreenState.Loading -> {
                binding?.progressBar?.visibility = View.VISIBLE
            }
        }
    }
}