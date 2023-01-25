package com.example.guru2_android

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.guru2_android.databinding.UserItemBinding

class UserListAdapter: ListAdapter<User, UserListAdapter.ViewHolder>(diffUtil) {
    inner class ViewHolder(private val binding: UserItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(user: User){
            binding.idTextView.text = user.id
            binding.nameTextView.text = user.email
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object{
        val diffUtil = object: DiffUtil.ItemCallback<User>(){
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem.pw == newItem.pw
            }
        }
    }
}