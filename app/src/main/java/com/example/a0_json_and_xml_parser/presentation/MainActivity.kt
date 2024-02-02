package com.example.a0_json_and_xml_parser.presentation

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.a0_json_and_xml_parser.R
import com.example.a0_json_and_xml_parser.data.repository.NewsRepositoryImpl
import com.example.a0_json_and_xml_parser.data.storage.Storage
import com.example.a0_json_and_xml_parser.domain.usecases.FillDataBaseByJSONUseCase
import com.example.a0_json_and_xml_parser.domain.usecases.FillDataBaseByXMLUseCase

class MainActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun downlandXMLButton(view: View) {
        Toast.makeText(this,"Pardon! \n It's not ready yet ", Toast.LENGTH_SHORT).show()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun downlandJSONButton(view: View) {
        FillDataBaseByJSONUseCase().invoke()
        openNextActivity()
    }

    private fun openNextActivity(){
        startActivity(Intent(this, ChoseHowToDisplayNewsActivity::class.java))
    }
}