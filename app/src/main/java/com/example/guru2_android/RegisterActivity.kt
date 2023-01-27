package com.example.guru2_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.guru2_android.databinding.ActivityRegisterBinding
import com.example.guru2_android.databinding.LoginBinding

class RegisterActivity : AppCompatActivity() {
    lateinit var db: DBHelper
    var users = ArrayList<User>()
    private lateinit var binding: ActivityRegisterBinding

    lateinit var btnRegister: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        db = DBHelper(this)

        btnRegister = findViewById<Button>(R.id.regiButton)

        btnRegister.setOnClickListener {
            inputUser().let {
                if (it != null) {
                    db.addUser(it)

                }
            }
        }
    }
     private fun inputUser(): User? {
         val id = binding.id.text.toString()
         val pw = binding.pw.toString()
         val email = binding.email.toString()
         val name = binding.name.toString()

         if (id == "" || pw == "" || email == "" || name == "") // 입력 정보가 하나라도 비어있으면
             return null // Null 반환

         return User(id, pw, email, name)
     }
}