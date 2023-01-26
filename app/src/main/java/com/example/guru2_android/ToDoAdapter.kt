package com.example.guru2_android

import android.content.Context
import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ToDoAdapter(val context: Context): RecyclerView.Adapter<ToDoAdapter.TodoViewHolder>() {

    private var list = mutableListOf<Todo>()

    inner class TodoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        var title = itemView.findViewById<TextView>(R.id.title)
        var toDoDate = itemView.findViewById<TextView>(R.id.toDoDate)
        var checkbox = itemView.findViewById<CheckBox>(R.id.cbCheck)
        var deleteButton = itemView.findViewById<CheckBox>(R.id.deleteBtn)

        fun onBind(data: Todo) {
            title.text = data.title
            toDoDate.text = data.toDoDate
            checkbox.isChecked = data.isChecked
            deleteButton.isChecked = data.isClicked

            if (data.isChecked) {
                title.paintFlags = title.paintFlags or STRIKE_THRU_TEXT_FLAG
            } else {
                title.paintFlags = title.paintFlags and STRIKE_THRU_TEXT_FLAG.inv()
            }

            checkbox.setOnClickListener{
                itemCheckBoxClickListener.onClick(it, layoutPosition, list[layoutPosition].id)
            }

            deleteButton.setOnClickListener{
                itemDeleteClickListener.onClick(it, layoutPosition, list[layoutPosition].id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.todo_item, parent, false)
        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun update(newList: MutableList<Todo>) {
        this.list = newList
        notifyDataSetChanged()
    }


    //체크
    interface ItemCheckBoxClickListener {
        fun onClick(view: View, position: Int, itemId: Long)
    }

    private lateinit var itemCheckBoxClickListener: ItemCheckBoxClickListener

    fun setItemCheckBoxClickListener(itemCheckBoxClickListener: ItemCheckBoxClickListener) {
        this.itemCheckBoxClickListener = itemCheckBoxClickListener
    }

    //삭제
    interface ItemDeleteClickListener {
        fun onClick(view: View, position: Int, itemId: Long)
    }

    private lateinit var itemDeleteClickListener: ItemDeleteClickListener

    fun setItemDeleteClickListener(itemDeleteClickListener: ItemDeleteClickListener) {
        this.itemDeleteClickListener = itemDeleteClickListener
    }
}