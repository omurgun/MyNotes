package com.omurgun.mynotes.ui.util

import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.icu.text.SimpleDateFormat
import androidx.core.content.res.ResourcesCompat
import com.omurgun.mynotes.R
import com.omurgun.mynotes.data.models.internal.InternalDate
import java.util.*

object Util {

    // UtilTest

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

    fun getMonthString(month: Int,context: Context) : String? {
        when (month) {
            1 -> {
                return context.getString(R.string.january)
            }
            2 -> {
                return context.getString(R.string.february)
            }
            3 -> {
                return context.getString(R.string.march)
            }
            4 -> {
                return context.getString(R.string.april)
            }
            5 -> {
                return context.getString(R.string.may)
            }
            6 -> {
                return context.getString(R.string.june)
            }
            7 -> {
                return context.getString(R.string.july)
            }
            8 -> {
                return context.getString(R.string.august)
            }
            9 -> {
                return context.getString(R.string.september)
            }
            10 -> {
                return context.getString(R.string.october)
            }
            11 -> {
                return context.getString(R.string.november)
            }
            12 -> {
                return context.getString(R.string.december)
            }
            else -> return null

        }
    }

    fun getCustomDate(date: String,context: Context) : String? {
        val today = smashDate(dateTimeFormatter(Date()))
        val currentDate = smashDate(date)

        if (today != null && currentDate != null) {
            if (today.year == currentDate.year) {
                return if (today.month == currentDate.month) {
                    if (today.day == currentDate.day) {
                        "${currentDate.hour} ${currentDate.minute}"
                    } else if (Integer.parseInt(today.day) == Integer.parseInt(currentDate.day) + 1) {
                        "${context.getString(R.string.yesterday)} ${currentDate.hour} ${currentDate.minute}"
                    } else {
                        "${currentDate.day} ${getMonthString(Integer.parseInt(currentDate.month),context) ?: ""}"
                    }

                } else {
                    "${currentDate.day}  ${getMonthString(Integer.parseInt(currentDate.month),context) ?: ""}"
                }
            }
            else {
                return "${currentDate.day} ${currentDate.month} ${currentDate.year}"
            }
        }
        return null
    }
}