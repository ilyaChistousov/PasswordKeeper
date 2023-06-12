package chistousov.ilya.password_details.presentation.passwords.adapter

import android.content.ClipData
import android.content.ClipboardManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import chistousov.ilya.password_details.R
import chistousov.ilya.password_details.databinding.PasswordItemBinding
import chistousov.ilya.password_details.domain.entity.PasswordModel


class PasswordAdapter(private val listener: ClickListener) : ListAdapter<PasswordModel,
        PasswordAdapter.PasswordViewHolder>(PasswordDiffUtilCallBack()), View.OnClickListener {

    override fun onClick(v: View?) {
        val password = v?.tag as PasswordModel

        when (v.id) {
            R.id.passwordCopy -> listener.onPasswordCopyClick(password)
            R.id.passwordDelete -> listener.onPasswordDeleteClick(password)
            else -> listener.onPasswordItemClick(password)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PasswordViewHolder {
        val binding = PasswordItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        binding.root.setOnClickListener(this)
        binding.passwordCopy.setOnClickListener(this)
        binding.passwordDelete.setOnClickListener(this)

        return PasswordViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PasswordViewHolder, position: Int) {
        val password = getItem(position)
        with(holder.binding) {
            root.tag = password
            passwordCopy.tag = password
            passwordDelete.tag = password
        }
        holder.create(password)
    }

    interface ClickListener {
        fun onPasswordItemClick(password: PasswordModel)
        fun onPasswordDeleteClick(password: PasswordModel)
        fun onPasswordCopyClick(password: PasswordModel)
    }

    class PasswordViewHolder(
        val binding: PasswordItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun create(item: PasswordModel) {
            binding.passwordLogin.text = item.login
            binding.passwordTitle.text = item.title
        }
    }
}