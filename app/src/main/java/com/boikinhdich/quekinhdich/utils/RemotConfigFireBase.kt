package com.boikinhdich.quekinhdich.utils

import android.util.Log
import com.google.firebase.remoteconfig.FirebaseRemoteConfig


fun versionAppFirebase(): Int {
    var s = FirebaseRemoteConfig.getInstance().getString("app_version_key").trim()
    Log.e("versionAppFirebase", "$s ")

    if (s.isEmpty())
        s = "0"
    return s.toInt()
}


fun countSelectAdsFullScreen(): Int {
    var s = FirebaseRemoteConfig.getInstance().getString("count_select_ads_key").trim()

    if (s.isEmpty())
        s = "6"
    return s.toInt()
}


fun isCollapsibleBannerAd(): Boolean {
    val s = FirebaseRemoteConfig.getInstance().getString("active_collapsible_banner_key").trim()
//    Log.e("isCollapsibleBannerAd", "$s")
    return s == "true"
//    return true
}

fun isActiveInterstitialAd(): Boolean {
    val s = FirebaseRemoteConfig.getInstance().getString("active_interstitial_ad_key").trim()
//    Log.e("isCollapsibleBannerAd", "$s")
    return s == "true"
//    return false
}

fun isActiveGameAnimal(): Boolean {
    val s = FirebaseRemoteConfig.getInstance().getString("active_game_animal_key").trim()
//    Log.e("isCollapsibleBannerAd", "$s")
    return s == "true"
//    return false
}

fun isActiveRateAppPlayStore(): Boolean {
    val s = FirebaseRemoteConfig.getInstance().getString("active_rate_app_play_store_key").trim()
//    Log.e("isCollapsibleBannerAd", "$s")
    return s == "true"
//    return false
}

