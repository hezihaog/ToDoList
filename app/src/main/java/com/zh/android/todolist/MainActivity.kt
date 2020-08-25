package com.zh.android.todolist

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.ViewGroup
import android.webkit.WebSettings
import androidx.appcompat.app.AppCompatActivity
import org.xwalk.core.XWalkView

class MainActivity : AppCompatActivity() {
    private val vWebView by lazy {
        val mainContent = findViewById<ViewGroup>(R.id.main_content)
        val webView = XWalkView(this)
        mainContent.addView(
            webView,
            ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        )
        webView
    }

    /**
     * Url地址
     */
    private val mWebUrl = "file:///android_asset/ToDoList.html"
    //private val mWebUrl = "https://www.baidu.com/"

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        vWebView.apply {
            settings.apply {
                //当页面宽度大于WebView宽度时，缩小使页面宽度等于WebView宽度
                loadWithOverviewMode = false
                //支持JS
                javaScriptEnabled = true
                //支持通过JS打开新窗口
                javaScriptCanOpenWindowsAutomatically = true
                //将图片调整到合适WebView的大小
                useWideViewPort = true
                //缩放至屏幕的大小
                loadWithOverviewMode = true
                //支持自动加载图片
                loadsImagesAutomatically = true
                //支持多窗口
                supportMultipleWindows()
                //支持缩放
                //settings.setSupportZoom(true);
                setSupportZoom(false)
                //支持双击缩放、缩小
                //settings.setBuiltInZoomControls(true);
                builtInZoomControls = false
                //设置允许访问见
                allowFileAccess = true
                //设置支持访问内容，支持file协议
                allowContentAccess = true
                //设置是否允许通过 file url 加载的 Js代码读取其他的本地文件
                allowFileAccessFromFileURLs = true
                //设置是否允许通过 file url 加载的 Javascript 可以访问其他的源(包括http、https等源)
                allowUniversalAccessFromFileURLs = true
                //开启DOM存储
                domStorageEnabled = true
                //不进行缓存
                settings.cacheMode = WebSettings.LOAD_NO_CACHE;
            }
            loadUrl(mWebUrl)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        vWebView.apply {
            loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            removeAllViews()
            vWebView.extensionManager.onDestroy()
            parent?.also {
                (it as ViewGroup).removeView(vWebView)
            }
        }
    }
}