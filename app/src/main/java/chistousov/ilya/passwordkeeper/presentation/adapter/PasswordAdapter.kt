package chistousov.ilya.passwordkeeper.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import chistousov.ilya.passwordkeeper.R
import chistousov.ilya.passwordkeeper.domain.model.PasswordModel


class PasswordAdapter : ListAdapter<PasswordModel,
        PasswordAdapter.PasswordViewHolder>(PasswordDiffUtilCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PasswordViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.password_item,
            parent,
            false)

        return PasswordViewHolder(view)
    }

    override fun onBindViewHolder(holder: PasswordViewHolder, position: Int) {

    }


    class PasswordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}