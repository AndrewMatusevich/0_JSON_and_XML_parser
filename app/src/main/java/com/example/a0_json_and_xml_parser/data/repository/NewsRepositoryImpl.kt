package com.example.a0_json_and_xml_parser.data.repository

import com.example.a0_json_and_xml_parser.data.storage.Storage
import com.example.a0_json_and_xml_parser.domain.models.News
import com.example.a0_json_and_xml_parser.domain.repository.NewsRepository

class NewsRepositoryImpl(private val storage: Storage): NewsRepository {

    override fun getListOfNews(): ArrayList<News> {
        return storage.getListOfNews()
    }

    override fun getNewsByKeyWord(keyWord: String): News {
        return storage.getNewsByKeyWord(keyWord = keyWord)
    }

    override fun fillDataBase(array: ArrayList<News>) {
        storage.fillDataBase(array)
    }
}