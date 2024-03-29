package com.boikinhdich.quekinhdich.ui

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import android.widget.LinearLayout
import android.widget.TextView
import com.boikinhdich.quekinhdich.R
import com.boikinhdich.quekinhdich.adapter.CardAdapter
import com.boikinhdich.quekinhdich.utils.ext.tryCatch
import com.boikinhdich.quekinhdich.utils.firebaseAnalyticsScreen


class TermsFragment : Fragment() {

    private var listener: FragmentListener? = null
    private lateinit var webView: WebView
    private lateinit var viewLoading: LinearLayout
    private var load = 0

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentListener)
            listener = (context as FragmentListener)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_terms, container, false)

        webView = view.findViewById<WebView>(R.id.webView)
        viewLoading = view.findViewById<LinearLayout>(R.id.viewLoading)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firebaseAnalyticsScreen(requireContext(), javaClass.simpleName)
        setWebView("https://sites.google.com/view/animal-sound24/trang-ch%E1%BB%A7")
    }

    override fun onResume() {
        super.onResume()
        listener?.apply { onResumeFragment("") }
    }

    override fun onDestroy() {
        super.onDestroy()
        tryCatch {
            listener?.let { it.onResumeFragment(getString(R.string.app_name)) }
            listener = null
            load = 0
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setWebView(url: String) {

        val webSettings: WebSettings = webView.settings
        webSettings.javaScriptEnabled = true
        webSettings.loadWithOverviewMode = true
        webSettings.useWideViewPort = true
        webSettings.builtInZoomControls = true
        webSettings.allowFileAccess = true
        webView.setInitialScale(1);

        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, progress: Int) {
                //Make the bar disappear after URL is loaded, and changes string to Loading...
                try {
                    webView.visibility = View.VISIBLE
                    if (progress == 100 && load == 1) {
                        load = 2
                        viewLoading.visibility = View.GONE
                    }
                    if (progress < 100 && load == 0) {
                        load = 1
                        viewLoading.visibility = View.VISIBLE
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        webView.clearCache(true)
        webView.clearHistory()
        webView.loadUrl(url)
    }
}