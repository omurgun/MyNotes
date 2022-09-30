package com.omurgun.mynotes.ui.util

import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.icu.text.SimpleDateFormat
import androidx.core.content.res.ResourcesCompat
import com.omurgun.mynotes.data.models.internal.InternalDate
import java.util.*

object Util {

    fun dateTimeFormatter(date : Date, pattern : String = "dd-MM-yyyy HH:mm:ss") : String {
        val simpleDateFormat = SimpleDateFormat(pattern, Locale.ENGLISH)
        return simpleDateFormat.format(date).toString()
    }

    fun getDrawable(id: Int,theme: Resources.Theme): Drawable {
        return ResourcesCompat.getDrawable(theme.resources, id, theme)!!
    }

    fun smashDate(date: String) : InternalDate? {
        var internalDate: InternalDate? = null
        internalDate = try {
            val x = date.split(" ")
            val firstHalf = x[0].split("-")
            val day = firstHalf[0]
            val month = firstHalf[1]
            val year = firstHalf[2]
            val secondHalf = x[1].split(":")
            val hour = secondHalf[0]
            val minute = secondHalf[1]
            val second = secondHalf[2]
            InternalDate(day,month,year,hour,minute,second)
        } catch (e: Exception) {
            null
        }

        return internalDate
    }

    fun getMonthString(month: Int) : String? {
        when (month) {
            1 -> {
                return "Ocak"
            }
            2 -> {
                return "Şubat"
            }
            3 -> {
                return "Mart"
            }
            4 -> {
                return "Nisan"
            }
            5 -> {
                return "Mayıs"
            }
            6 -> {
                return "Haziran"
            }
            7 -> {
                return "Temmuz"
            }
            8 -> {
                return "Ağustos"
            }
            9 -> {
                return "Eylül"
            }
            10 -> {
                return "Ekim"
            }
            11 -> {
                return "Kasım"
            }
            12 -> {
                return "Aralık"
            }
            else -> return null

        }
    }
}