package com.boikinhdich.quekinhdich.utils

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import com.google.firebase.analytics.FirebaseAnalytics

//todo: New event 2/2/2021
fun firebaseAnalyticsScreen(context: Context, screenName: String) {
    Log.e("AnalyticsScreen","$screenName")
    val firebaseAnalytics = FirebaseAnalytics.getInstance(context)
    val bundle = Bundle()
    bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, screenName)
    bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, screenName)
    firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)
}

@SuppressLint("HardwareIds")
fun firebaseAnalyticsEventNoParam(context: Context, eventName: String) {
    val string = eventName.replace(" ", "")
    val firebaseAnalytics = FirebaseAnalytics.getInstance(context)
    val bundle = Bundle()
    Log.d("AnalyticsEventNoParam", string)
    firebaseAnalytics.logEvent(string, bundle)
}

@SuppressLint("HardwareIds")
fun firebaseAnalyticsEvent(context: Context, eventName: String, bundle: Bundle) {
    val firebaseAnalytics = FirebaseAnalytics.getInstance(context)
//            val bundle = Bundle()
    Log.d("initAnalyticsEvent", "$eventName $bundle")
    firebaseAnalytics.logEvent(eventName, bundle)
}