package chistousov.ilya.passwordkeeper.presentation.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import chistousov.ilya.passwordkeeper.databinding.PasswordItemBinding
import chistousov.ilya.passwordkeeper.domain.model.PasswordModel


class PasswordAdapter(private val itemClickListener: (Int) -> Unit) : ListAdapter<PasswordModel,
        PasswordAdapter.PasswordViewHolder>(PasswordDiffUtilCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PasswordViewHolder {
        val binding = PasswordItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)

        return PasswordViewHolder(binding) {
            itemClickListener(getItem(it).id)
        }
    }

    override fun onBindViewHolder(holder: PasswordViewHolder, position: Int) {
        holder.create(currentList[position])
    }


    class PasswordViewHolder(private val binding: PasswordItemBinding,
    private val clickAtPosition: (Int) -> Unit)
        : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                clickAtPosition(adapterPosition)
            }
        }
        fun create(item: PasswordModel) {
            binding.passwordLogin.text = item.login
            binding.passwordTitle.text = item.title
        }
    }
}