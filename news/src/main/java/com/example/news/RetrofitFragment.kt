package com.example.news

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.news.databinding.FragmentRetrofitBinding
import com.example.news.model.PageListModel
import com.example.news.recycler.MyAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RetrofitFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentRetrofitBinding.inflate(inflater, container, false)

        val call : Call<PageListModel> = MyApplication.networkService.getList(
            MyApplication.QUERY,
            MyApplication.API_KEY,
            1,
            7
        )

        call?.enqueue(object : Callback<PageListModel>{

            override fun onResponse(call: Call<PageListModel>, response: Response<PageListModel>) {
                if (response.isSuccessful){
                    binding.retrofitRecyclerView.layoutManager = LinearLayoutManager(activity)
                    binding.retrofitRecyclerView.adapter = MyAdapter(activity as Context, response.body()?.articles)
                }
            }

            override fun onFailure(call: Call<PageListModel>, t: Throwable) {
               Log.d("fail", "error")
            }

        })

        return binding.root
    }


}