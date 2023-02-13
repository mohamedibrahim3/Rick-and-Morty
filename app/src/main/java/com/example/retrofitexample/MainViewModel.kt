package com.example.retrofitexample

import android.telecom.Call
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.retrofitexample.network.ApiClient
import com.example.retrofitexample.network.CharacterResponse
import retrofit2.Response
import javax.security.auth.callback.Callback
import com.example.retrofitexample.network.Character as Character1

class MainViewModel(
    private val repository: Repository = Repository(ApiClient.apiService)
):ViewModel() {
    private var _charactersLiveData = MutableLiveData<ScreenState<List<Character1>?>>()
    val charactersLiveData:LiveData<ScreenState<List<Character1>?>>
    get() = _charactersLiveData
    init {
        fetchCharacter()
    }

    fun fetchCharacter(){
        val client = repository.getCharacters("1")
        _charactersLiveData.postValue(ScreenState.Loading(null))
        client.enqueue(object : retrofit2.Callback<CharacterResponse>{
            override fun onResponse(
                call: retrofit2.Call<CharacterResponse>,
                response: Response<CharacterResponse>
            ) {
                if(response.isSuccessful){
                    _charactersLiveData.postValue(ScreenState.Success(response.body()?.result))
                }
                else{
                    _charactersLiveData.postValue(ScreenState.Error(response.code().toString(),null))
                }
            }

            override fun onFailure(call: retrofit2.Call<CharacterResponse>, t: Throwable) {
                //Log.e("Failure",t.message.toString())
                _charactersLiveData.postValue(ScreenState.Error(t.message.toString(),null))

            }
        })
    }
}