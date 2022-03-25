package com.companyglobal.alef_app.ui.fragments.notification.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.companyglobal.alef_app.core.BaseViewHolder
import com.companyglobal.alef_app.core.utils.Timestamp
import com.companyglobal.alef_app.data.model.Notification
import com.companyglobal.alef_app.databinding.ItemNotificationBinding

class NotificationAdapter(
    private var notificationList: List<Notification>,
    private val itemClickListener: OnNotificationClickListener
) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    interface OnNotificationClickListener {
        fun onNotificationClick(notification: Notification)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding = ItemNotificationBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        val holder = NotificationViewHolder(itemBinding, parent.context)

        itemBinding.root.setOnClickListener {
            val position =
                holder.bindingAdapterPosition.takeIf { it != DiffUtil.DiffResult.NO_POSITION }
                    ?: return@setOnClickListener
            itemClickListener.onNotificationClick(notificationList[position])
        }

        return holder
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is NotificationViewHolder -> holder.bind(notificationList[position])
        }
    }

    override fun getItemCount(): Int = notificationList.size

    @SuppressLint("NotifyDataSetChanged")
    fun setMarkAllView(newNotificationList: List<Notification>) {
        this.notificationList = newNotificationList
        notifyDataSetChanged()
    }

    private inner class NotificationViewHolder(
        val binding: ItemNotificationBinding,
        val context: Context
    ) : BaseViewHolder<Notification>(binding.root) {
        override fun bind(item: Notification) {
            with(binding) {
                addNotificationTimestamp(item)

                tvTitleNotification.text = item.title
                tvDescription.text = item.description

                if (item.isView) {
                    llStateNotify.visibility = View.VISIBLE
                } else {
                    llStateNotify.visibility = View.GONE
                }

            }
        }

        private fun addNotificationTimestamp(notification: Notification) {
            with(binding) {
                val createdAt = (notification.timestamp?.time?.div(1000L))?.let {
                    Timestamp.getTimeAgo(it.toInt())
                }

                tvTimestamp.text = createdAt
            }
        }
    }

}