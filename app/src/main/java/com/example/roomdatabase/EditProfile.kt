package com.example.roomdatabase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import database_material.AppDatabase
import database_material.User

class EditProfile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        val loginText : EditText = findViewById(R.id.loginEdit)
        val passwordText : EditText = findViewById(R.id.passwordEdit)
        val edit : Button = findViewById(R.id.editProfile)
        val mainActivity = MainActivity()

        val login = intent.getStringExtra("login")!!
        val password = intent.getStringExtra("pass")
        val dao  = AppDatabase.getDatabase(this).userDao()
       loginText.setText(login)
       passwordText.setText(password)
        edit.setOnClickListener {

            val l = loginText.text.toString()
            val p = passwordText.text.toString()
            val user : User = User(dao.findUserId(login),l,p)
            dao.updateUser(user)

            val intent = Intent(this,MainActivity::class.java)
            mainActivity.clearData()
            mainActivity.loggedIn(false)
            this.finish()
            startActivity(intent)
            Toast.makeText(this,"Log In again",Toast.LENGTH_LONG).show()
        }

    }
}