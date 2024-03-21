//package com.amuse.animalsounds.utils.ext
//
//import android.annotation.SuppressLint
//import android.content.Context
//import android.content.res.Configuration
//import android.content.res.Resources
//import android.text.*
//import android.text.method.LinkMovementMethod
//import android.text.style.ClickableSpan
//import android.text.style.ForegroundColorSpan
//import android.util.Base64
//import android.util.Log
//import android.util.Patterns
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import androidx.core.content.ContextCompat
//import androidx.core.text.HtmlCompat
//import androidx.transition.TransitionManager
//import com.amuse.animalsounds.MainApp
//import com.amuse.animalsounds.R
//import com.amuse.animalsounds.data.FileManager
//import com.amuse.animalsounds.utils.sharePreferences.SPKeyEnum
//import com.amuse.animalsounds.utils.sharePreferences.SharePreferencesManager
//import java.io.File
//import java.io.FileWriter
//import java.net.URLEncoder
//import java.nio.charset.Charset
//import java.text.NumberFormat
//import java.text.SimpleDateFormat
//import java.util.*
//import java.util.regex.Pattern
//
//
//fun Context.getStringResourceByName(aString: String, lan: String = ""): String {
//    val resources = getResourcesByLocale(this, lan)
//    val resId: Int = getIdByNameRes(aString, "string")
//    return resources.getString(resId)
//}
//
//fun getResourcesByLocale(context: Context, lan: String = ""): Resources {
//    val res = context.resources
//    val conf = Configuration(res.configuration)
//    val language = SharePreferencesManager.getInstance()
//        .getValue(SPKeyEnum.LANGUAGE, getSystemLocale().ifEmpty { "en" })
//    if (lan.isNotEmpty())
//        conf.locale = Locale(lan)
//    else
//        conf.locale = Locale(language)
//    return Resources(res.assets, res.displayMetrics, conf)
//}
//
//
//var checkText = true
//
//fun Context.setTextShowHide(view: ViewGroup, tvConten: TextView, lines: Int, item: String) {
//
//    try {
//        var lineCount = 0
//
//        tvConten.post {
//            lineCount = tvConten.lineCount
//            if (lineCount > lines) {
//                val lastCharShown: Int = tvConten.layout.getLineVisibleEnd(lines - 1)
//                tvConten.setLines(lines)
//                val moreString = " More >>"
//                val suffix = "  $moreString"
//                val actionDisplayText =
//                    HtmlCompat.fromHtml(
//                        item.substring(
//                            0,
//                            lastCharShown - suffix.length - 20
//                        ) + "..." + suffix, HtmlCompat.FROM_HTML_MODE_COMPACT
//                    )
//
//                val truncatedSpannableString = SpannableString(actionDisplayText)
//                val startIndex = actionDisplayText.indexOf(moreString)
//                // setOnClick moreString
//                truncatedSpannableString.setSpan(object : ClickableSpan() {
//                    override fun onClick(textView: View) {
//                        textView.invalidate()
//                    }
//
//                    override fun updateDrawState(ds: TextPaint) {
//                        super.updateDrawState(ds)
//                        ds.isUnderlineText = false
//                        tvConten.invalidate()
//                    }
//                }, startIndex, startIndex + moreString.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
//
//                //set color moreString
//                truncatedSpannableString.setSpan(
//                    ForegroundColorSpan(ContextCompat.getColor(this, R.color.black)),
//                    startIndex,
//                    startIndex + moreString.length,
//                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
//                )
//
//                // set background moreString onClick
//                tvConten.highlightColor = ContextCompat.getColor(this, R.color.gray_text_color)
//                tvConten.setText(truncatedSpannableString, TextView.BufferType.SPANNABLE)
//                tvConten.movementMethod = LinkMovementMethod.getInstance()
//            } else {
//                tvConten.setLines(lineCount)
//            }
//            //        if (tvConten.getLineCount() > 3) {
//            //            textView6.setLines(3)
//            //        } else textView6.setLines(tvConten.getLineCount())
//
//            tvConten.setOnClickListener { v: View? ->
//                if (checkText) {
//                    tvConten.text = HtmlCompat.fromHtml(item, HtmlCompat.FROM_HTML_MODE_COMPACT)
//                    tvConten.post {
//                        tvConten.setLines(tvConten.lineCount)
//                        TransitionManager.beginDelayedTransition(view)
//                    }
//                } else {
//                    setTextShowHide(
//                        view,
//                        tvConten,
//                        lines,
//                        item
//                    )
//                    TransitionManager.beginDelayedTransition(view)
//                }
//                checkText = !checkText
//            }
//
//
//        }
//    } catch (e: Exception) {
//        tvConten.text = HtmlCompat.fromHtml(item, HtmlCompat.FROM_HTML_MODE_COMPACT)
//        e.printStackTrace()
//    }
//    tvConten.visibility = View.VISIBLE
//}
//
//
//fun Context.saveFileJsonFeedback(): File? {
//    var gpxfile: File? = null
//    val file = File(cacheDir, "text")
//    if (!file.exists())
//        file.mkdir()
//    try {
//        gpxfile = File(file, "${Calendar.getInstance().timeInMillis}")
//        val writer = FileWriter(gpxfile)
//        writer.append("content")
//        writer.flush()
//        writer.close()
//    } catch (e: java.lang.Exception) {
//        e.printStackTrace()
//    }
//    return gpxfile
//}
//
//fun getCurrentDate(): String {
//    val currentDate = Date()
//    val dateFormat = SimpleDateFormat("HH:mm dd-MM-yyyy", Locale.getDefault())
//    return dateFormat.format(currentDate)
//}