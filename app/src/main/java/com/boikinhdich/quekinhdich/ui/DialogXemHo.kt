package com.boikinhdich.quekinhdich.ui

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.boikinhdich.quekinhdich.R
import com.boikinhdich.quekinhdich.databinding.DialogXemHoBinding
import java.util.Random

class DialogXemHo : Dialog {

    private var listener: DialogListener? = null

    constructor(context: Context) : super(context) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        val binding = DialogXemHoBinding.inflate(LayoutInflater.from(context))
        setContentView(binding.root)

        binding.apply {

            val spinnerData = (1..64).map { it.toString() }
            val adapter =
                ArrayAdapter(context, R.layout.style_text_spinner, spinnerData)
            adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)

            spinner.adapter = adapter

            var idQue = 0

            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long,
                ) {
                    val selectedItem = spinnerData[position]
                    // Xử lý khi một mục được chọn
                    idQue = selectedItem.toInt()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // Xử lý khi không có mục nào được chọn
                }
            }

            btnSelectIdQue.setOnClickListener {
                listener?.onNumber(idQue)
                dismiss()
            }

            btnClose.setOnClickListener {
                dismiss()
            }

            btnRandomQue.setOnClickListener {
                val random = Random()
                val randomNumber = random.nextInt(64) + 1
                listener?.onNumber(randomNumber)
                dismiss()
            }
        }


        val w: Int = ViewGroup.LayoutParams.WRAP_CONTENT
        val h: Int = ViewGroup.LayoutParams.WRAP_CONTENT
        window!!.setLayout(w, h)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        show()
    }

    fun setListener(listener: DialogListener) {
        this.listener = listener
    }

    interface DialogListener {
        fun onNumber(idQue: Int)
    }
}