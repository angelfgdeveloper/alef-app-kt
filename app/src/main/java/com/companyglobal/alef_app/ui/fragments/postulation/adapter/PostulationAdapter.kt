package com.companyglobal.alef_app.ui.fragments.postulation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.companyglobal.alef_app.core.BaseViewHolder
import com.companyglobal.alef_app.data.model.Postulation
import com.companyglobal.alef_app.databinding.ItemPostulationBinding

class PostulationAdapter(
    private var postulationList: List<Postulation>,
    private val itemClickListener: OnPostulationClickListener
) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    interface OnPostulationClickListener {
        fun onPostulationClick(postulation: Postulation)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding = ItemPostulationBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        val holder = PostulationViewHolder(itemBinding, parent.context)

        itemBinding.root.setOnClickListener {
            val position =
                holder.bindingAdapterPosition.takeIf { it != DiffUtil.DiffResult.NO_POSITION }
                    ?: return@setOnClickListener
            itemClickListener.onPostulationClick(postulationList[position])
        }

        return holder
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is PostulationViewHolder -> holder.bind(postulationList[position])
        }
    }

    override fun getItemCount(): Int = postulationList.size

    private inner class PostulationViewHolder(
        val binding: ItemPostulationBinding,
        val context: Context
    ) : BaseViewHolder<Postulation>(binding.root) {
        override fun bind(item: Postulation) {
            with(binding) {
                setStepView(item)

                tvTitleVacant.text = item.title
                tvDescription.text = item.description

                if (item.stepAdvance == 3 || item.stepAdvance == 4) {
                    btnCancelPostulation.isEnabled = false
                } else {
                    btnCancelPostulation.isEnabled = true
                    btnCancelPostulation.setOnClickListener {
                        Toast.makeText(context, "No puedes cancelar hahaha", Toast.LENGTH_SHORT).show()
                    }
                }

            }
        }

        private fun setStepView(postulation: Postulation) {
            binding.stepView.state
                .steps(object : ArrayList<String?>() {
                    init {
                        add("Aplicado")
                        add("C.V. visto")
                        add("En proceso")
                        add("Finalizado")
                    }
                })
                .stepsNumber(4)
                .commit()

            binding.stepView.go(postulation.stepAdvance, true)
        }

    }

}