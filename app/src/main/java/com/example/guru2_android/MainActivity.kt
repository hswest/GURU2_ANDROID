package com.example.guru2_android

import android.app.DatePickerDialog
import android.graphics.Paint
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.guru2_android.databinding.ActivityMainBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    //날짜 선택 변수
    private lateinit var date: TextView
    private lateinit var selectDate: Button

    private var selectDateBtn: Button? = null
    private var dateTextView: TextView? = null
    private var cal = Calendar.getInstance()

    //투두리스트 변수
    private lateinit var toDoListView : ArrayList<String>
    private lateinit var adapter : ArrayAdapter<String>
    private lateinit var toDoEdit : EditText

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)


        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_mypage, R.id.nav_slideshow
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        //날짜 선택
        selectDate()

        //투두리스트
        toDoList()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    //날짜 선택
    @RequiresApi(Build.VERSION_CODES.O)
    private fun selectDate() {
        date = findViewById(R.id.date)
        selectDate = findViewById(R.id.selectDate)

        dateTextView = this.date
        selectDateBtn = this.selectDate

        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ISO_DATE
        val formatted = current.format(formatter)

        dateTextView!!.text = "$formatted"

        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int,
                                   dayOfMonth: Int) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            }
        }

        selectDateBtn!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                DatePickerDialog(this@MainActivity,
                    dateSetListener,
                    // set DatePickerDialog to point to today's date when it loads up
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)).show()
            }
        })
    }

    private fun updateDateInView() {
        val myFormat = "yyyy/MM/dd" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        dateTextView!!.text = sdf.format(cal.getTime())
    }

    //투두리스트 구현
    private fun toDoList() {
        toDoListView = ArrayList()

        adapter = ArrayAdapter(this, R.layout.todo_item, toDoListView)

        val listView: ListView = findViewById(R.id.toDoView)
        val addBtn: Button = findViewById(R.id.addButton)
        toDoEdit = findViewById(R.id.toDoEdit)

        listView.adapter = adapter

        addBtn.setOnClickListener {
            addItem()
        }

        listView.setOnItemClickListener { adapterView, view, i, l ->
            val textView: TextView = view as TextView

            textView.paintFlags = textView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        }
    }

    private fun addItem() {
        val toDo : String = toDoEdit.text.toString()

        if(toDo.isNotEmpty()) {
            toDoListView.add(toDo)

            adapter.notifyDataSetChanged()
        } else {
            Toast.makeText(this, "할 일을 적어주세요", Toast.LENGTH_SHORT).show()
        }
    }

}