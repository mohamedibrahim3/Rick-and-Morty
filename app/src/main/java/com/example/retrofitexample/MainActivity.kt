package com.example.retrofitexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.retrofitexample.databinding.ActivityMainBinding
import com.example.retrofitexample.databinding.RvItemBinding
import com.example.retrofitexample.network.ApiClient
import com.example.retrofitexample.network.CharacterResponse
import retrofit2.Call
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    var binding: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        val client = ApiClient.apiService.fetchCharacters("1")
        client.enqueue(object : retrofit2.Callback<CharacterResponse>{
            override fun onResponse(
                call: Call<CharacterResponse>,
                response: Response<CharacterResponse>
            ){
                if (response.isSuccessful){
                    Log.d("characters",""+response.body())

                    val result = response.body()?.result
                    result?.let {
                        val adapter = MainAdapter(result)
                        binding?.charactersRV?.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
                        binding?.charactersRV?.adapter = adapter

                    }
                }
            }

            override fun onFailure(call: Call<CharacterResponse>,t:Throwable){
                Log.e("failed0",""+t.message)
            }
        })
        fun onDestroy() {
            super.onDestroy()
            binding = null
        }
    }
}