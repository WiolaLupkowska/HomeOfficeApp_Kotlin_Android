package com.example.homeoffice

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUp : AppCompatActivity() {
    var registerName: TextInputLayout? = null
    var registerUsername: TextInputLayout? = null
    var registerPhone: TextInputLayout? = null
    var registerEmail: TextInputLayout? = null
    var registerPassword: TextInputLayout? = null
    var btnRegister: Button? = null
    var rootNode: FirebaseDatabase? = null
    var reference: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        registerName = findViewById(R.id.register_name)
        registerUsername = findViewById(R.id.register_username)
        registerPhone = findViewById(R.id.register_phone)
        registerEmail = findViewById(R.id.register_email)
        registerPassword = findViewById(R.id.register_password)

    }

    fun onClickBtnRejestracja(view: View?) {
        rootNode = FirebaseDatabase.getInstance()
        reference = rootNode!!.getReference("users")

        //pobranie wartości z textfields, jak będzie null to !! powoduje nullpointer
        val name = registerName!!.editText!!.text.toString()
        val username = registerUsername!!.editText!!.text.toString()
        val phone = registerPhone!!.editText!!.text.toString()
        val email = registerEmail!!.editText!!.text.toString()
        val password = registerPassword!!.editText!!.text.toString()
        val helperClass = UserHelperClass(name, username, phone, email, password)
        reference!!.child(phone).setValue(helperClass)
    }
}