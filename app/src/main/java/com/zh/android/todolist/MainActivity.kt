package com.zh.android.todolist

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    /**
     * Url地址
     */
    private val mWebUrl = "file:///android_asset/ToDoList.html"

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val webView = findViewById<WebView>(R.id.web_view)
        webView.apply {
            //远程支持Debug
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                WebView.setWebContentsDebuggingEnabled(true)
            }
            val settings: WebSettings = settings
            //当页面宽度大于WebView宽度时，缩小使页面宽度等于WebView宽度
            settings.loadWithOverviewMode = false
            //支持JS
            settings.javaScriptEnabled = true
            //支持通过JS打开新窗口
            settings.javaScriptCanOpenWindowsAutomatically = true
            //将图片调整到合适WebView的大小
            settings.useWideViewPort = true
            //缩放至屏幕的大小
            settings.loadWithOverviewMode = true
            //支持自动加载图片
            settings.loadsImagesAutomatically = true
            //支持多窗口
            settings.supportMultipleWindows()
            //支持缩放
            //settings.setSupportZoom(true);
            settings.setSupportZoom(false)
            //支持双击缩放、缩小
            //settings.setBuiltInZoomControls(true);
            settings.builtInZoomControls = false
            //设置允许访问见
            settings.allowFileAccess = true
            //设置支持访问内容，支持file协议
            settings.allowContentAccess = true
            //设置是否允许通过 file url 加载的 Js代码读取其他的本地文件
            settings.allowFileAccessFromFileURLs = true
            //设置是否允许通过 file url 加载的 Javascript 可以访问其他的源(包括http、https等源)
            settings.allowUniversalAccessFromFileURLs = true
            //开启DOM存储
            settings.domStorageEnabled = true
            //不进行缓存
            settings.cacheMode = WebSettings.LOAD_NO_CACHE
            //5.0以上允许加载http和https混合的页面(5.0以下默认允许，5.0+默认禁止)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            }
        }
        webView.loadUrl(mWebUrl)
    }
}