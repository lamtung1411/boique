package com.amuse.animalsounds.utils.ext

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.view.View
import android.view.ViewGroup
import android.view.Window
import com.amuse.animalsounds.utils.admod.AdmobManager
import com.amuse.animalsounds.utils.sharePreferences.SPKeyEnum
import com.boikinhdich.quekinhdich.utils.sharePreferences.SharePreferencesManager
import com.boikinhdich.quekinhdich.databinding.ViewRateAppBinding
import com.boikinhdich.quekinhdich.databinding.ViewSettingAppBinding
import com.boikinhdich.quekinhdich.utils.ext.getScreenSizeInches
import com.boikinhdich.quekinhdich.utils.ext.onRateApp
import com.boikinhdich.quekinhdich.utils.ext.onShareApp
import com.google.android.play.core.review.ReviewManager
import kotlin.math.roundToInt


fun Activity.diaLogSetting(reviewManager: ReviewManager) {
    try {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val binding = ViewSettingAppBinding.inflate(layoutInflater)
        dialog.setContentView(binding.root)

        binding.apply {

            btnPrivacyPolicy.setOnClickListener {
//                listener.onTemps()
                val url = "https://sites.google.com/view/animal-sound24/trang-ch%E1%BB%A7"
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(intent)
                dialog.dismiss()
            }
            btnRateUs.setOnClickListener {
                dialog.dismiss()
                onDialogRating(reviewManager)
            }
            btnCancel.setOnClickListener {
                dialog.dismiss()
            }
//            btnChangeLanguage.setOnClickListener {
//                dialog.dismiss()
//                onChooseLanguage(true, listener)
//            }

            btnMoreApps.setOnClickListener {
                val url = "https://play.google.com/store/apps/developer?id=Pham+Van+Dai&hl=vi-VN"
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(intent)
                dialog.dismiss()
            }

            btnShare.setOnClickListener {
                dialog.dismiss()
                onShareApp()
            }
            val width = (resources.displayMetrics.widthPixels * 0.9).toInt()
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.window!!.setLayout(width, height)
            dialog.show()

            viewAdNative.root.visibility = View.GONE
//        if (versionAppFirebase() >= BuildConfig.VERSION_CODE)
            AdmobManager.getInstance().apply {
                createNativeAd { adNative ->
                    try {
                        populateNativeAdView(adNative, viewAdNative)
                        viewAdNative.root.visibility = View.VISIBLE
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }

}

/**
 * Show dialog notice
 */

enum class DialogNoticeEnum {
    NO_BTN
}

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

interface DialogNoticeListen {
    fun onDismiss(event: DialogNoticeEnum)
}


/**
 * Dialog loading
 */
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

//fun Context.onDismissDialogLoading() {
//    try {
//        if (dialogLoading != null)
//            dialogLoading!!.dismiss()
//        dialogLoading = null
//    } catch (e: Exception) {
//        e.printStackTrace()
//    }
//}

fun Activity.onDialogRating(reviewManager: ReviewManager) {
    try {
        var dialogRating = Dialog(this)
        dialogRating.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val binding = ViewRateAppBinding.inflate(layoutInflater)
        dialogRating.setContentView(binding.root)

        binding.apply {

            viewFeedBack.visibility = View.GONE
            btnLater.setOnClickListener {
                dialogRating.dismiss()
            }

            btnSubmit.isEnabled = false
            btnSubmit.alpha = 0.5f

//        edtFeedback.addTextChangedListener {
//            if (it!!.isEmpty()) {
//                btnSubmit.isEnabled = false
//                btnSubmit.alpha = 0.3f
//            } else {
//                btnSubmit.isEnabled = true
//                btnSubmit.alpha = 1f
//            }
//        }

            var rating = 0F
            ratingBar.rating = 5f
            ratingBar.setOnRatingBarChangeListener { ratingBar, fl, b ->
//                SharePreferencesManager.getInstance().setValue(SPKeyEnum.RATE_APP, true)
                rating = ratingBar.rating
                if (rating >= 4) {
                    onRateApp(reviewManager)
                    dialogRating.dismiss()
                } else {
                    viewFeedBack.visibility = View.VISIBLE
                }
            }

            btnSubmit.setOnClickListener {
                if (rating >= 4) {
                    onRateApp(reviewManager)
                } else {
//                FileManager.getInstance().uploadFeedBack(
//                    this@onDialogRating,
//                    FeedBackModel(
//                        rating.toInt(),
//                        edtFeedback.text.toString(),
//                        getCurrentDate()
//                    )
//                )
                }
                dialogRating.dismiss()
            }
        }

        val w: Int = if (getScreenSizeInches(this) > 7)
            (resources.displayMetrics.widthPixels * 0.6).roundToInt()
        else (resources.displayMetrics.widthPixels * 0.8).roundToInt()
        val h: Int = ViewGroup.LayoutParams.WRAP_CONTENT
        dialogRating.window!!.setLayout(w, h)
        dialogRating.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogRating.show()

    } catch (e: java.lang.Exception) {
        e.printStackTrace()
    }
}

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

interface ChooseLanguageListener {
    fun onTemps()
}





