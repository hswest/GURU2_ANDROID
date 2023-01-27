package com.example.guru2_android.ui.mydiary

import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.guru2_android.MainActivity
import com.example.guru2_android.R
import com.example.guru2_android.databinding.ActivityDiaryBinding
import java.util.*

class DiaryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDiaryBinding

    private val format = SimpleDateFormat("yyyy-MM-dd", Locale.KOREAN)

    companion object {
        private const val DIARY = R.layout.activity_diary
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, DIARY)

        binding.diarySave.setOnClickListener {
            val title = binding.titleEditText.text
            val content = binding.diaryEditText.text
            if (title == null || content == null) {
                Toast.makeText(this, "제목과 내용을 입력해주세요.", Toast.LENGTH_SHORT).show()

            } else {
                val time = format.format(Date())
                val db = DiaryHelper(this).writableDatabase
                db.execSQL("insert into diary values (\'$title\', \'$content\', \'$time\')")
                db.close()
                Toast.makeText(this, "메모가 등록되었습니다.", Toast.LENGTH_SHORT).show()
                onBackPressed()
            }
        }
    }


    override fun onBackPressed() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
