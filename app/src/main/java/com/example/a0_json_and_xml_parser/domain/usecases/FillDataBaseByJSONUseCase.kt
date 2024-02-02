package com.example.a0_json_and_xml_parser.domain.usecases

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.a0_json_and_xml_parser.data.repository.NewsRepositoryImpl
import com.example.a0_json_and_xml_parser.data.storage.Storage
import com.example.a0_json_and_xml_parser.domain.models.News
import com.example.a0_json_and_xml_parser.domain.repository.NewsRepository
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class FillDataBaseByJSONUseCase(private val newsRepository : NewsRepository = NewsRepositoryImpl(Storage)) {
    @RequiresApi(Build.VERSION_CODES.O)
    operator fun invoke(){
        val arrayOfNews: ArrayList<News> = ArrayList()
        val client = OkHttpClient()

        var newsID = 0
        var newsTitle = ""
        var newsDescription = ""
        var newsDateTimeResult = LocalDateTime.now()
        var newsKeyWords = ""

             Thread{
                 val request = Request.Builder()
                     .url("https://api2.kiparo.com/static/it_news.json")
                     .build()

                 val response: Response = client.newCall(request).execute()
                 if (!response.isSuccessful) { throw IOException() }

                 val jsonContact = JSONObject(response.body!!.string())
                 val jsonArrayInfo : JSONArray = jsonContact.getJSONArray("news")

                 for (index in 0 until jsonArrayInfo.length()){
                     val jsonObject: JSONObject = jsonArrayInfo.getJSONObject(index)

                     try {
                         newsTitle = jsonObject.getString("title")
                     }catch (e : Exception){}

                     val dateFromColumnString : List<String> = jsonObject.getString("date").split(" ")
                     val localDateFromFile = LocalDate.parse(dateFromColumnString[0])
                     val localTimeFromFile = LocalTime.parse(dateFromColumnString[1])
                     newsDateTimeResult = LocalDateTime.of(localDateFromFile, localTimeFromFile)

                     newsID = jsonObject.getInt("id")
                     newsDescription = jsonObject.getString("description")
                     newsKeyWords = jsonObject.getString("keywords")

                     arrayOfNews.add(News(id = newsID, title = newsTitle, description = newsDescription, date = newsDateTimeResult, keywords = newsKeyWords))
                 }
                 newsRepository.fillDataBase(arrayOfNews)
             }.start()

    }
}