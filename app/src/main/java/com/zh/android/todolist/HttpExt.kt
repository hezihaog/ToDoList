package com.zh.android.todolist

import android.os.AsyncTask
import com.zh.android.http.HttpRequest

/**
 * @author wally
 * @date 2021/01/05
 */

/**
 * 异步执行请求
 */
fun HttpRequest.enqueue(
    onSuccess: (result: String) -> Unit,
    onFail: ((e: Exception) -> Unit?)? = null,
    onCancelled: ((result: String?) -> Unit?)? = null
) {
    val that = this
    object : AsyncTask<Void, Void, String?>() {
        override fun doInBackground(vararg params: Void): String? {
            try {
                return that.execute().body()
            } catch (e: Exception) {
                onFail?.invoke(e)
            }
            return null
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            if (result != null) {
                onSuccess(result)
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