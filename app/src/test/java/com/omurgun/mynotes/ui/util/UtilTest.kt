package com.omurgun.mynotes.ui.util



import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Before
import org.junit.Test
import com.google.common.truth.Truth.assertThat
import org.junit.runner.RunWith
import java.util.*


@RunWith(AndroidJUnit4::class)
class UtilTest {

    private lateinit var context: Context

    @Before
    fun setUp() {
       context = ApplicationProvider.getApplicationContext()
    }

    @After
    fun tearDown() {
    }

    @Test
    fun dateTimeFormatter() {

        val today = Date()
        val date = Util.dateTimeFormatter(today)
        assertThat(date).isEqualTo(today.toString())

    }

    @Test
    fun smashDate() {

        val today = Util.smashDate(Util.dateTimeFormatter(Date()))
        assertThat(today).isNotNull()
    }

    @Test
    fun getMonthString() {
        val monthString = Util.getMonthString(1,ApplicationProvider.getApplicationContext())
        assertThat(monthString).isEqualTo("January")
    }

    @Test
    fun getCustomDate() {
    }
}