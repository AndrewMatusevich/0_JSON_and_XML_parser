package com.example.a0_json_and_xml_parser.domain.usecases

import com.example.a0_json_and_xml_parser.data.repository.NewsRepositoryImpl
import com.example.a0_json_and_xml_parser.data.storage.Storage
import com.example.a0_json_and_xml_parser.domain.repository.NewsRepository

class GetNewsByKeyWordUseCase(private val newsRepository : NewsRepository = NewsRepositoryImpl(Storage)) {
    operator fun invoke(keyWord : String) = newsRepository.getNewsByKeyWord(keyWord)
}