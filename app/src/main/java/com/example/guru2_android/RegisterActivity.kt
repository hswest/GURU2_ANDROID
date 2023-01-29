package com.example.guru2_android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.guru2_android.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    lateinit var db: DBHelper
    private lateinit var binding: ActivityRegisterBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DBHelper(this)

        binding.regiButton.setOnClickListener {
            inputUser().let {
                if (it != null) {
                    db.addUser(it)
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                }
                else {
                    Toast.makeText(this, "정보를 모두 입력해주세요", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
     private fun inputUser(): User? {
         val id = binding.id.text.toString()
         val pw = binding.pw.text.toString()
         val email = binding.email.text.toString()
         val name = binding.name.text.toString()

         if (id == "" || pw == "" || email == "" || name == "") // 입력 정보가 하나라도 비어있으면
             return null // Null 반환

         return User(id, pw, email, name)
     }
}