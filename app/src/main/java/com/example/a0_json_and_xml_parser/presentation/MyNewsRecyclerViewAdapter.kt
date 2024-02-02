package com.example.a0_json_and_xml_parser.presentation

import android.os.Build
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi

import com.example.a0_json_and_xml_parser.databinding.FragmentItemBinding
import com.example.a0_json_and_xml_parser.domain.models.News
import java.time.format.DateTimeFormatter

class MyNewsRecyclerViewAdapter(private val news: ArrayList<News>) : RecyclerView.Adapter<MyNewsRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            FragmentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = news[position]
        holder.title.text = item.title
        holder.description.text = item.description
        val dateTimeFormatter = DateTimeFormatter.ofPattern( "dd MMMM yyyy \n hh:mm:ss")
        holder.date.text = item.date.format(dateTimeFormatter)
    }

    override fun getItemCount(): Int = news.size

    inner class ViewHolder(binding: FragmentItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val title: TextView = binding.title
        val description: TextView = binding.description
        val date: TextView = binding.date
    }
}