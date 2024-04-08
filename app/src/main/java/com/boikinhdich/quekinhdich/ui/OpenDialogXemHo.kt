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
import com.boikinhdich.quekinhdich.adapter.CardModel
import com.boikinhdich.quekinhdich.databinding.DialogXemHoBinding
import com.boikinhdich.quekinhdich.ui.main.FragmentListener
import kotlin.random.Random

class OpenDialogXemHo(private val items: ArrayList<CardModel>, private val context: Context) {
    private var dialogXemHo: Dialog? = null
    private lateinit var binding: DialogXemHoBinding

    private var dialogListener: DialogListener? = null

    fun setDialogListener(listener: DialogListener) {
        dialogListener = listener
    }


    fun showDialog() {
        dialogXemHo = Dialog(context)
        dialogXemHo?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = DialogXemHoBinding.inflate(LayoutInflater.from(context))
        dialogXemHo?.setContentView(binding.root)

        binding.apply {
            val spinnerData = (1..64).map { it.toString() }
            val adapter = ArrayAdapter(context, R.layout.style_text_spinner, spinnerData)
            adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)

            spinner.adapter = adapter

            var idQue = 0

            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
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
                for (item in items) {
                    if (item.id == idQue)
                        dialogListener?.onItemSelected(item)
                }
                dialogXemHo?.dismiss()
            }

            btnClose.setOnClickListener {
                dialogXemHo?.dismiss()
            }

            btnRandomQue.setOnClickListener {
                val random = Random
                val randomNumber = random.nextInt(64) + 1

                for (item in items) {
                    if (item.id == randomNumber)
                        dialogListener?.onItemSelected(item)
                }
                dialogXemHo?.dismiss()
            }
        }

        val w: Int = ViewGroup.LayoutParams.WRAP_CONTENT
        val h: Int = ViewGroup.LayoutParams.WRAP_CONTENT
        dialogXemHo?.window?.setLayout(w, h)
        dialogXemHo?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogXemHo?.show()
    }

    interface DialogListener {
        fun onItemSelected(item: CardModel)
    }
}