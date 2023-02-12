package com.example.roomdatabase

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import database_material.*


private lateinit var sharedPreferences : SharedPreferences
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val login : EditText = findViewById(R.id.login)
        val password : EditText = findViewById(R.id.password)
        val buttonLogin : Button = findViewById(R.id.buttonLogin)
        val buttonRegister : Button = findViewById(R.id.buttonReg)


        sharedPreferences = this.getSharedPreferences("pref",0)
        val dao : UserDao = AppDatabase.getDatabase(this).userDao()


    val categories = listOf(
            Category(1,"Зарплата","Пополнение"),
            Category(2,"Перевод кому","Снятие"),
            Category(3,"Перевод от","Пополнение"),
            Category(4,"Еда","Снятие"),
            Category(5,"Транспорт","Снятие"),
            Category(6,"Развлечения","Снятие"),
            Category(7,"Иные пополнения","Пополнение"),
        )

        val users = (User(1,"abc","123"))
        val logs = listOf(FinanceLog(1,3400,1,2),
                          FinanceLog(2,1000,2,2),
                          FinanceLog(3,928,2,2),
                          FinanceLog(4,123,3,2),
                          FinanceLog(5,4000,3,2),)
        dao.addCategory(categories)
        dao.addUser(users)
        dao.addLog(logs)

        buttonLogin.setOnClickListener {
            if (dao.getUserById(login.text.toString(), password.text.toString())){
                storeData(User(0,login.text.toString(), password.text.toString()))
                loggedIn(true)
                val intent = Intent(this, MainMenu::class.java)
                intent.putExtra("login", login.text.toString())
                intent.putExtra("pass", password.text.toString())
                this.finish()
                startActivity(intent)
            }
            else{
                Toast.makeText(this, "Incorrect login or password", Toast.LENGTH_LONG).show()
            }
        }

        buttonRegister.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            this.finish()
            startActivity(intent)
        }

    }

    override fun onStart() {
        super.onStart()
        if(auth()){
            val intent = Intent(this, MainMenu::class.java)
            intent.putExtra("login", sharedPreferences.getString("login",""))
            intent.putExtra("pass", sharedPreferences.getString("pass",""))
            this.finish()
            startActivity(intent)
        }

    }

    private fun auth() : Boolean{
        return getUserLoggedIn()
    }

    private fun storeData(user : User){
        val editor : SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString("login",user.login)
        editor.putString("pass",user.password)
        editor.apply()
    }


    fun loggedIn(logIn : Boolean){
        val editor : SharedPreferences.Editor = sharedPreferences.edit()
        editor.putBoolean("loggedIn", logIn)
        editor.apply()
    }

    fun clearData(){
        val editor : SharedPreferences.Editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

    private fun getUserLoggedIn() : Boolean{
        return sharedPreferences.getBoolean("loggedIn", false)
    }

}