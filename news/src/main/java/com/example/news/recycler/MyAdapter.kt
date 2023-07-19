package com.example.news.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.news.databinding.ItemMainBinding
import com.example.news.model.ItemModel


class MyViewHolder(val binding: ItemMainBinding) : RecyclerView.ViewHolder(binding.root)

class MyAdapter(val context: Context, val datas: MutableList<ItemModel>?) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        MyViewHolder(ItemMainBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount(): Int {
        return datas?.size ?: 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as MyViewHolder).binding

        val model = datas!![position]
        binding.itemTitle.text = model.title
        binding.itemDesc.text = model.description
        binding.itemTime.text = " ${model.author} At ${model.publishedAt}"

        Glide.with(context)
            .load(model.urlToImage)
            .into(binding.itemImage)
    }
}