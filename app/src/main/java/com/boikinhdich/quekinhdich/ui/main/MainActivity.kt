package com.boikinhdich.quekinhdich.ui.main


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.amuse.animalsounds.utils.admod.AdmobManager
import com.amuse.animalsounds.utils.ext.diaLogSetting
import com.amuse.animalsounds.utils.ext.hideSoftKeyboard
import com.boikinhdich.quekinhdich.R
import com.boikinhdich.quekinhdich.adapter.CardModel
import com.boikinhdich.quekinhdich.databinding.ActivityMainBinding
import com.boikinhdich.quekinhdich.ui.DetailQueFragment
import com.boikinhdich.quekinhdich.ui.SelectQueFragment
import com.boikinhdich.quekinhdich.ui.TutorialQueFragment
import com.boikinhdich.quekinhdich.utils.ext.makeStatusBarTransparent
import com.boikinhdich.quekinhdich.utils.ext.tryCatch
import com.boikinhdich.quekinhdich.utils.versionAppFirebase
import com.google.android.gms.ads.AdListener
import com.google.android.play.core.review.ReviewManager
import com.google.android.play.core.review.testing.FakeReviewManager
import java.util.Timer


class MainActivity : AppCompatActivity(), FragmentListener {

    private var admobManager: AdmobManager? = null
    private var isAddBanner = false
    private var reviewManager: ReviewManager? = null
    private lateinit var binding: ActivityMainBinding // View Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Khởi tạo binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        reviewManager = FakeReviewManager(this)

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
            }

            switchFragment(SelectQueFragment(), "SelectQueFragment", false)
        }
    }

    override fun onSelectQueFragment() {
        switchFragment(SelectQueFragment(), "SelectQueFragment", true)
    }

    override fun onDetailQueFragment(item: CardModel) {
        switchFragment(DetailQueFragment.newInstance(item), "DetailQueFragment", true)
    }

    override fun onTutorialQueFragment() {
        switchFragment(TutorialQueFragment(), "TermsFragment", true)
    }

    override fun onDialogSetting() {
       diaLogSetting(reviewManager!!)
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