package com.example.a0_json_and_xml_parser.domain.usecases

import android.os.Build
import android.util.Xml
import androidx.annotation.RequiresApi
import com.example.a0_json_and_xml_parser.data.repository.NewsRepositoryImpl
import com.example.a0_json_and_xml_parser.data.storage.Storage
import com.example.a0_json_and_xml_parser.domain.models.News
import com.example.a0_json_and_xml_parser.domain.repository.NewsRepository
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException
import java.io.InputStream
import java.net.URL
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

private val ns: String? = null
class FillDataBaseByXMLUseCase(private val newsRepository : NewsRepository = NewsRepositoryImpl(Storage)) {
    @RequiresApi(Build.VERSION_CODES.O)
    operator fun invoke(){
        //var arrayOfNews: ArrayList<News> = ArrayList()
        //val client = OkHttpClient()
//
        //val request = Request.Builder()
        //        .url("https://api2.kiparo.com/static/it_news.xml")
        //        .build()
        //Thread{
        //    val response: Response = client.newCall(request).execute()
        //    if (!response.isSuccessful) { throw IOException() }
        //    val xmlString = response.body!!.string()
//
        //    val serializer : Serializer = Persister()
        //    serializer.read(News::class,xmlString)
//
        //    val factory = XmlPullParserFactory.newInstance()
        //    factory.isNamespaceAware = true
        //    val xpp = factory.newPullParser()
        //    xpp.setInput(xmlString.reader())
        //    var eventType = xpp.eventType
//
//
        //}.start()

        Thread{
                newsRepository.fillDataBase(parse(URL("https://api2.kiparo.com/static/it_news.xml").openStream()))
        }.start()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Throws(XmlPullParserException::class, IOException::class)
    private fun parse(inputStream: InputStream): ArrayList<News> {
        inputStream.use { inputStream ->
            val parser: XmlPullParser = Xml.newPullParser()
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
            parser.setInput(inputStream, null)
            parser.nextTag()
            return readFeed(parser)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Throws(XmlPullParserException::class, IOException::class)
    private fun readFeed(parser: XmlPullParser): ArrayList<News> {
        val arrayListWithNews = ArrayList<News>()

        parser.require(XmlPullParser.START_TAG, ns, "root")
        while (parser.next() != XmlPullParser.END_TAG) {
             if (parser.eventType == XmlPullParser.START_TAG) {
                 parser.require(XmlPullParser.START_TAG, ns, parser.name)
                 while (parser.next() != XmlPullParser.END_TAG) {
                     if (parser.name == "element") {
                         arrayListWithNews.add(readEntry(parser))
                     }
                 }
            }
        }
        return arrayListWithNews
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Throws(XmlPullParserException::class, IOException::class)
    private fun readEntry(parser: XmlPullParser): News {
        parser.require(XmlPullParser.START_TAG, ns, "element")
        var id = 0
        var title = ""
        var description = ""
        var date : LocalDateTime = LocalDateTime.now()
        val keywords : StringBuilder = java.lang.StringBuilder()
        while (parser.next() != XmlPullParser.END_TAG) {

            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }

            when (parser.name) {
                "id" -> id = readDesiredTag(parser, "id").toInt()
                "title" -> title = readDesiredTag(parser, "title")
                "description" -> description = readDesiredTag(parser, "description")
                "date" -> {
                    val dateFromColumnString : List<String> = readDesiredTag(parser, "date").split(" ")
                    val localDateFromFile = LocalDate.parse(dateFromColumnString[0])
                    val localTimeFromFile = LocalTime.parse(dateFromColumnString[1])
                    date = LocalDateTime.of(localDateFromFile, localTimeFromFile)
                }
                "keywords" -> {
                    parser.nextTag()
                    val res = parser.name
                    while (parser.name == "element"){
                        keywords.append("\"\"" + readDesiredTag(parser, "element") + "\"\"|")
                        parser.nextTag()
                    }
                }
                else -> skip(parser)
            }
        }
        return News(id = id, title = title, description = description, date = date, keywords = keywords.toString() )
    }


    @Throws(IOException::class, XmlPullParserException::class)
    private fun readDesiredTag(parser: XmlPullParser, desiredTag : String): String {

        parser.require(XmlPullParser.START_TAG, ns, desiredTag)
        val desiredTagResultText = readText(parser)
        parser.require(XmlPullParser.END_TAG, ns, desiredTag)
        return desiredTagResultText
    }


    @Throws(IOException::class, XmlPullParserException::class)
    private fun readText(parser: XmlPullParser): String {
        var result = ""
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.text
            parser.nextTag()
        }
        return result
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun skip(parser: XmlPullParser) {
        if (parser.eventType != XmlPullParser.START_TAG) {
            throw IllegalStateException()
        }
        var depth = 1
        while (depth != 0) {
            when (parser.next()) {
                XmlPullParser.END_TAG -> depth--
                XmlPullParser.START_TAG -> depth++
            }
        }
    }
}