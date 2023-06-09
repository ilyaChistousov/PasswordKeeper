package chistousov.ilya.password_details.presentation.passwords.adapter

import android.content.ClipData
import android.content.ClipboardManager
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import chistousov.ilya.password_details.R
import chistousov.ilya.password_details.databinding.PasswordItemBinding
import chistousov.ilya.password_details.domain.entity.PasswordModel


class PasswordAdapter(private val itemClickListener: (Int) -> Unit) : ListAdapter<PasswordModel,
        PasswordAdapter.PasswordViewHolder>(PasswordDiffUtilCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PasswordViewHolder {
        val binding = PasswordItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return PasswordViewHolder(binding) {
            itemClickListener(getItem(it).id)
        }
    }

    override fun onBindViewHolder(holder: PasswordViewHolder, position: Int) {
        holder.create(currentList[position])
    }


    class PasswordViewHolder(
        private val binding: PasswordItemBinding,
        private val clickAtPosition: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                clickAtPosition(adapterPosition)
            }
        }

        fun create(item: PasswordModel) {
            binding.passwordLogin.text = item.login
            binding.passwordTitle.text = item.title
            binding.passwordCopy.setOnClickListener {
                val clipboardManager =
                    binding.root.context.getSystemService(ClipboardManager::class.java)
                val clipData = ClipData.newPlainText("label", item.password)
                clipboardManager.setPrimaryClip(clipData)
                Toast.makeText(
                    binding.root.context,
                    R.string.password_copied,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}