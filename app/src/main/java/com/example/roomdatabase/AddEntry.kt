package com.example.roomdatabase

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import database_material.AppDatabase
import database_material.FinanceLog
import database_material.UserDao

@SuppressLint("StaticFieldLeak")
private lateinit var  dropdown : Spinner
private lateinit var dao :UserDao

private var minus : Boolean = true

private lateinit var item : String

class AddEntry : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_entry)
        dropdown = findViewById(R.id.category)
        dao  = AppDatabase.getDatabase(this).userDao()
        val sumText : EditText = findViewById(R.id.sumEnter)
        val buttonAddEntry : Button = findViewById(R.id.buttonAdd)
        val addCategory : Button = findViewById(R.id.buttonAddCategory)
        val log = intent.getStringExtra("login")!!
        val pass = intent.getStringExtra("pass")!!
        val balance = intent.getDoubleExtra("balance",0.0)

        buttonAddEntry.setOnClickListener {
            if(sumText.text.toString().toDouble() >= balance && minus) {

                Toast.makeText(this, "Not enough money", Toast.LENGTH_LONG).show()
            }
            else {
                dao.addLog(
                    listOf(
                        FinanceLog(
                            0,
                            Integer.parseInt(sumText.text.toString()),
                            dao.findCategoryId(item),
                            dao.findUserId(log)
                        )
                    )
                )
                val intent = Intent(this,MainMenu::class.java)
                intent.putExtra("login",log)
                intent.putExtra("pass",pass)
                this.finish()
                startActivity(intent)

            }

        }
        addCategory.setOnClickListener {
            val intent = Intent(this,AddCategory::class.java)
            intent.putExtra("login",log)
            intent.putExtra("pass",pass)
            intent.putExtra("balance", balance)
            this.finish()
            startActivity(intent)
        }

    }

    private fun fillList(type : String){
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, dao.getAllCategories(type))
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        dropdown.adapter = adapter
        dropdown.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {

                item = adapter.getItem(position)!!
                if (item != null) {
                   Log.e("",dao.findCategoryId(item).toString())
                }
            }
        }
    }

    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            val checked = view.isChecked
            when (view.getId()) {
                R.id.radio_insert ->
                    if (checked) {
                        fillList("Пополнение")
                        minus = false
                    }
                R.id.radio_cut ->
                    if (checked) {
                        fillList("Снятие")
                        minus = true
                    }
            }
        }
    }
}