package com.example.agecal

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private var selectedDate : TextView? = null
    private var showMin : TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnDatePicker: Button = findViewById(R.id.datePicker)
        selectedDate = findViewById(R.id.txtSelectedDate)
        showMin = findViewById(R.id.txtShow)

        btnDatePicker.setOnClickListener {
            clickDatePicker()
        }
    }
    private fun clickDatePicker(){
        val myCal = Calendar.getInstance()
        val year = myCal.get(Calendar.YEAR)
        val month = myCal.get(Calendar.MONTH)
        val day = myCal.get(Calendar.DAY_OF_MONTH)
        val dpd = DatePickerDialog(this,
            {_,selectedYear,selectedMonth,selectedDay ->

                val gotDate = "${selectedDay}/${selectedMonth+1}/${selectedYear}"
                selectedDate?.text = gotDate

                val sdf = SimpleDateFormat("dd/MM/yyyy",Locale.ENGLISH)
                val theDate = sdf.parse(gotDate)

                theDate?.let{
                    val selectedDateInMin : Long = theDate.time /60000 //milli sec to min

                    val currDate = sdf.parse(sdf.format(System.currentTimeMillis()))
                    currDate?.let {
                        val currDateInMin = currDate.time/60000
                        val diff = currDateInMin - selectedDateInMin
                        showMin?.text = "$diff"
                        Toast.makeText(this,"Life! - ${diff*60} in Seconds",Toast.LENGTH_LONG).show()
                    }
                }

            },
            year,
            month,
            day
        )
        dpd.datePicker.maxDate = System.currentTimeMillis()
        dpd.show()
    }
}