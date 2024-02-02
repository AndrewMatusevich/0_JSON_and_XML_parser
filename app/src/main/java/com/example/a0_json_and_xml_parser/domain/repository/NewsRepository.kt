package com.example.a0_json_and_xml_parser.domain.repository

import com.example.a0_json_and_xml_parser.domain.models.News

interface NewsRepository {
    fun getListOfNews(): ArrayList<News>
    fun getNewsByKeyWord(keyWord: String): News
    fun fillDataBase(array: ArrayList<News>)
}