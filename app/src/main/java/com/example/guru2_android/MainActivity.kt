package com.example.guru2_android

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.graphics.Paint
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.provider.SyncStateContract.Helpers.update
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.guru2_android.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var binding: ActivityMainBinding

    //날짜 선택 변수
    private lateinit var date: TextView
    private lateinit var selectDate: Button

    private var selectDateBtn: Button? = null
    private var dateTextView: TextView? = null
    private var cal = Calendar.getInstance()

    //투두리스트 변수
    //private lateinit var toDoView : ArrayList<String>
    //private lateinit var adapter : ArrayAdapter<String>
    private lateinit var toDoEdit : EditText

    lateinit var toDoViewModel: ToDoViewModel
    lateinit var todoAdapter: ToDoAdapter
    lateinit var toDoView: RecyclerView

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
                R.id.nav_home, R.id.nav_mypage, R.id.nav_information
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        toDoView = findViewById(R.id.toDoView)
        //val listView: ListView = findViewById(R.id.toDoView)
        //val addBtn: Button = findViewById(R.id.addButton)
        toDoEdit = findViewById(R.id.toDoEdit)

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
                //모르겠음!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                CoroutineScope(Dispatchers.IO).launch {
                    toDoViewModel.getByDate(date.text.toString())
                }
            }
        })

    }

    private fun updateDateInView() {
        val myFormat = "yyyy-MM-dd" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        dateTextView!!.text = sdf.format(cal.getTime())
    }

    //투두리스트 구현
    private fun toDoList() {
        //toDoView = ArrayList()

        //adapter = ArrayAdapter(this, R.layout.todo_item, toDoView)

        //val listView: ListView = findViewById(R.id.toDoView)
        val addBtn: Button = findViewById(R.id.addButton)
        toDoEdit = findViewById(R.id.toDoEdit)

        //listView.adapter = adapter

        addBtn.setOnClickListener {
            val title = toDoEdit.text.toString()
            val toDoDate = dateTextView?.text.toString()

            if(title.isNotEmpty()) {
                val todo = Todo(0, title, toDoDate, false)

                //registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {

                //val todoAdd = it.data?.getSerializableExtra("todo") as Todo

                //when(it.data?.getIntExtra("flag", -1)) {
                //0 -> {
                CoroutineScope(Dispatchers.IO).launch {
                    toDoViewModel.insert(todo)
                }
                Toast.makeText(this, "추가되었습니다.", Toast.LENGTH_SHORT).show()
                //}
                //}
                //}


            } else {
                Toast.makeText(this, "할 일을 적어주세요", Toast.LENGTH_SHORT).show()
            }
        }

        /*
        listView.setOnItemClickListener { adapterView, view, i, l ->
            val textView: TextView = view as TextView

            textView.paintFlags = textView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        }

         */

        toDoViewModel = ViewModelProvider(this)[ToDoViewModel::class.java]

        toDoViewModel.todoList.observe(this) {
            todoAdapter.update(it)
        }

        todoAdapter = ToDoAdapter(this)
        toDoView.layoutManager = LinearLayoutManager(this)
        toDoView.adapter = todoAdapter

        todoAdapter.setItemCheckBoxClickListener(object: ToDoAdapter.ItemCheckBoxClickListener {
            override fun onClick(view: View, position: Int, itemId: Long) {
                CoroutineScope(Dispatchers.IO).launch {
                    val todo = toDoViewModel.getOne(itemId)
                    todo.isChecked = !todo.isChecked
                    toDoViewModel.update(todo)
                }
            }
        })
    }
/*
    private fun addItem() {
        val title = toDoEdit.text.toString()
        val toDoDate = dateTextView?.text.toString()

        if(title.isNotEmpty()) {
            val todo = Todo(0, title, toDoDate, false)

            //registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {

                    //val todoAdd = it.data?.getSerializableExtra("todo") as Todo

                    //when(it.data?.getIntExtra("flag", -1)) {
                        //0 -> {
                            CoroutineScope(Dispatchers.IO).launch {
                                toDoViewModel.insert(todo)
                            }
                            Toast.makeText(this, "추가되었습니다.", Toast.LENGTH_SHORT).show()
                        //}
                    //}
                //}


        } else {
            Toast.makeText(this, "할 일을 적어주세요", Toast.LENGTH_SHORT).show()
        }
    }
*/
}