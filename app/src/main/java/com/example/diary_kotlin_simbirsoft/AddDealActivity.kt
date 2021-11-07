package com.example.diary_kotlin_simbirsoft

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.icu.util.Calendar
import android.text.format.DateUtils
import android.view.View
import android.widget.EditText
import android.widget.TextView

class AddDealActivity : AppCompatActivity() {

    var editTextName: EditText? = null
    var editTextDescription: EditText? = null
    var editTextDate: TextView? = null
    var editTextTime: TextView? = null
    var editTextTime2: TextView? = null
    var textViewError1: TextView? = null
    var textViewError2: TextView? = null
    var dateAndTimeStart: Calendar = Calendar.getInstance()
    var dateAndTimeFinish: Calendar = Calendar.getInstance()
    var deal:Deal=Deal()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_deal)
        editTextName = findViewById(R.id.editTextName);
        editTextDescription = findViewById(R.id.editTextDescription);
        editTextDate = findViewById(R.id.editTextDate);
        editTextTime = findViewById(R.id.editTextTime);
        editTextTime2 = findViewById(R.id.editTextTime2)
        textViewError1 = findViewById(R.id.textViewError1)
        textViewError2 = findViewById(R.id.textViewError2)
        setInitialDate();
        setInitialTimeStart();
        setInitialTimeEnd();

    }

    fun addData(v: View?){
        deal.name= editTextName?.text.toString()
        deal.description= editTextDescription?.text.toString()

        deal.date= DateUtils.formatDateTime(
            this,
            dateAndTimeStart.getTimeInMillis(),
            DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_SHOW_YEAR

        )
        deal.time_start= dateAndTimeStart.get(Calendar.MILLISECONDS_IN_DAY)/1000/60
        deal.time_finish= dateAndTimeFinish.get(Calendar.MILLISECONDS_IN_DAY)/1000/60

        deal.id = java.util.UUID.randomUUID().toString()
        println("вот это хочу записать "+deal.name+deal.date+" "+deal.id)
        if(deal.time_start>=deal.time_finish || deal.name==""){
            if(deal.time_start>=deal.time_finish)
                textViewError2?.setVisibility(View.VISIBLE)
            else
                textViewError2?.setVisibility(View.INVISIBLE)
            if(deal.name=="")
                textViewError1?.setVisibility(View.VISIBLE)
            else
                textViewError1?.setVisibility(View.INVISIBLE)
        }
        else{
            try {
                realm!!.beginTransaction()
                realm!!.copyToRealmOrUpdate(deal)
                realm!!.commitTransaction()
                //realm!!.close()
            } catch (e: Exception) {
                println(e)
            }
            finish()
        }

    }


    // Dialogs for date and time
    fun setDate(v: View?) {
        DatePickerDialog(
            this@AddDealActivity, d,
            dateAndTimeStart.get(Calendar.YEAR),
            dateAndTimeStart.get(Calendar.MONTH),
            dateAndTimeStart.get(Calendar.DAY_OF_MONTH)
        )
            .show()
    }

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

    // Set initial date and time(current)
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
                dateAndTimeFinish.getTimeInMillis(),
                 DateUtils.FORMAT_SHOW_TIME
            )
        )
    }

    // Listeners
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

    var d =
        OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            dateAndTimeStart.set(Calendar.YEAR, year)
            dateAndTimeStart.set(Calendar.MONTH, monthOfYear)
            dateAndTimeStart.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            setInitialDate()
        }



}