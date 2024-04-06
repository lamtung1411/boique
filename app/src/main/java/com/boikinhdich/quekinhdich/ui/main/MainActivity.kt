package com.boikinhdich.quekinhdich.ui.main


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.amuse.animalsounds.utils.admod.AdmobManager
import com.amuse.animalsounds.utils.ext.hideSoftKeyboard
import com.boikinhdich.quekinhdich.R
import com.boikinhdich.quekinhdich.adapter.CardModel
import com.boikinhdich.quekinhdich.databinding.ActivityMainBinding
import com.boikinhdich.quekinhdich.ui.DetailQueFragment
import com.boikinhdich.quekinhdich.ui.SelectQueFragment
import com.boikinhdich.quekinhdich.ui.TermsFragment
import com.boikinhdich.quekinhdich.ui.TutorialQueFragment
import com.boikinhdich.quekinhdich.utils.ext.makeStatusBarTransparent
import com.boikinhdich.quekinhdich.utils.ext.tryCatch
import com.boikinhdich.quekinhdich.utils.versionAppFirebase
import com.google.android.gms.ads.AdListener
import java.util.Timer


class MainActivity : AppCompatActivity(), FragmentListener {

    private var admobManager: AdmobManager? = null
    private var isAddBanner = false
    val TAG = "MainActivity"
    private var timerAds: Timer? = null

    private lateinit var binding: ActivityMainBinding // View Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Khởi tạo binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {

            AdmobManager.initializeInstance()
            AdmobManager.getInstance().initPopupAdmobGDPR(this@MainActivity)

            versionAppFirebase()

            makeStatusBarTransparent()

            tryCatch {

                admobManager = AdmobManager.getInstance()
                admobManager?.let {
                    it.bannerListener(object : AdListener() {
                        override fun onAdLoaded() {
                            super.onAdLoaded()
                            viewAdBanner.addView(it.adBanner)
                        }
                    })
                }

                isAddBanner = true

//                btnShare.setOnClickListener {
//                    onShareApp()
//                }

//                btnMore.setOnClickListener {
//                    diaLogSetting(reviewManager!!, object : ChooseLanguageListener {
//                        override fun onChangeLanguage() {
//                            triggerRestart(this@MainActivity)
//                        }
//
//                        override fun onTemps() {
//
//                            switchFragment(
//                                TermsFragment(),
//                                "TermsFragment",
//                                true
//                            )
//                        }
//                    })
//                }

//                if (BuildConfig.VERSION_CODE < versionAppFirebase())
//                    showNoiceUpdateApp(this)
            }

            switchFragment(SelectQueFragment(), "SelectQueFragment", true)
        }
    }

    override fun onSelectQueFragment() {
        switchFragment(SelectQueFragment(), "SelectQueFragment", true)
    }

    override fun onDetailQueFragment(item: CardModel) {
        switchFragment(DetailQueFragment.newInstance(item), "DetailQueFragment", true)
    }

    override fun onTermsFragment() {
        switchFragment(TermsFragment(), "TermsFragment", true)
    }

    override fun onTutorialQueFragment() {
        switchFragment(TutorialQueFragment(), "TermsFragment", true)
    }

    fun switchFragment(
        fragment: Fragment?,
        tag: String?,
        isTaskback: Boolean,
    ) {
        hideSoftKeyboard()
        val fragmentTransaction =
            supportFragmentManager
                .beginTransaction().setCustomAnimations(
                    R.animator.slide_in_left, R.animator.slide_out_left,
                    R.animator.slide_out_right, R.animator.slide_in_right
                )
                .replace(R.id.container, fragment!!, tag)
        if (isTaskback) fragmentTransaction.addToBackStack(tag)
        fragmentTransaction.commit()
    }
}