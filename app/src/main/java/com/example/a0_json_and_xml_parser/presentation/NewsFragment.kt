package com.example.a0_json_and_xml_parser.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.a0_json_and_xml_parser.R
import com.example.a0_json_and_xml_parser.domain.models.News

class NewsFragment(private val myNews: ArrayList<News>) : Fragment() {

    private var columnCount = 1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val recyclerView = inflater.inflate(R.layout.fragment_item_list, container, false)

        GridLayoutManager(context, columnCount)

        if (recyclerView is RecyclerView) {
            with(recyclerView) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = MyNewsRecyclerViewAdapter(myNews)
            }
        }
        return recyclerView
    }

    companion object {
        fun newInstance(columnCount: Int) = NewsFragment(ArrayList())
    }
}