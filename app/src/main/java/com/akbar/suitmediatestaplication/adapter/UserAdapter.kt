package com.akbar.suitmediatestaplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.akbar.suitmediatestaplication.data.User
import com.akbar.suitmediatestaplication.databinding.ItemUserBinding
import com.squareup.picasso.Picasso

class UserAdapter(
    private val userList: List<User>,
    private val onItemClick: (User) -> Unit
) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener { onItemClick(userList[adapterPosition]) }
        }

        fun bind(user: User) {
            // Use Picasso to load the avatar image
            Picasso.get().load(user.avatar).into(binding.imgImageOutput)

            // Set other TextViews with user data
            binding.tvEmailOutputUser.text = user.email
            binding.tvNameOutputUser.text = "${user.first_name} ${user.last_name}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(userList[position])
    }

    override fun getItemCount(): Int = userList.size
}
