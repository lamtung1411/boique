package com.boikinhdich.quekinhdich.ui

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.Display
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import carbon.widget.ImageView
import com.amuse.animalsounds.utils.ext.ChooseLanguageListener
import com.amuse.animalsounds.utils.ext.diaLogSetting
import com.amuse.animalsounds.utils.ext.onDialogRating
import com.boikinhdich.quekinhdich.R
import com.boikinhdich.quekinhdich.adapter.CardAdapter
import com.boikinhdich.quekinhdich.adapter.CardModel
import com.boikinhdich.quekinhdich.adapter.model.fromJsonArray
import com.boikinhdich.quekinhdich.databinding.DialogXemHoBinding
import com.boikinhdich.quekinhdich.databinding.FragmentSelectQueBinding
import com.boikinhdich.quekinhdich.databinding.ViewRateAppBinding
import com.boikinhdich.quekinhdich.utils.ext.getScreenSizeInches
import com.boikinhdich.quekinhdich.utils.ext.onRateApp
import com.google.android.play.core.review.ReviewManager
import com.google.android.play.core.review.testing.FakeReviewManager
import java.util.Random
import kotlin.math.roundToInt

class SelectQueFragment : Fragment() {

    lateinit var mainActivity: MainActivity
    lateinit var detailQueFragment: DetailQueFragment
    private var reviewManager: ReviewManager? = null

    private val cardAdapter by lazy { CardAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSelectQueBinding.inflate(inflater, container, false)

        detailQueFragment = DetailQueFragment()
        mainActivity = activity as MainActivity
        reviewManager = FakeReviewManager(context)

        binding.apply {
            // Khởi tạo RecyclerView và adapter

            cardRecylerview.apply {
                layoutManager = GridLayoutManager(context, 8)
                setHasFixedSize(true)
                adapter = cardAdapter
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
                mainActivity.switchFragment(TutorialQueFragment(), "TutorialQueFragment", true)
            }

            btnSetting.setOnClickListener {
                mainActivity.diaLogSetting(reviewManager!!, object : ChooseLanguageListener {

                    override fun onTemps() {

                        mainActivity.switchFragment(
                            TermsFragment(),
                            "TermsFragment",
                            true
                        )
                    }
                })
            }

            btnXemHo.setOnClickListener {

                var dialogXemHo = Dialog(requireContext())
                dialogXemHo.requestWindowFeature(Window.FEATURE_NO_TITLE)
                val binding = DialogXemHoBinding.inflate(layoutInflater)
                dialogXemHo.setContentView(binding.root)

                binding.apply {

                    numberPicker.minValue = 1 // Đặt giá trị nhỏ nhất mà người dùng có thể chọn
                    numberPicker.maxValue = 64 // Đặt giá trị lớn nhất mà người dùng có thể chọn
                    numberPicker.value = 1 // Đặt giá trị ban đầu của NumberPicker

                    numberPicker.setOnValueChangedListener { picker, oldVal, newVal ->
                        // Xử lý sự kiện khi giá trị được chọn thay đổi
                        // newVal là giá trị mới được chọn
                    }

                    btnSelectIdQue.setOnClickListener {
//                        val idQue = edtIdQue.text.toString()
//                        for (item in items) {
//                            if (item.id == idQue.toInt())
//                                setItemDetailQue(item)
//                        }
//                        dialogXemHo.dismiss()
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

        return binding.root
    }

    private fun setItemDetailQue(item: CardModel) {
        val bundle = Bundle()
        bundle.putSerializable("item", item)

        detailQueFragment.arguments = bundle
        mainActivity.switchFragment(detailQueFragment, "DetailQueFragment", true)
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

}