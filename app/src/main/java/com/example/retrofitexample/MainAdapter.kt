package com.example.retrofitexample

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.retrofitexample.databinding.RvItemBinding
import okhttp3.internal.concurrent.Task
import java.util.TimerTask
import com.example.retrofitexample.network.Character as Character1

class MainAdapter(val characterList: List<Character1>) : RecyclerView.Adapter<MainAdapter.MainViewHolder>(){
    inner class MainViewHolder(val itemBinding: RvItemBinding):
        RecyclerView.ViewHolder(itemBinding.root){
            fun bindData(character: Character1){
                itemBinding.name.text = character.name
                itemBinding.image.load(character.image){
                    transformations(CircleCropTransformation())
                }
            }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(RvItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return characterList.size
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val character = characterList[position]
        holder.bindData(character)
    }

}