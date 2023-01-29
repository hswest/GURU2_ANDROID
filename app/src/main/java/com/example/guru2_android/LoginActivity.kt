package com.example.guru2_android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.guru2_android.databinding.LoginBinding

class LoginActivity : AppCompatActivity() {
    lateinit var db: DBHelper
    private lateinit var binding: LoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DBHelper(this)

        binding.signupButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.loginButton.setOnClickListener {
            createUser().let {
                if (it != null) {
                    if (db.login(it)) {
                        Toast.makeText(this, "로그인 성공!", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)

                    } else {
                        Toast.makeText(this, "로그인 실패!", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "정보를 모두 입력해주세요", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun createUser(): User2? {
        val id = binding.inputId.text.toString()
        val pw = binding.inputPassword.text.toString()
        if (id == "" || pw == "") // 입력 정보가 하나라도 비어있으면
            return null // Null 반환

        return User2(id, pw)
    }
}