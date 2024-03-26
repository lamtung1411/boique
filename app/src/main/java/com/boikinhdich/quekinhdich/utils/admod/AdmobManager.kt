package com.amuse.animalsounds.utils.admod

import android.annotation.SuppressLint
import com.google.android.gms.ads.interstitial.InterstitialAd
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import android.view.View
import android.view.WindowManager
import androidx.databinding.library.BuildConfig
import com.boikinhdich.quekinhdich.R
import com.boikinhdich.quekinhdich.databinding.AdUnifiedBinding
import com.boikinhdich.quekinhdich.utils.countSelectAdsFullScreen
import com.boikinhdich.quekinhdich.utils.isActiveInterstitialAd
import com.boikinhdich.quekinhdich.utils.isCollapsibleBannerAd
import com.google.ads.mediation.admob.AdMobAdapter
import com.google.android.gms.ads.*
import kotlin.jvm.Synchronized
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.ump.ConsentInformation
import com.google.android.ump.ConsentRequestParameters
import com.google.android.ump.UserMessagingPlatform
import java.util.*

class AdmobManager() {

    private var countSelect = 0
    private val TAG = "AdmobManager"
    var mInterstitialAd: InterstitialAd? = null
    var adBanner: AdView? = null
//    var adNativeView: NativeAdView? = null
    private var interstitialAdLoadCallback: InterstitialAdLoadCallback? = null
    val TAG_Banner = "Banner_ADS"
    private var adBannerListener: AdListener? = null
    lateinit var context: Activity


    private var countLoadError = 0
    private lateinit var consentInformation: ConsentInformation

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var sInstance: AdmobManager? = null

        @SuppressLint("StaticFieldLeak")

        @Synchronized
        fun initializeInstance() {
            if (sInstance == null)
                sInstance = AdmobManager()
        }

