package com.amuse.animalsounds.utils.ext

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Point
import android.net.Uri
import android.os.Build
import android.util.DisplayMetrics
import android.view.Display
import android.view.View
import android.view.WindowManager
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.boikinhdich.quekinhdich.R
import java.util.Locale


/**
 * Open app in play store
 */
fun Context.openAppInPlayStore() {
    val uri = Uri.parse("market://details?id=$packageName")
    val goToMarket = Intent(Intent.ACTION_VIEW, uri)
    // To count with Play market backstack, After pressing back button,
    // to taken back to our application, we need to add following flags to intent.
    goToMarket.addFlags(
        Intent.FLAG_ACTIVITY_NO_HISTORY or
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT or
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK
    )
    try {
        startActivity(goToMarket)
    } catch (e: ActivityNotFoundException) {
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("http://play.google.com/store/apps/details?id=$packageName")
            )
        )
    }
}

/**
 * Get default status bar height
 */
fun Context.getStatusBarHeight(): Int {
    var result = 0
    val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
    if (resourceId > 0) {
        result = resources.getDimensionPixelSize(resourceId)
    }
    return result
}

fun Activity.getScreenHeight(): Int {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val windowMetrics = windowManager.currentWindowMetrics
        val rect = windowMetrics.bounds
        rect.bottom
    } else {
        resources.displayMetrics.heightPixels
    }
}

data class ButtonSpecs(
    val text: String? = null,
    val handler: (() -> Unit)? = null,
)


fun Activity.makeStatusBarTransparent() {
    window.apply {
        clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        statusBarColor = Color.TRANSPARENT


        WindowCompat.setDecorFitsSystemWindows(this, true)
        WindowCompat.getInsetsController(this, this.decorView).apply {
            systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            hide(WindowInsetsCompat.Type.systemBars())
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            attributes.layoutInDisplayCutoutMode =
                WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
        }
    }
}

fun Context.setLanguage(str: String) {
    val local = Locale(str)
    val resources = this.resources
    val displayMetrics = resources.displayMetrics
    val configuration = resources.configuration
    configuration.locale = local
    resources.updateConfiguration(configuration, displayMetrics)
}

fun Context.setLanguage(local: Locale) {
    val resources = this.resources
    val displayMetrics = resources.displayMetrics
    val configuration = resources.configuration
    configuration.locale = local
    resources.updateConfiguration(configuration, displayMetrics)
}


fun Activity.onShareApp() {
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(
            Intent.EXTRA_TEXT,
            "${getString(R.string.app_name)} \n https://play.google.com/store/apps/details?id=${packageName}"
        )
        type = "text/plain"
    }

    val shareIntent = Intent.createChooser(sendIntent, null)
    startActivity(shareIntent)
}
//
//fun Activity.onRateApp(reviewManager: ReviewManager) {
//    Log.e("onRateApp", " ${isActiveRateAppPlayStore()}")
//    if (!isActiveRateAppPlayStore()) {
//        openApp(packageName)
//        return
//    }
//    reviewManager.requestReviewFlow()
//        .addOnCompleteListener { task ->
//            if (task.isSuccessful) {
//                Log.e("showRateApp", "${task.isSuccessful} ")
//                // We can get the ReviewInfo object
//                val reviewInfo: ReviewInfo = task.result
//                reviewManager.launchReviewFlow(this, reviewInfo)
//                    .addOnCompleteListener { task1 ->
//                        Log.e("addOnCompleteListener", "${task1.isSuccessful} ${task1.isComplete}")
//                        if (!task1.isSuccessful)
//                            openApp(packageName)
//                    }.addOnFailureListener {
//                        openApp(packageName)
//                    }.addOnCanceledListener {
//                        openApp(packageName)
//                    }
//
//            } else {
//                // There was some problem, continue regardless of the result.
//                // show native rate app dialog on error
//                openApp(packageName)
//            }
//        }.addOnFailureListener {
//            openApp(packageName)
//        }.addOnCanceledListener {
//            openApp(packageName)
//        }
//
//}

fun Activity.getScreenSizeInches(activity: Activity): Double {
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

fun Activity.openApp(appPackageName: String) {
    try {
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("market://details?id=$appPackageName")
            )
        )
    } catch (exception: ActivityNotFoundException) {
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
            )
        )
    }
}

//
//@SuppressLint("SimpleDateFormat")
//fun isStreakDay(): Boolean {
//    val c = Calendar.getInstance()
//    val strDate = SharePreferencesManager.getInstance().getValue(SPKeyEnum.STREAKDAY)
//
//    if (TextUtils.isEmpty(strDate)) {
//        SharePreferencesManager.getInstance()
//            .setValue(SPKeyEnum.STREAKDAY, getDateNow())
//        return false
//    } else {
//        val format = SimpleDateFormat(Defaults.DATE_FORMAT)
//        var dateStart: Date? = null
//        var dateNow: Date? = null
//        try {
//            dateStart = format.parse(strDate!!)
//            dateNow = format.parse(format.format(c.time))
//            val diff = dateNow.time - dateStart.time
//            val diffDays = diff / (24 * 60 * 60 * 1000)
//
//            if (diffDays.toInt() != 0) {
//                SharePreferencesManager.getInstance()
//                    .setValue(SPKeyEnum.STREAKDAY, getDateNow())
//                return false
//            } else return true
//
//        } catch (e: java.lang.Exception) {
//            e.printStackTrace()
//            return true
//        }
//    }
//}
//
//@SuppressLint("SimpleDateFormat")
//fun getDateNow(): String? {
//    val c = Calendar.getInstance()
//    val df = SimpleDateFormat(Defaults.DATE_FORMAT)
//    return df.format(c.time)
//}
//
//
//fun getSystemLocale(): String =
//    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
//        LocaleList.getDefault().get(0).language
//    else
//        Locale.getDefault().language

//fun tryCatch(onTry: (() -> Unit)) {
//    try {
//        onTry.invoke()
//    } catch (e: Exception) {
//        e.printStackTrace()
//        FirebaseCrashlytics.getInstance().recordException(e)
//    }
//}
//
//
//inline fun <reified T : Fragment> newInstance(vararg params: Pair<String, Any>): T =
//    T::class.java.newInstance().apply {
//        arguments = bundleOf(*params)
//    }