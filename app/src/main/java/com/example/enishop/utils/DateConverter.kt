package com.example.enishop.utils

import java.text.SimpleDateFormat
import java.util.Date

object DateConverter {
    val formatter = SimpleDateFormat("dd/MM/yyyy")

    fun convertDateToSimpleString(date : Date): String {
        return formatter.format(date)
    }
}