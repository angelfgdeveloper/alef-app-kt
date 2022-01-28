package com.alefglobalintegralproductivityconsulting.alef_app.ui.fragments.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alefglobalintegralproductivityconsulting.alef_app.core.BaseViewHolder
import com.alefglobalintegralproductivityconsulting.alef_app.data.model.Vacant
import com.alefglobalintegralproductivityconsulting.alef_app.databinding.ItemVacantBinding

class VacantAdapter(
    private val vacantList: List<Vacant>,
    private val itemClickListener: OnVacantClickListener
) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    interface OnVacantClickListener {
        fun onVacantClick(vacant: Vacant)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding = ItemVacantBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        val holder = VacantViewHolder(itemBinding, parent.context)

        itemBinding.root.setOnClickListener {
            val position =
                holder.bindingAdapterPosition.takeIf { it != DiffUtil.DiffResult.NO_POSITION }
                    ?: return@setOnClickListener
            itemClickListener.onVacantClick(vacantList[position])
        }

        return holder
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is VacantViewHolder -> holder.bind(vacantList[position])
        }
    }

    override fun getItemCount(): Int = vacantList.size

    private inner class VacantViewHolder(
        val binding: ItemVacantBinding,
        val context: Context
    ) : BaseViewHolder<Vacant>(binding.root) {
        override fun bind(item: Vacant) {
            with(binding) {
                tvLocation.text = item.location
                tvTimestamp.text = item.timestamp
                tvTitleVacant.text = item.title
                tvCompany.text = item.company
                tvDescription.text = item.description

                if ((item.firstSalary != -1 || item.secondSalary != -1) && item.paymentMethod.isNotEmpty()) {
                    llSalary.visibility = View.VISIBLE

                    if (item.firstSalary != -1 && item.secondSalary != -1) {
                        tvSalary.text = "$${item.firstSalary} - $${item.secondSalary}"
                    } else if (item.firstSalary != -1) {
                        tvSalary.text = "$${item.firstSalary}"
                    } else if (item.secondSalary != -1) {
                        tvSalary.text = "$${item.secondSalary}"
                    }

                    tvPayments.text = item.paymentMethod
                } else {
                    llSalary.visibility = View.GONE
                }
            }
//            Glide.with(context)
//                .load("https://image.tmdb.org/t/p/w500/${item.poster_path}")
//                .centerCrop()
//                .into(binding.imageViewMovie)
        }
    }

}