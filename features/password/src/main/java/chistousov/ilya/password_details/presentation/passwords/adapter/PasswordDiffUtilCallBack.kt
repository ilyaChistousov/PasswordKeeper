package chistousov.ilya.password_details.presentation.passwords.adapter

import androidx.recyclerview.widget.DiffUtil
import chistousov.ilya.password_details.domain.entity.PasswordModel

class PasswordDiffUtilCallBack : DiffUtil.ItemCallback<PasswordModel>() {
    override fun areItemsTheSame(oldItem: PasswordModel, newItem: PasswordModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PasswordModel, newItem: PasswordModel): Boolean {
        return oldItem == newItem
    }
}