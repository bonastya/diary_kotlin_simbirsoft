package com.example.diary_kotlin_simbirsoft

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.DatePicker

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener

import android.widget.TimePicker

import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.icu.util.Calendar

import android.text.format.DateUtils
import android.view.View
import android.widget.EditText
import android.widget.TextView





class AddDealActivity : AppCompatActivity() {

    var editTextDate: TextView? = null
    var editTextTime: TextView? = null
    var editTextTime2: TextView? = null
    var dateAndTimeStart: Calendar = Calendar.getInstance()
    var dateAndTimeFinish: Calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_deal)

        editTextDate = findViewById(R.id.editTextDate);
        editTextTime = findViewById(R.id.editTextTime);
        editTextTime2 = findViewById(R.id.editTextTime2);
        setInitialDate();
        setInitialTimeStart();
        setInitialTimeEnd();

    }


    fun setDate(v: View?) {
        DatePickerDialog(
            this@AddDealActivity, d,
            dateAndTimeStart.get(Calendar.YEAR),
            dateAndTimeStart.get(Calendar.MONTH),
            dateAndTimeStart.get(Calendar.DAY_OF_MONTH)
        )
            .show()
    }

    // отображаем диалоговое окно для выбора времени
    fun setTimeStart(v: View?) {
        TimePickerDialog(
            this@AddDealActivity, t1,
            dateAndTimeStart.get(Calendar.HOUR_OF_DAY),
            dateAndTimeStart.get(Calendar.MINUTE), true
        )
            .show()
    }

    fun setTimeEnd(v: View?) {
        TimePickerDialog(
            this@AddDealActivity, t2,
            dateAndTimeFinish.get(Calendar.HOUR_OF_DAY),
            dateAndTimeFinish.get(Calendar.MINUTE), true
        )
            .show()
    }

    // установка начальных даты и времени
    private fun setInitialDate() {
        editTextDate?.setText(
            DateUtils.formatDateTime(
                this,
                dateAndTimeStart.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_SHOW_YEAR

            )
        )
    }

    private fun setInitialTimeStart() {
        editTextTime?.setText(
            DateUtils.formatDateTime(
                this,
                dateAndTimeStart.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_TIME
            )
        )
    }

    private fun setInitialTimeEnd() {
        editTextTime2?.setText(
            DateUtils.formatDateTime(
                this,
                dateAndTimeFinish.getTimeInMillis()+60*1000*60,
                 DateUtils.FORMAT_SHOW_TIME
            )
        )
    }

    // установка обработчика выбора времени
    var t1 =
        OnTimeSetListener { view, hourOfDay, minute ->
            dateAndTimeStart.set(Calendar.HOUR_OF_DAY, hourOfDay)
            dateAndTimeStart.set(Calendar.MINUTE, minute)
            setInitialTimeStart()
        }

    var t2 =
        OnTimeSetListener { view, hourOfDay, minute ->
            dateAndTimeFinish.set(Calendar.HOUR_OF_DAY, hourOfDay)
            dateAndTimeFinish.set(Calendar.MINUTE, minute)
            setInitialTimeEnd()
        }

    // установка обработчика выбора даты
    var d =
        OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            dateAndTimeStart.set(Calendar.YEAR, year)
            dateAndTimeStart.set(Calendar.MONTH, monthOfYear)
            dateAndTimeStart.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            setInitialDate()
        }














}