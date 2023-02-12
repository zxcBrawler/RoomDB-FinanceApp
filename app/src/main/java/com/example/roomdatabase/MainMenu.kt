package com.example.roomdatabase

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.DataSet
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.ColorTemplate.COLORFUL_COLORS
import com.github.mikephil.charting.utils.ColorTemplate.PASTEL_COLORS
import database_material.*
import kotlin.math.log
private lateinit var dao : UserDao
private lateinit var readAllData: List<CategoryFinance>
private lateinit var login : String
private lateinit var pass : String
private lateinit var chart : PieChart
private lateinit var chart2 : PieChart

class MainMenu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)
        dao  = AppDatabase.getDatabase(this).userDao()
        val mainActivity = MainActivity()
        chart = findViewById(R.id.chart)
        chart2 = findViewById(R.id.chart2)

        val loginTextView : TextView = findViewById(R.id.loginText)
        val passTextView : TextView = findViewById(R.id.passwordText)
        val balanceTextView : TextView = findViewById(R.id.balance)


        val logout : Button = findViewById(R.id.logout)
        val add : Button = findViewById(R.id.add)
        val edit : Button = findViewById(R.id.edit)



        login = intent.getStringExtra("login")!!
        pass = intent.getStringExtra("pass")!!

        loginTextView.text = "Логин:  $login"
        passTextView.text = "Пароль:  $pass"


        readAllData = dao.getFinanceById(login)

        val balanceIncome = dao.getUserSpends(login,"Пополнение")
        val balanceOutcome = dao.getUserSpends(login,"Снятие")
        val result = balanceIncome - balanceOutcome
        balanceTextView.text = result.toString()

        add.setOnClickListener {
            val intent = Intent(this, AddEntry::class.java)
            intent.putExtra("login",login)
            intent.putExtra("pass",pass)
            intent.putExtra("balance", result)
            this.finish()
            startActivity(intent)
        }
        edit.setOnClickListener {
            val intent = Intent(this, EditProfile::class.java)
            intent.putExtra("login",login)
            intent.putExtra("pass",pass)

            this.finish()
            startActivity(intent)
        }

        getReplenishes()




        logout.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            mainActivity.clearData()
            mainActivity.loggedIn(false)
            this.finish()
            startActivity(intent)
        }


    }

    private fun getReplenishes(){
        val replenishPie : ArrayList<PieEntry> = arrayListOf()
        val cutPie : ArrayList<PieEntry> = arrayListOf()

        val listTypeReplenish : ArrayList<String> = arrayListOf()
        val listTypeCut : ArrayList<String> = arrayListOf()

        readAllData.forEach {
            if (it.category.type == "Пополнение")
                listTypeReplenish.add(it.category.name)
            else listTypeCut.add(it.category.name)
        }
        for(item in listTypeReplenish.distinct()) {
            replenishPie.add(PieEntry(dao.getSpendings(login, item).toFloat(), item))
        }
        for(item in listTypeCut.distinct()) {
            cutPie.add(PieEntry(dao.getSpendings(login, item).toFloat(), item))
        }
        val dataSet  = PieDataSet(replenishPie, "")
        val dataSet2  = PieDataSet(cutPie, "")
        dataSet.setColors(COLORFUL_COLORS,255)
        dataSet2.setColors(PASTEL_COLORS,255)

        val data  = PieData(dataSet)
        val data2  = PieData(dataSet2)

        chart.data = data
        chart2.data = data2

    }


}


