package com.example.a0_json_and_xml_parser.domain.models

import java.time.LocalDateTime

data class News(val id: Int,
                val title: String,
                val description: String,
                val date: LocalDateTime,
                val keywords: String ) {}