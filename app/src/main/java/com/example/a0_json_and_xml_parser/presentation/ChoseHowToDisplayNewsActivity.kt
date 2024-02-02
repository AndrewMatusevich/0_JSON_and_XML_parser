package com.example.a0_json_and_xml_parser.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.a0_json_and_xml_parser.R
import com.example.a0_json_and_xml_parser.domain.models.News
import com.example.a0_json_and_xml_parser.domain.usecases.GetListOfNewsUseCase
import com.example.a0_json_and_xml_parser.domain.usecases.GetNewsByKeyWordUseCase

class ChoseHowToDisplayNewsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chose_how_to_display_news)
    }

    fun lookForKeyWord(view: View) {
        val keyWord = findViewById<EditText>(R.id.searchByKeyword).text.toString()
        val arrayWithResult = ArrayList<News>()
        try {
        val singleNews : News = GetNewsByKeyWordUseCase().invoke(keyWord = keyWord.trim())
        arrayWithResult.add(singleNews)
        openFragment(NewsFragment(arrayWithResult),"NewsFragment",R.id.emptyFrame)
        }catch (e : Exception) {Toast.makeText(this,"Pardon! \n There's not such news ", Toast.LENGTH_LONG).show()}
    }

    fun displayAllNews(view: View) {
        val arrayWithResult : ArrayList<News> = GetListOfNewsUseCase().invoke()
        openFragment(NewsFragment(arrayWithResult),"NewsFragment",R.id.emptyFrame)
    }

    fun exit(view: View) {super.onBackPressed()}

    private fun openFragment(fragment: Fragment, fragmentName : String, container : Int){
        supportFragmentManager.beginTransaction().apply {
            replace(container, fragment)
            addToBackStack(fragmentName)
            commit()
        }
    }
}