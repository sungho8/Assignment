package com.sungho.searchapp.model

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class SearchItem(
    val title : String,
    val thumbnail : String,
    val dateTime : String
){
    fun date(): String {
        val format = LocalDateTime.parse(dateTime, DateTimeFormatter.ISO_DATE_TIME)
        return format.format(DateTimeFormatter.ofPattern("yy.MM.dd hh:mm"))
    }
}