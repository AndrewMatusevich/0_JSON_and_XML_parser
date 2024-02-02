package com.example.a0_json_and_xml_parser.data.storage

import com.example.a0_json_and_xml_parser.domain.models.News


object Storage {

    private var storage : ArrayList<News> = ArrayList()

    fun getListOfNews(): ArrayList<com.example.a0_json_and_xml_parser.domain.models.News> {
        return storage
    }

    fun getNewsByKeyWord(keyWord: String): com.example.a0_json_and_xml_parser.domain.models.News {
        var result : News? = null
        storage.forEach { currentNews ->
            val keyWordRegex = "\"$keyWord\""
            if (keyWordRegex in currentNews.keywords){
                result = currentNews
            }
        }
        if (result == null) throw NoSuchFieldException()
        return result!!
    }

   fun fillDataBase(array: ArrayList<com.example.a0_json_and_xml_parser.domain.models.News>) {
       storage = array
       storage.sortWith(compareBy { it.date })
   }
}