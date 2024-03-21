//package com.amuse.animalsounds.utils.ext
//
//import android.app.Activity
//import android.app.Dialog
//import android.content.Context
//import android.graphics.Color
//import android.graphics.drawable.ColorDrawable
//import android.os.SystemClock
//import android.view.View
//import android.view.ViewGroup
//import android.view.Window
//import android.widget.EditText
//import android.widget.FrameLayout
//import android.widget.ImageView
//import android.widget.LinearLayout
//import android.widget.TextView
//import androidx.appcompat.app.AppCompatActivity
//import androidx.constraintlayout.widget.ConstraintLayout
//import androidx.core.widget.addTextChangedListener
//import androidx.fragment.app.Fragment
//import androidx.fragment.app.FragmentManager
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.boikinhdich.quekinhdich.R
//import java.util.*
//import kotlin.math.roundToInt
//
//
//fun Activity.diaLogSetting(reviewManager: ReviewManager, listener: ChooseLanguageListener) {
//    try {
//        val dialog = Dialog(this)
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        val binding = ViewSettingAppBinding.inflate(layoutInflater)
//        dialog.setContentView(binding.root)
//
//        binding.apply {
//
//            btnPrivacyPolicy.setOnClickListener {
//                listener.onTemps()
//                dialog.dismiss()
//            }
//            btnRateUs.setOnClickListener {
//                dialog.dismiss()
//                onDialogRating(reviewManager)
//            }
//            btnCancel.setOnClickListener {
//                dialog.dismiss()
//            }
//            btnChangeLanguage.setOnClickListener {
//                dialog.dismiss()
//                onChooseLanguage(true, listener)
//            }
//
//            btnMoreApps.setOnClickListener {
//                dialog.dismiss()
//            }
//
//            btnShare.setOnClickListener {
//                dialog.dismiss()
//                onShareApp()
//            }
//            val width = (resources.displayMetrics.widthPixels * 0.9).toInt()
//            val height = ViewGroup.LayoutParams.MATCH_PARENT
//            dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
//            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//            dialog.window!!.setLayout(width, height)
//            dialog.show()
//
//            viewAdNative.root.visibility = View.GONE
////        if (versionAppFirebase() >= BuildConfig.VERSION_CODE)
//            AdmobManager.getInstance().apply {
//                createNativeAd { adNative ->
//                    try {
//                        populateNativeAdView(adNative, viewAdNative)
//                        viewAdNative.root.visibility = View.VISIBLE
//                    } catch (e: Exception) {
//                        e.printStackTrace()
//                    }
//                }
//            }
//        }
//    } catch (e: Exception) {
//        e.printStackTrace()
//    }
//
//}
//
///**
// * Show dialog notice
// */
//
//enum class DialogNoticeEnum {
//    NO_BTN
//}
//
//fun Context.showAlertDialogNotification(
//    string: String,
//    code: Int,
//    dialogNotice: DialogNoticeEnum,
//    dialogNoticeListen: DialogNoticeListen?,
//) {
//
//    val dialog = Dialog(this)
//    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
//    dialog.setContentView(R.layout.view_notice)
//    var eventDismiss = DialogNoticeEnum.NO_BTN
//
//
//    val tvContent = dialog.findViewById<android.widget.TextView>(R.id.tvContent)
//    val btnClose = dialog.findViewById<ImageView>(R.id.btnClose)
//
//
//
//    if (string.isNotEmpty())
//        tvContent.text = string
//    btnClose.setOnClickListener {
//        eventDismiss = DialogNoticeEnum.NO_BTN
//        dialog.dismiss()
//    }
//
//
////    val window = dialog.window
////    if (window != null) {
////        val layoutParams = window.attributes
//////        layoutParams.windowAnimations = R.style.DialogAnimationDuration300
////    }
//
//    val width = (resources.displayMetrics.widthPixels * 0.9).toInt()
//    val height = ViewGroup.LayoutParams.WRAP_CONTENT
//    dialog.window!!.setLayout(width, height)
//    dialog.show()
//}
//
//interface DialogNoticeListen {
//    fun onDismiss(event: DialogNoticeEnum)
//}
//
//
///**
// * Dialog loading
// */
//var dialogLoading: NSDialogLoading? = null
//var timerLoading = 0L
//var timerDelayLoading: Timer? = null
//
//fun Context.onDialogLoading(isShadown: Boolean) {
//
//    if (dialogLoading == null) {
//        timerDelayLoading?.let { it.cancel() }
//        timerLoading = SystemClock.currentThreadTimeMillis()
//
//        dialogLoading = NSDialogLoading.switcher(isShadown)
//        dialogLoading!!.isCancelable = false
//        var manager: FragmentManager? = null
//
//        if (this is AppCompatActivity)
//            manager = this.supportFragmentManager
//        else
//            manager = (this as Fragment).childFragmentManager
//
//        dialogLoading!!.show(manager, "NSDialogLoading")
//    }
//}
//
//fun Context.onDismissDialogLoading() {
//    try {
//        if (dialogLoading != null)
//            dialogLoading!!.dismiss()
//        dialogLoading = null
//    } catch (e: Exception) {
//        e.printStackTrace()
//    }
//}
//
//fun Activity.onDialogRating(reviewManager: ReviewManager) {
//    try {
//        var dialogLanguage = Dialog(this)
//        dialogLanguage.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        dialogLanguage.setContentView(R.layout.view_rate_app)
//        val ratingBar = dialogLanguage.findViewById<RatingBar>(R.id.simpleRatingBar)
//        val edtFeedback = dialogLanguage.findViewById<EditText>(R.id.edtFeedback)
//        val btnLater = dialogLanguage.findViewById<TextView>(R.id.btnLater)
//        val viewFeedBack = dialogLanguage.findViewById<LinearLayout>(R.id.viewFeedBack)
//        val btnSubmit = dialogLanguage.findViewById<TextView>(R.id.btnSubmit)
//
//        viewFeedBack.visibility = View.GONE
//        btnLater.setOnClickListener {
//            dialogLanguage.dismiss()
//        }
//
//        btnSubmit.isEnabled = false
//        btnSubmit.alpha = 0.5f
//        edtFeedback.addTextChangedListener {
//            if (it!!.isEmpty()) {
//                btnSubmit.isEnabled = false
//                btnSubmit.alpha = 0.3f
//            } else {
//                btnSubmit.isEnabled = true
//                btnSubmit.alpha = 1f
//            }
//        }
//        ratingBar.setStar(5f)
//        var rating = 0F
//        ratingBar.setOnRatingChangeListener {
//            SharePreferencesManager.getInstance().setValue(SPKeyEnum.RATE_APP, true)
//            rating = it
//            if (rating >= 4) {
//                onRateApp(reviewManager)
//                dialogLanguage.dismiss()
//            } else {
//                viewFeedBack.visibility = View.VISIBLE
//            }
//        }
//        btnSubmit.setOnClickListener {
//            if (rating >= 4) {
//                onRateApp(reviewManager)
//            } else {
//                FileManager.getInstance().uploadFeedBack(
//                    this@onDialogRating,
//                    FeedBackModel(
//                        rating.toInt(),
//                        edtFeedback.text.toString(),
//                        getCurrentDate()
//                    )
//                )
//            }
//            dialogLanguage.dismiss()
//        }
//
//        val w: Int = if (getScreenSizeInches(this) > 7)
//            (resources.displayMetrics.widthPixels * 0.6).roundToInt()
//        else (resources.displayMetrics.widthPixels * 0.8).roundToInt()
//        val h: Int = ViewGroup.LayoutParams.WRAP_CONTENT
//        dialogLanguage.window!!.setLayout(w, h)
//        dialogLanguage.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        dialogLanguage.show()
//    } catch (e: java.lang.Exception) {
//        e.printStackTrace()
//    }
//}
//
//fun Activity.onNotificationErrorInternet() {
//    try {
//        val dialogLanguage = Dialog(this)
//        dialogLanguage.apply {
//            requestWindowFeature(Window.FEATURE_NO_TITLE)
//            setContentView(R.layout.view_error_internet)
//
//            findViewById<TextView>(R.id.btnCancel).setOnClickListener {
//                dismiss()
//            }
//
//            val w: Int
//            if (getScreenSizeInches(this@onNotificationErrorInternet) > 7)
//                w = (resources.displayMetrics.widthPixels * 0.6).roundToInt()
//            else w = (resources.displayMetrics.widthPixels * 0.8).roundToInt()
//            val h: Int = ViewGroup.LayoutParams.WRAP_CONTENT
//            window!!.setLayout(w, h)
//            window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//            show()
//        }
//
//    } catch (e: java.lang.Exception) {
//        e.printStackTrace()
//    }
//}
//
//fun Activity.onChooseLevelGame(onClick: (LevelGame) -> Unit) {
//    try {
//        val dialogLanguage = Dialog(this)
//        dialogLanguage.apply {
//            requestWindowFeature(Window.FEATURE_NO_TITLE)
//            setContentView(R.layout.view_choose_level_game)
//
//            val btnEasy = findViewById<ConstraintLayout>(R.id.btnEasy)
//            val btnMedium = findViewById<ConstraintLayout>(R.id.btnMedium)
//            val btnHard = findViewById<ConstraintLayout>(R.id.btnHard)
//
//            btnEasy.setOnClickListener {
//                onClick.invoke(LevelGame.EASY)
//                dismiss()
//            }
//            btnMedium.setOnClickListener {
//                onClick.invoke(LevelGame.MEDIUM)
//                dismiss()
//            }
//            btnHard.setOnClickListener {
//                onClick.invoke(LevelGame.HARD)
//                dismiss()
//            }
//
//            val w: Int = if (getScreenSizeInches(this@onChooseLevelGame) > 7)
//                (resources.displayMetrics.widthPixels * 0.6).roundToInt()
//            else (resources.displayMetrics.widthPixels * 0.8).roundToInt()
//            val h: Int = ViewGroup.LayoutParams.WRAP_CONTENT
//            window!!.setLayout(w, h)
//            window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//            show()
//        }
//
//    } catch (e: java.lang.Exception) {
//        e.printStackTrace()
//    }
//}
//
//
//fun Activity.onChooseLanguage(isCancelable: Boolean, listener: ChooseLanguageListener) {
//    try {
//        val dialogLanguage = Dialog(this)
////        dialogLanguage.setCancelable(false)
//        dialogLanguage.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        dialogLanguage.setContentView(R.layout.view_choose_language)
//        val rvList =
//            dialogLanguage.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.rvList)
//
//        dialogLanguage.findViewById<ImageView>(R.id.btnClose).setOnClickListener {
//            dialogLanguage.dismiss()
//        }
//
//        val adapter = LanguageAdapter(this, object : LanguageAdapter.LanguageListener {
//            override fun onClick(pos: Int) {
//                dialogLanguage.dismiss()
//                listener.onChangeLanguage()
//            }
//        })
//        rvList.layoutManager = LinearLayoutManager(this)
//        rvList.adapter = adapter
//
//
//        val w: Int
//        if (getScreenSizeInches(this) > 7)
//            w = (resources.displayMetrics.widthPixels * 0.6).roundToInt()
//        else w = (resources.displayMetrics.widthPixels * 0.8).roundToInt()
//        val h: Int = ViewGroup.LayoutParams.WRAP_CONTENT
//        dialogLanguage.window!!.setLayout(w, h)
//        dialogLanguage.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        dialogLanguage.show()
//    } catch (e: java.lang.Exception) {
//        e.printStackTrace()
//    }
//}
//
//interface ChooseLanguageListener {
//    fun onChangeLanguage()
//    fun onTemps()
//}
//
//
//
//
//
