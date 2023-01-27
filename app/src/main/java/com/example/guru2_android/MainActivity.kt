package com.example.guru2_android

import android.app.DatePickerDialog
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.guru2_android.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


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

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_mydiary, R.id.nav_information
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        toDoView = findViewById(R.id.toDoView)
        toDoEdit = findViewById(R.id.toDoEdit)

        toDoViewModel = ViewModelProvider(this)[ToDoViewModel::class.java]
        toDoViewModel.list(dateTextView?.text.toString()).observe(this@MainActivity) {
            todoAdapter.update(it as MutableList<Todo>)
        }

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
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)).show()
            }
        })

    }

    private fun updateDateInView() {
        val myFormat = "yyyy-MM-dd"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        dateTextView!!.text = sdf.format(cal.getTime())

        toDoViewModel = ViewModelProvider(this)[ToDoViewModel::class.java]

        toDoViewModel.list(dateTextView?.text.toString()).observe(this) {
            todoAdapter.update(it as MutableList<Todo>)
        }
    }

    //투두리스트 구현
    private fun toDoList() {
        val addBtn: Button = findViewById(R.id.addButton)
        toDoEdit = findViewById(R.id.toDoEdit)

        toDoViewModel = ViewModelProvider(this)[ToDoViewModel::class.java]
        addBtn.setOnClickListener {
            val title = toDoEdit.text.toString()
            val toDoDate = dateTextView?.text.toString()

            //투두 추가
            if(title.isNotEmpty()) {
                val todo = Todo(0, title, toDoDate, false, false)

                CoroutineScope(Dispatchers.IO).launch {
                    toDoViewModel.insert(todo)
                }
                Toast.makeText(this, "추가되었습니다.", Toast.LENGTH_SHORT).show()

                toDoViewModel.list(dateTextView?.text.toString()).observe(this@MainActivity) {
                    todoAdapter.update(it as MutableList<Todo>)
                }
            } else {
                Toast.makeText(this, "할 일을 적어주세요", Toast.LENGTH_SHORT).show()
            }
        }

        todoAdapter = ToDoAdapter(this)
        toDoView.layoutManager = LinearLayoutManager(this)
        toDoView.adapter = todoAdapter

        //체크
        todoAdapter.setItemCheckBoxClickListener(object: ToDoAdapter.ItemCheckBoxClickListener {
            override fun onClick(view: View, position: Int, itemId: Long) {
                CoroutineScope(Dispatchers.IO).launch {
                    val todo = toDoViewModel.getOne(itemId)
                    todo.isChecked = !todo.isChecked
                    toDoViewModel.update(todo)
                }
            }
        })

       //삭제
        todoAdapter.setItemDeleteClickListener(object: ToDoAdapter.ItemDeleteClickListener {
            override fun onClick(view: View, position: Int, itemId: Long) {
                CoroutineScope(Dispatchers.IO).launch {
                    toDoViewModel.delete(itemId)
                }
            }
        })
    }
}