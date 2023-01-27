package com.example.guru2_android.ui.mydiary

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.guru2_android.databinding.DiaryItemBinding
import java.util.*

class DiaryAdapter(private var todos: Vector<Diary>, private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = DiaryItemBinding.inflate(LayoutInflater.from(context),parent, false)
        return ItemHoder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemHolder = holder as ItemHoder
        val binding = itemHolder.binding
        val pos = (todos.size - 1) - position
        binding.titleTv.text = todos[pos].title
        val date = "작성일 " + todos[pos].date
        binding.dateTv.text = date

    }

    override fun getItemCount(): Int {
        return todos.size
    }

    class ItemHoder : RecyclerView.ViewHolder {
        var binding: DiaryItemBinding

        constructor(binding: DiaryItemBinding) : super(binding.root) {
            this.binding = binding
        }
    }

}