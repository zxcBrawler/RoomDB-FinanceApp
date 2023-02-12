package com.example.roomdatabase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import database_material.AppDatabase
import database_material.Category
import database_material.UserDao

private lateinit var dao : UserDao
private lateinit var type :String
class AddCategory : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_category)
        val button : Button = findViewById(R.id.addCategory)
        val nameCategory : EditText = findViewById(R.id.categoryEnter)
        val log = intent.getStringExtra("login")!!
        val pass = intent.getStringExtra("pass")!!
        val balance = intent.getDoubleExtra("balance",0.0)

        dao = AppDatabase.getDatabase(this).userDao()

        button.setOnClickListener {
            addCategory(type,nameCategory.text.toString())
            val intent = Intent(this,AddEntry::class.java)
            intent.putExtra("login",log)
            intent.putExtra("pass",pass)
            intent.putExtra("balance",balance)
            this.finish()
            startActivity(intent)
        }
    }

    fun onRadioButtonClickedAdd(view: View) {
        if (view is RadioButton) {
            val checked = view.isChecked
            when (view.getId()) {
                R.id.radio_insertAdd ->
                    if (checked) {
                        type = "Пополнение"

                    }
                R.id.radio_cutAdd ->
                    if (checked) {
                        type = "Снятие"
                    }
            }
        }
    }

    private fun addCategory(type : String, name : String){
        dao.addCategory(listOf(Category(0,name,type)))
    }
}