package chistousov.ilya.passwordkeeper.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import chistousov.ilya.passwordkeeper.domain.model.PasswordModel

class PasswordDiffUtilCallBack : DiffUtil.ItemCallback<PasswordModel>() {
    override fun areItemsTheSame(oldItem: PasswordModel, newItem: PasswordModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PasswordModel, newItem: PasswordModel): Boolean {
        return oldItem == newItem
    }
}