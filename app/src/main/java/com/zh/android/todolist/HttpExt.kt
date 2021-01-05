package com.zh.android.todolist

import android.os.AsyncTask
import cn.hutool.http.HttpRequest

/**
 * @author wally
 * @date 2021/01/05
 */

/**
 * 异步执行请求
 */
fun HttpRequest.enqueue(
    onSuccess: (result: String) -> Unit,
    onFail: (() -> Unit?)? = null,
    onCancelled: ((result: String?) -> Unit?)? = null
) {
    val that = this
    object : AsyncTask<Void, Void, String>() {
        override fun doInBackground(vararg params: Void?): String {
            return that.execute().body()
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            if (result != null) {
                onSuccess(result)
            } else {
                onFail?.invoke()
            }
        }

        override fun onCancelled(result: String?) {
            super.onCancelled(result)
            onCancelled?.invoke(result)
        }

        override fun onCancelled() {
            super.onCancelled()
            onCancelled?.invoke(null)
        }
    }.execute()
}