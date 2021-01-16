package com.zh.android.todolist

import android.annotation.SuppressLint
import android.os.*
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.zh.android.http.HttpRequest
import com.apkfuns.logutils.LogUtils
import com.zh.android.todolist.ext.enqueue

class MainActivity : AppCompatActivity() {
    private val mMainHandler:Handler by lazy {
        Handler(Looper.getMainLooper())
    }
    private var mRequestTask: AsyncTask<Void, Void, String?>? = null

    private val vWebView by lazy {
        val mainContent = findViewById<ViewGroup>(R.id.main_content)
        val webView = WebView(this)
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
    private val mWebUrl = "file:///android_asset/todolist/ToDoList.html"

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        vWebView.apply {
            //远程支持Debug
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                WebView.setWebContentsDebuggingEnabled(true)
            }
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
                cacheMode = WebSettings.LOAD_NO_CACHE
                //5.0以上允许加载http和https混合的页面(5.0以下默认允许，5.0+默认禁止)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                }
            }
            loadUrl(mWebUrl)
        }
        testHttp()
    }

    override fun onDestroy() {
        super.onDestroy()
        vWebView.apply {
            parent?.also {
                (it as ViewGroup).removeView(vWebView)
            }
            removeAllViews()
            destroy()
        }
        mRequestTask?.apply {
            if (!isCancelled) {
                cancel(true)
            }
        }
    }

    private fun testHttp() {
        val url = "https://www.wanandroid.com/banner/json"
        //val url = "http://localhost:8080/user/info"
        mRequestTask = HttpRequest
            .get(url)
            .header("token", "123")
            .timeout(-1)
            .enqueue({
                toast(it)
                LogUtils.json(it)
            }, {
                it.printStackTrace()
                toast(it.message)
                LogUtils.d("请求失败，原因：${it.message}")
            })
    }

    private fun toast(msg: String?) {
        if (msg.isNullOrBlank()) {
            return
        }
        fun show() {
            Toast.makeText(this@MainActivity.applicationContext, msg, Toast.LENGTH_SHORT).show()
        }
        if (Thread.currentThread() == Looper.getMainLooper().thread) {
            show()
        } else {
            mMainHandler.post {
                show()
            }
        }
    }
}