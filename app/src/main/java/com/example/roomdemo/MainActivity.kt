package com.example.roomdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdemo.databinding.ActivityMainBinding
import com.example.roomdemo.db.SubscribeDataBase
import com.example.roomdemo.db.Subscriber
import com.example.roomdemo.db.SubscriberRepositary

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private lateinit var subscribeViewModel: SubscribeViewModel
    private lateinit var adapter: MyRecyclerViewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_main)

        val dao=SubscribeDataBase.getInstance(application).subsDao
        val repo=SubscriberRepositary(dao)
        val factory= SubscriberViewModelFactory(repo)

        subscribeViewModel=ViewModelProvider(this,factory).get(SubscribeViewModel::class.java)
        binding.myViewModel=subscribeViewModel
        binding.lifecycleOwner=this
        initRecyclerView()

        subscribeViewModel.Message.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()

            }
        })




    }

    private fun initRecyclerView(){
        binding.rvRoom.layoutManager=LinearLayoutManager(this)
        adapter=MyRecyclerViewAdapter({ItemSelected:Subscriber->listItemClicked(ItemSelected)})
        binding.rvRoom.adapter=adapter

        displaySubscribesList()
    }

    fun displaySubscribesList(){
        subscribeViewModel.subscribers.observe(this, Observer {
            Log.i("MYTAG",it.toString())
            adapter.setList(it)
            adapter.notifyDataSetChanged()
        })
    }

    private fun listItemClicked(subscriber: Subscriber){
//        Toast.makeText(this, "selected name is ${subscriber.name}", Toast.LENGTH_LONG).show()
        subscribeViewModel.initUpdataeAndDelete(subscriber)
    }
}