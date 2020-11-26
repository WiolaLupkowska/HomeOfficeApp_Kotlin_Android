package com.example.homeoffice

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class LogIn : AppCompatActivity() {
    var btnEmail: Button? = null
    var btnPassword: Button? = null
    var logInUsername: TextInputLayout? = null
    var logInPassword: TextInputLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)
        logInUsername = findViewById(R.id.log_in_username)
        logInPassword = findViewById(R.id.log_in_password)
    }

    private fun validatePassword(): Boolean {
        val value = logInPassword!!.editText!!.text.toString()
        return if (value.isEmpty()) {
            logInPassword!!.error = "Pole nie może być puste"
            false
        } else {
            logInPassword!!.error = null
            logInPassword!!.isErrorEnabled = false
            true
        }
    }

    private fun validateUsername(): Boolean {
        val `val` = logInUsername!!.editText!!.text.toString()
        return if (`val`.isEmpty()) {
            logInUsername!!.error = "Pole nie może być puste"
            false
        } else {
            logInUsername!!.error = null
            logInUsername!!.isErrorEnabled = false
            true
        }
    }
    val isUsername: Unit
        get() { //sprawdzanie podanego Username  w bazie, pobranie przypisanego do niego hasla
            val userEnteredUsername = logInUsername!!.editText!!.text.toString().trim { it <= ' ' }
            val userEnteredPassword = logInPassword!!.editText!!.text.toString().trim { it <= ' ' }
            println(userEnteredPassword)
            val reference = FirebaseDatabase.getInstance().getReference("users")
            val checkUser = reference.orderByChild("username").equalTo(userEnteredUsername) //szukanie w bazie wpisanego emailu
            checkUser.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) { //jesli są dane w datasnapshot
                        logInUsername!!.error = null
                        logInUsername!!.isErrorEnabled = false
                        val passwordFromDB = dataSnapshot.child(userEnteredUsername).child("password").getValue(String::class.java)!!
                        println(passwordFromDB)
                        if (passwordFromDB == userEnteredPassword) {
                            logInUsername!!.error = null
                            logInUsername!!.isErrorEnabled = false
                            val usernameFromDB = dataSnapshot.child(userEnteredUsername).child("username").getValue(String::class.java)!!
                            val nameFromDB = dataSnapshot.child(userEnteredUsername).child("name").getValue(String::class.java)!!
                            val phoneFromDB = dataSnapshot.child(userEnteredUsername).child("phone").getValue(String::class.java)!!
                            val intent = Intent(applicationContext, MainActivity::class.java)
                            intent.putExtra("name", nameFromDB)
                            intent.putExtra("phone", phoneFromDB)
                            intent.putExtra("email", usernameFromDB)
                            intent.putExtra("password", passwordFromDB)
                            startActivity(intent)
                        } else {
                            logInPassword!!.error = "Nieprawidłowe hasło"
                            logInPassword!!.requestFocus()
                        }
                    } else {
                        logInUsername!!.error = "Nieprawidłowy email"
                        logInUsername!!.requestFocus()
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {}
            })
        }

    fun onClickBtnZaloguj(view: View?) {
        if (!validateUsername() or !validatePassword()) {
            return
        } else {
            isUsername
        }
    }

    fun onClickBtnZarejestruj(view: View?) {
        val intent = Intent(this, SignUp::class.java)
        startActivity(intent)
    }
}