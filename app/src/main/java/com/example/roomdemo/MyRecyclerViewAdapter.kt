package com.example.roomdemo

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdemo.databinding.ListItemBinding
import com.example.roomdemo.db.Subscriber

class MyRecyclerViewAdapter(private val clickListner:(Subscriber)->Unit)
    :RecyclerView.Adapter<MyAdapterViewHolder>() {

    private val subscribersList=ArrayList<Subscriber>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapterViewHolder {
        val layoutInflater=LayoutInflater.from(parent.context)
        val binding : ListItemBinding=DataBindingUtil.inflate(layoutInflater,R.layout.list_item,parent,false)
        return MyAdapterViewHolder(binding)

    }

    override fun onBindViewHolder(holder: MyAdapterViewHolder, position: Int) {
        holder.bind(subscribersList[position],clickListner)
    }

    fun setList(subscribers: List<Subscriber>){
        subscribersList.clear()
        subscribersList.addAll(subscribers)
    }

    override fun getItemCount(): Int {
       return subscribersList.size
    }
}

class MyAdapterViewHolder(val binding: ListItemBinding):RecyclerView.ViewHolder(binding.root)
{
    fun bind(subscriber: Subscriber,clickListner:(Subscriber)->Unit){
        binding.itemName.text=subscriber.name
        binding.itemEmail.text=subscriber.email
        binding.listItemLayout.setOnClickListener{
            clickListner(subscriber)
        }
    }



}