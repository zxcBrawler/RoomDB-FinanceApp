package com.example.roomdatabase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import database_material.AppDatabase
import database_material.User
import database_material.UserDao

class Register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val register : Button = findViewById(R.id.buttonRegister)

        val loginReg : EditText = findViewById(R.id.loginReg)
        val passReg : EditText = findViewById(R.id.passwordReg)

        val dao : UserDao = AppDatabase.getDatabase(this).userDao()

        register.setOnClickListener {
            if(!dao.checkUser(loginReg.text.toString(),passReg.text.toString())) {
                dao.addUser(User(0, loginReg.text.toString(), passReg.text.toString()))
                val intent = Intent(this,MainActivity::class.java)
                this.finish()
                startActivity(intent)
            }
            else {
                Toast.makeText(this,"the user already exists", Toast.LENGTH_LONG).show()
            }

        }

    }
}