package com.example.diary_kotlin_simbirsoft

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.provider.CalendarContract
import android.util.DisplayMetrics
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import io.realm.Realm
import android.R.bool
import android.annotation.SuppressLint
import android.icu.util.Calendar
import android.text.format.DateUtils
import androidx.core.view.children
import java.time.Instant
import java.time.Instant.now
import java.time.LocalDateTime
import java.util.*


var realm: Realm? =null
var calendarView:CalendarView?=null

class MainActivity : AppCompatActivity() {

    var deals = mutableListOf<Deal>()
    //private

    var hour_heigh =100.0
    var selectedDate = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        println("aaa lf")
        Realm.init(this)
        Realm.getDefaultInstance().also { realm = it }


        calendarView = findViewById(R.id.calendarView);
        calendarView!!.setOnDateChangeListener {
                view, year, month, dayOfMonth ->
            selectedDate.set(year, month, dayOfMonth)
            update()
        }


        update()
    }

    override fun onResume() {
        super.onResume()
        update()
    }

    fun intToTime(j: Int): String? {
        return if (j < 10) "0$j:00" else "$j:00"
    }

    fun dealInSelectedDay(d: Deal): Boolean {
        val dealDate: String? = d.date
        val selectedDateStr: String? =DateUtils.formatDateTime(
            this,
            selectedDate.timeInMillis,
            DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_SHOW_YEAR

        )
        if (dealDate != null) {
            return dealDate==selectedDateStr
        }
        return false
    }

    fun readData(){
        if(realm!=null){
            println("вот realm хоть есть ")
            if(realm!!.where(Deal::class.java).findAll().size !=0){
                println("вот сколько записей "+realm!!.where(Deal::class.java).findAll().size)
                deals.clear()
                for(i in realm!!.where(Deal::class.java).findAll().filter { d-> dealInSelectedDay(d) }){
                    deals.add(i)
                    println("вот что прочитал "+i.name+" "+i.time_start+" "+i.time_finish)
                }

            }
        }

    }




fun update(){
    println("aaa lf")

    readData()

    val dealsColumns: LinearLayout = findViewById(R.id.deals_columns) as LinearLayout
    dealsColumns.removeAllViews()


    var hours:LinearLayout= LinearLayout(this)
    val lph= LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
        LinearLayout.LayoutParams.WRAP_CONTENT)
    lph.setMargins(20, 3, 10, 0)
    hours.setLayoutParams(lph)
    hours.orientation = LinearLayout.VERTICAL

for(i in 0..23){
    //children of parent linearlayout
    val hour = TextView(this)
    val lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
        LinearLayout.LayoutParams.WRAP_CONTENT)
    lp.setMargins(0, 0, 0, 0)

    hour.setBackgroundColor(Color.CYAN)

    hour.setLayoutParams(lp)

    hour.setText(" "+intToTime(i));
    hour.setTextColor(Color.BLACK)
    hour.getLayoutParams().height = hour_heigh.toInt()
    hour.getLayoutParams().width = 120
    hours.addView(hour); // lo agregamos al layout
}
    dealsColumns.addView(hours)


    ///определить ширину дел
    val displayMetrics = DisplayMetrics()
    windowManager.defaultDisplay.getMetrics(displayMetrics)

    if(deals.size!=0){
        var dealWidth = (displayMetrics.widthPixels-250)/deals.size

        for(i in deals){


            var dealColumn:LinearLayout= LinearLayout(this)
            val lpd= LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT)
            lpd.setMargins(0, 3, 5, 0)
            dealColumn.setLayoutParams(lpd)
            dealColumn.orientation = LinearLayout.VERTICAL


            var block:Space = Space(this)
            block.setBackgroundColor(Color.MAGENTA)
            block.layoutParams= LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT)
            block.getLayoutParams().height = (hour_heigh / 60 * i.time_start).toInt()
            block.getLayoutParams().width = dealWidth

            dealColumn.addView(block)







            val deal = TextView(this)
            val lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT)
            lp.setMargins(0, 0, 0, 0)

            deal.setBackgroundColor(Color.CYAN)

            deal.setLayoutParams(lp)

            deal.setText(i.name);
            deal.setTextColor(Color.BLACK)
            deal.getLayoutParams().height = (hour_heigh /60 *(i.time_finish - i.time_start)).toInt()
            deal.getLayoutParams().width = dealWidth
            dealColumn.addView(deal);





            var block2:Space = Space(this)
            block2.setBackgroundColor(Color.MAGENTA)
            block2.layoutParams= LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT)
            block2.getLayoutParams().height = (hour_heigh /60 *(24 * 60 - i.time_finish)).toInt()
            block2.getLayoutParams().width = dealWidth

            dealColumn.addView(block2)


            dealsColumns.addView(dealColumn)

        }
    }





}

    fun goToAddDeal(view: android.view.View) {
        //val editText = findViewById<EditText>(R.id.editTextTextPersonName)
        val message = "111"
        val intent = Intent(this, AddDealActivity::class.java).apply {
            putExtra(EXTRA_MESSAGE, message)
        }
        startActivity(intent)
    }

    fun upd(view: android.view.View) {
        update()
    }


}