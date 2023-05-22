package chistousov.ilya.passwordkeeper.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import chistousov.ilya.passwordkeeper.R
import chistousov.ilya.passwordkeeper.databinding.PasswordItemBinding
import chistousov.ilya.passwordkeeper.domain.model.PasswordModel


class PasswordAdapter : ListAdapter<PasswordModel,
        PasswordAdapter.PasswordViewHolder>(PasswordDiffUtilCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PasswordViewHolder {
        val binding = PasswordItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        return PasswordViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PasswordViewHolder, position: Int) {
        holder.create(currentList[position])
    }


    class PasswordViewHolder(private val binding: PasswordItemBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun create(item: PasswordModel) {
            binding.passwordLogin.text = item.login
            binding.passwordTitle.text = item.title
        }
    }
}