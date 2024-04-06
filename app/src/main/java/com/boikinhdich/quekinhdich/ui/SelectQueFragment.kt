package com.boikinhdich.quekinhdich.ui

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Display
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.animation.AnimationUtils
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.amuse.animalsounds.utils.ext.ChooseLanguageListener
import com.amuse.animalsounds.utils.ext.diaLogSetting
import com.boikinhdich.quekinhdich.R
import com.boikinhdich.quekinhdich.ui.adapter.CardAdapter
import com.boikinhdich.quekinhdich.adapter.CardModel
import com.boikinhdich.quekinhdich.data.model.fromJsonArray
import com.boikinhdich.quekinhdich.databinding.DialogXemHoBinding
import com.boikinhdich.quekinhdich.databinding.FragmentSelectQueBinding
import com.boikinhdich.quekinhdich.ui.main.FragmentListener
import com.boikinhdich.quekinhdich.ui.main.MainActivity
import com.google.android.play.core.review.ReviewManager
import com.google.android.play.core.review.testing.FakeReviewManager
import java.util.Random
import kotlin.math.roundToInt

class SelectQueFragment : Fragment() {

    private var _binding: FragmentSelectQueBinding? = null
    private val binding get() = _binding!!

    var listener: FragmentListener? = null
    private var reviewManager: ReviewManager? = null

    private val cardAdapter by lazy { CardAdapter() }
    private var isShowAnimatonFisrt = true

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentListener)
            listener = context as FragmentListener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSelectQueBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        reviewManager = FakeReviewManager(context)

        binding.apply {
            // Khởi tạo RecyclerView và adapter

            cardRecylerview.apply {
                layoutManager = GridLayoutManager(context, 8)
                adapter = cardAdapter

                if (isShowAnimatonFisrt)
                    animationRecyclview()
                else
                    layoutAnimation = null
                isShowAnimatonFisrt = false


            }

            val json =
                requireContext().assets.open("data.json").bufferedReader().use { it.readText() }
            val items = fromJsonArray(json, CardModel::class.java)

            cardAdapter.apply {
                setItems(items)
                setOnItemClickListener(object : CardAdapter.OnItemClickListener {
                    override fun onItemClick(position: Int) {
                        setItemDetailQue(items[position])
                    }
                })
            }

            btnPalmOff.setOnClickListener {
                items.shuffle()
                cardAdapter.notifyDataSetChanged()
                cardRecylerview.scheduleLayoutAnimation()
            }

            btnInstruct.setOnClickListener {
                listener?.onTutorialQueFragment()
            }

            btnSetting.setOnClickListener {
                requireActivity().diaLogSetting(reviewManager!!, object : ChooseLanguageListener {
                    override fun onTemps() {
                        listener?.onTermsFragment()
                    }
                })
            }

            btnXemHo.setOnClickListener {

                var dialogXemHo = Dialog(requireContext())
                dialogXemHo.requestWindowFeature(Window.FEATURE_NO_TITLE)
                val binding = DialogXemHoBinding.inflate(layoutInflater)
                dialogXemHo.setContentView(binding.root)

                binding.apply {

                    val spinnerData = (1..64).map { it.toString() }
                    val adapter =
                        ArrayAdapter(requireContext(), R.layout.style_text_spinner, spinnerData)
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
                                setItemDetailQue(item)
                        }
                        dialogXemHo.dismiss()
                    }

                    btnClose.setOnClickListener {
                        dialogXemHo.dismiss()
                    }

                    btnRandomQue.setOnClickListener {
                        val random = Random()
                        val randomNumber = random.nextInt(64) + 1

                        for (item in items) {
                            if (item.id == randomNumber)
                                setItemDetailQue(item)
                        }
                        dialogXemHo.dismiss()
                    }
                }

                val w: Int = if (getScreenSizeInches(requireActivity()) > 7)
                    (resources.displayMetrics.widthPixels * 0.6).roundToInt()
                else (resources.displayMetrics.widthPixels * 0.8).roundToInt()
                val h: Int = ViewGroup.LayoutParams.WRAP_CONTENT
                dialogXemHo.window!!.setLayout(w, h)
                dialogXemHo.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialogXemHo.show()
            }

        }
    }

    private fun setItemDetailQue(item: CardModel) {
        listener?.onDetailQueFragment(item)
    }

    private fun getScreenSizeInches(activity: Activity): Double {
        val windowManager = activity.windowManager
        val display = windowManager.defaultDisplay
        val displayMetrics = DisplayMetrics()
        display.getMetrics(displayMetrics)

        // since SDK_INT = 1;
        var mWidthPixels = displayMetrics.widthPixels
        var mHeightPixels = displayMetrics.heightPixels

        // includes window decorations (statusbar bar/menu bar)
        if (Build.VERSION.SDK_INT >= 14 && Build.VERSION.SDK_INT < 17) {
            try {
                mWidthPixels =
                    Display::class.java.getMethod("getRawWidth").invoke(display) as Int
                mHeightPixels =
                    Display::class.java.getMethod("getRawHeight")
                        .invoke(display) as Int
            } catch (ignored: Exception) {
            }
        }

        // includes window decorations (statusbar bar/menu bar)
        if (Build.VERSION.SDK_INT >= 17) {
            try {
                val realSize = Point()
                Display::class.java.getMethod(
                    "getRealSize",
                    Point::class.java
                ).invoke(display, realSize)
                mWidthPixels = realSize.x
                mHeightPixels = realSize.y
            } catch (ignored: Exception) {
            }
        }
        val dm = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(dm)
        val x = Math.pow(mWidthPixels / dm.xdpi.toDouble(), 2.0)
        val y = Math.pow(mHeightPixels / dm.ydpi.toDouble(), 2.0)
        return Math.sqrt(x + y)
    }

    private fun animationRecyclview() {
        val anim = AnimationUtils.loadLayoutAnimation(
            requireContext(),
            R.animator.layout_animation_from_bottom_scale
        )
        binding!!.cardRecylerview.layoutAnimation = anim
    }


}