        @Synchronized
        fun getInstance(): AdmobManager {
            if (sInstance == null)
                sInstance = AdmobManager()
            return sInstance!!
        }
    }

    init {
    }


    fun initPopupAdmobGDPR(activity: Activity) {
        this.context = activity
//        val debugSettings = ConsentDebugSettings.Builder(activity)
//            .setDebugGeography(ConsentDebugSettings.DebugGeography.DEBUG_GEOGRAPHY_EEA)
//            .addTestDeviceHashedId("6C7B74CDBC39BFB891E7B7A64B16FF8D")
//            .build()

        val params = ConsentRequestParameters
            .Builder()
//            .setConsentDebugSettings(debugSettings)
            .build()

        consentInformation = UserMessagingPlatform.getConsentInformation(activity)
        consentInformation.requestConsentInfoUpdate(activity, params, {
            // The consent information state was updated.
            // You are now ready to check if a form is available.
            Log.e(
                "consentInformation",
                "${consentInformation.consentStatus} ${consentInformation.isConsentFormAvailable}"
            )
            if (consentInformation.canRequestAds())
                initAdsAdmob()
            else {
                if (consentInformation.isConsentFormAvailable)
                    loadForm(activity)
            }
        }, {
            // Handle the error.
            Log.e(TAG, "Error code: ${it.errorCode} message: ${it.message}")
        })
        if (consentInformation.canRequestAds())
            initAdsAdmob()
    }


    fun resetConsentInformation() {
        countLoadError = 0
        consentInformation.reset()
        initPopupAdmobGDPR(context)
    }

    private fun loadForm(activity: Activity) {
        UserMessagingPlatform.loadConsentForm(activity, {
            if (consentInformation.consentStatus == ConsentInformation.ConsentStatus.REQUIRED) {
                it.show(activity) {
                    if (consentInformation.consentStatus == ConsentInformation.ConsentStatus.OBTAINED) {
                        // App can start requesting ads.
                        Log.d(TAG, "App can start requesting ads")
                        initAdsAdmob()
                    }
                    loadForm(activity)
                }
            }

//            if (consentInformation.consentStatus == ConsentInformation.ConsentStatus.OBTAINED) {
//                // Finish submitting permission request
//                initAdsAdmob(context)
//            }
            Log.d(TAG, " ${consentInformation.consentStatus}")
        }, {
            // Handle the error.
            Log.e(TAG, "Error code: ${it.errorCode} message: ${it.message}")
        })
    }

    private fun initAdsAdmob() {
        MobileAds.initialize(context) { }
        if (BuildConfig.DEBUG) {
            val testDeviceIds =
                listOf(
                    "D1026E50BF1ED07C83DB7A6223D4E4F1",
                    "FFC4D523BB31210D3C81B44E42DCEB4F"
                )
            val configuration =
                RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build()
            MobileAds.setRequestConfiguration(configuration)
        }
//        initBanner()
        initBanner(context)
        createInterstitialAd()
    }

    private fun initBanner(context: Context) {
        adBanner = null
        adBanner = AdView(context)
        val adSize = context.adSize()
        adBanner?.setAdSize(adSize)
        adBanner?.adUnitId = context.getString(R.string.id_banner)

        val request = if (isCollapsibleBannerAd()) {
            AdRequest
                .Builder()
                .addNetworkExtrasBundle(AdMobAdapter::class.java, Bundle().apply {
                    putString("collapsible", "bottom")
                })
                .build()
        } else
            AdRequest.Builder().build()

        adBanner?.loadAd(request)
        adBanner?.adListener = object : AdListener() {
            override fun onAdLoaded() {
                super.onAdLoaded()
                adBannerListener?.onAdLoaded()
            }

            override fun onAdFailedToLoad(p0: LoadAdError) {
                super.onAdFailedToLoad(p0)
                adBannerListener?.onAdFailedToLoad(p0)
            }
        }
    }

    private fun Context.adSize(): AdSize {
        val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val displayMetrics = DisplayMetrics()
        wm.defaultDisplay.getMetrics(displayMetrics)

//        Display display = .thó.getWindowManager().getDefaultDisplay();
//        DisplayMetrics outMetrics = new DisplayMetrics();
//        wm.getMetrics(outMetrics);
        val widthPixels = displayMetrics.widthPixels
        val density = displayMetrics.density
        val adWidth = (widthPixels / density).toInt()
        return AdSize.getLandscapeAnchoredAdaptiveBannerAdSize(this, adWidth)
    }


    fun bannerListener(
        adListener: AdListener,
    ) {
        adBannerListener = adListener
    }

    fun showInterstitialAdCheck(activity: Activity) {
        try {
            countSelect++
            if (countSelect >= countSelectAdsFullScreen()) {
                if (mInterstitialAd != null) mInterstitialAd!!.show(activity)
                if (mInterstitialAd == null) createInterstitialAd()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun showInterstitialAd(activity: Activity) {
        try {
            if (mInterstitialAd != null) {
                mInterstitialAd!!.show(activity)
                countSelect = 0
            }
            if (mInterstitialAd == null) createInterstitialAd()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun createInterstitialAd() {
        Log.e(TAG,"createInterstitialAd ${isActiveInterstitialAd()}")
        if (isActiveInterstitialAd()) {
            if (interstitialAdLoadCallback == null) {
                interstitialAdLoadCallback = object : InterstitialAdLoadCallback() {
                    override fun onAdLoaded(interstitialAd: InterstitialAd) {
                        mInterstitialAd = interstitialAd
                        mInterstitialAd!!.fullScreenContentCallback =
                            object : FullScreenContentCallback() {
                                override fun onAdFailedToShowFullScreenContent(error: AdError) {}
                                override fun onAdDismissedFullScreenContent() {
                                    Log.e(TAG, "onAdDismissedFullScreenContent")
                                    mInterstitialAd = null
                                }

                                override fun onAdShowedFullScreenContent() {
                                    super.onAdShowedFullScreenContent()
                                    countSelect = 0
                                    Log.e("onAdShowedFull", " ")
                                }
                            }
//                        initNativeAd()
                    }

                    override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                        mInterstitialAd = null
                        Log.e(TAG, "onAdDismissedFullScreenContent")
                    }
                }
            }
            InterstitialAd.load(
                context,
                context.getString(R.string.id_intermediary),
                AdRequest.Builder().build(),
                interstitialAdLoadCallback!!
            )
        }
    }

    var nativeAd: NativeAd? = null
    fun createNativeAd(mNativeAd: (NativeAd) -> Unit) {
        try {
            if (nativeAd != null) {
                mNativeAd(nativeAd!!)
                return
            }
            Log.e(TAG, " createNativeAd")
            val adLoader =
                AdLoader.Builder(
                    context,
                    context.getString(R.string.id_native)
                )
                    .forNativeAd { nativeAd: NativeAd ->
                        this.nativeAd?.destroy()
                        this.nativeAd = nativeAd
                        mNativeAd(nativeAd)
                    }
                    .withAdListener(object : AdListener() {
                        override fun onAdFailedToLoad(error: LoadAdError) {
                            Log.e(TAG, " createNativeAd onAdFailedToLoad")
                            nativeAd?.destroy()
                            nativeAd = null
                        }
                    }).build()
            adLoader.loadAd(AdRequest.Builder().build())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun nativeAdRefresh() {
        nativeAd?.destroy()
        nativeAd = null
        createNativeAd {

        }
    }

    fun populateNativeAdView(nativeAd: NativeAd, unifiedAdBinding: AdUnifiedBinding) {
        val nativeAdView = unifiedAdBinding.root

        // Set the media view.
        nativeAdView.mediaView = unifiedAdBinding.adMedia

        // Set other ad assets.
        nativeAdView.headlineView = unifiedAdBinding.adHeadline
        nativeAdView.bodyView = unifiedAdBinding.adBody
        nativeAdView.callToActionView = unifiedAdBinding.adCallToAction
        nativeAdView.iconView = unifiedAdBinding.adAppIcon
        nativeAdView.priceView = unifiedAdBinding.adPrice
        nativeAdView.starRatingView = unifiedAdBinding.adStars
        nativeAdView.storeView = unifiedAdBinding.adStore
        nativeAdView.advertiserView = unifiedAdBinding.adAdvertiser

        // The headline and media content are guaranteed to be in every UnifiedNativeAd.
        unifiedAdBinding.adHeadline.text = nativeAd.headline
        nativeAd.mediaContent?.let { unifiedAdBinding.adMedia.mediaContent = it }

        // These assets aren't guaranteed to be in every UnifiedNativeAd, so it's important to
        // check before trying to display them.
        if (nativeAd.body == null) {
            unifiedAdBinding.adBody.visibility = View.INVISIBLE
        } else {
            unifiedAdBinding.adBody.visibility = View.VISIBLE
            unifiedAdBinding.adBody.text = nativeAd.body
        }

        if (nativeAd.callToAction == null) {
            unifiedAdBinding.adCallToAction.visibility = View.INVISIBLE
        } else {
            unifiedAdBinding.adCallToAction.visibility = View.VISIBLE
            unifiedAdBinding.adCallToAction.text = nativeAd.callToAction
        }

        if (nativeAd.icon == null) {
            unifiedAdBinding.adAppIcon.visibility = View.GONE
        } else {
            unifiedAdBinding.adAppIcon.setImageDrawable(nativeAd.icon?.drawable)
            unifiedAdBinding.adAppIcon.visibility = View.VISIBLE
        }

        if (nativeAd.price == null) {
            unifiedAdBinding.adPrice.visibility = View.INVISIBLE
        } else {
            unifiedAdBinding.adPrice.visibility = View.VISIBLE
            unifiedAdBinding.adPrice.text = nativeAd.price
        }

        if (nativeAd.store == null) {
            unifiedAdBinding.adStore.visibility = View.INVISIBLE
        } else {
            unifiedAdBinding.adStore.visibility = View.VISIBLE
            unifiedAdBinding.adStore.text = nativeAd.store
        }

        if (nativeAd.starRating == null) {
            unifiedAdBinding.adStars.visibility = View.INVISIBLE
        } else {
            unifiedAdBinding.adStars.rating = nativeAd.starRating!!.toFloat()
            unifiedAdBinding.adStars.visibility = View.VISIBLE
        }

        if (nativeAd.advertiser == null) {
            unifiedAdBinding.adAdvertiser.visibility = View.INVISIBLE
        } else {
            unifiedAdBinding.adAdvertiser.text = nativeAd.advertiser
            unifiedAdBinding.adAdvertiser.visibility = View.VISIBLE
        }

        // This method tells the Google Mobile Ads SDK that you have finished populating your
        // native ad view with this native ad.
        nativeAdView.setNativeAd(nativeAd)

        // Get the video controller for the ad. One will always be provided, even if the ad doesn't
        // have a video asset.
        val mediaContent = nativeAd.mediaContent
        val vc = mediaContent?.videoController

        // Updates the UI to say whether or not this ad has a video asset.
        if (vc != null && mediaContent.hasVideoContent()) {
            // Create a new VideoLifecycleCallbacks object and pass it to the VideoController. The
            // VideoController will call methods on this object when events occur in the video
            // lifecycle.
            vc.videoLifecycleCallbacks =
                object : VideoController.VideoLifecycleCallbacks() {
                    override fun onVideoEnd() {
                        // Publishers should allow native ads to complete video playback before
                        // refreshing or replacing them with another ad in the same UI location.
//                        mainActivityBinding.refreshButton.isEnabled = true
//                        mainActivityBinding.videostatusText.text = "Video status: Video playback has ended."
                        vc.play()
                        super.onVideoEnd()
                    }
                }
        } else {
//            mainActivityBinding.videostatusText.text = "Video status: Ad does not contain a video asset."
//            mainActivityBinding.refreshButton.isEnabled = true
        }
    }


}