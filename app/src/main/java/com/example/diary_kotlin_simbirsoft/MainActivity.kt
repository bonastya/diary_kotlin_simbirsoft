package com.example.diary_kotlin_simbirsoft

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.CalendarContract
import android.util.DisplayMetrics
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Space
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    var hour_heigh =100


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        update()
    }

    fun intToTime(j: Int): String? {
        return if (j < 10) "0$j:00" else "$j:00"
    }
fun update(){

    var deal1 = Deal()
    deal1.name="name1"
    deal1.date=111
    deal1.time_start=111
    deal1.time_finish=222

    var deal2 = Deal()
    deal2.name="name2"
    deal2.date=111
    deal2.time_start=211
    deal2.time_finish=322
    var deals = listOf<Deal>(deal1,deal2,deal2)



    val dealsColumns: LinearLayout = findViewById(R.id.deals_columns) as LinearLayout

    var hours:LinearLayout= LinearLayout(this)
    val lph= LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
        LinearLayout.LayoutParams.WRAP_CONTENT)
    lph.setMargins(80, 10, 10, 0)
    hours.setLayoutParams(lph)
    hours.orientation = LinearLayout.VERTICAL

for(i in 0..24){
    //children of parent linearlayout
    val hour = TextView(this)
    val lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
        LinearLayout.LayoutParams.WRAP_CONTENT)
    lp.setMargins(0, 0, 7, 0)

    hour.setBackgroundColor(Color.CYAN)

    hour.setLayoutParams(lp)

    hour.setText(intToTime(i));
    hour.setTextColor(Color.BLACK)
    hour.getLayoutParams().height = hour_heigh
    hour.getLayoutParams().width = 120
    hours.addView(hour); // lo agregamos al layout
}
    dealsColumns.addView(hours)


    ///определить ширину дел
    val displayMetrics = DisplayMetrics()
    windowManager.defaultDisplay.getMetrics(displayMetrics)

    var dealWidth = (displayMetrics.widthPixels-300)/deals.size

    for(i in deals){



        println("делаю дело")
        var dealColumn:LinearLayout= LinearLayout(this)
        val lpd= LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)
        lpd.setMargins(0, 0, 5, 0)
        dealColumn.setLayoutParams(lpd)
        dealColumn.orientation = LinearLayout.VERTICAL


        var block:Space = Space(this)
        block.setBackgroundColor(Color.MAGENTA)
        block.layoutParams= LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)
        block.getLayoutParams().height = hour_heigh / 60 * i.time_start
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
        deal.getLayoutParams().height = hour_heigh /60 *(i.time_finish - i.time_start)
        deal.getLayoutParams().width = dealWidth
        dealColumn.addView(deal);





        var block2:Space = Space(this)
        block2.setBackgroundColor(Color.MAGENTA)
        block2.layoutParams= LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)
        block2.getLayoutParams().height = hour_heigh /60 *(24 * 60 - i.time_finish)
        block2.getLayoutParams().width = dealWidth

        dealColumn.addView(block2)


        dealsColumns.addView(dealColumn)

    }



/*

    Column(children: [
            for (int j = 0; j < 24; j++)
    Container(
        color: Color(0xff3AF0E5),
        height: hour_heigh * 1,
    width: 40,
    margin: EdgeInsets.only(right: 2),
    child: Text(intToTime(j)),
    )
    ]),
    if (deals.length == 0)
        Expanded(
            child: Column(
                    children: [
                Container(
                    color: Colors.white,
                width: 2000,
    height: hour_heigh * 24,
    )
    ],
    )),
    for (Deal d in deals)
    Expanded(
        child: Column(
                children: [
            Container(
                color: Colors.white,
            height: hour_heigh / 60 * d.time_start,
    width: 2000,
    ),
    Container(
        alignment: Alignment.topCenter,
    color: Color(0xffd1fff6),
    height: hour_heigh /
    60 *
            (d.time_finish - d.time_start),
    width: 2000,
    child: Text(
    d.name + "\n" + "(" + d.description + ")",
    textAlign: TextAlign.center,
    ),
    ),
    Container(
        color: Colors.white,
    height: hour_heigh /
    60 *
            (24 * 60 - d.time_finish),
    width: 2000,
    )
    ],
    ),
    ),


*/







}




















}