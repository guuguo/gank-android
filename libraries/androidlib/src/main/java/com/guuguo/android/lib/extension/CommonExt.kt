package com.guuguo.android.lib.extension

import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Environment
import androidx.fragment.app.FragmentActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.guuguo.android.lib.ktx.runOnUIThread
import com.guuguo.android.lib.utils.LifecycleHandler
import com.guuguo.android.lib.utils.Utils
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

/** json相关 **/
fun Any.toJson() = Gson().toJson(this)

inline fun <reified T> String.toBean() = Gson().fromJson<T>(this, object : TypeToken<T>() {}.type)

/**
 * 将Bitmap保存到相册
 */
fun Bitmap.saveToAlbum(format: Bitmap.CompressFormat = Bitmap.CompressFormat.PNG, quality: Int = 100, filename: String = "", callback: ((path: String?, uri: Uri?)->Unit)? = null ) {
    GlobalScope.launch {
        try {
            //1. create path
            val dirPath = Environment.getExternalStorageDirectory().absolutePath + "/" + Environment.DIRECTORY_PICTURES
            val dirFile = File(dirPath)
            if (!dirFile.exists()) dirFile.mkdirs()
            val ext = when (format) {
                Bitmap.CompressFormat.PNG -> ".png"
                Bitmap.CompressFormat.JPEG -> ".jpg"
                Bitmap.CompressFormat.WEBP -> ".webp"
            }
            val target = File(dirPath, (if(filename.isEmpty()) System.currentTimeMillis().toString() else filename) + ext)
            if (target.exists()) target.delete()
            target.createNewFile()
            //2. save
            compress(format, quality, FileOutputStream(target))
            //3. notify
            MediaScannerConnection.scanFile(Utils.getContext(), arrayOf(target.absolutePath),
                    arrayOf("image/$ext")) { path, uri ->
                runOnUIThread {
                    callback?.invoke(path, uri)
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
            runOnUIThread { callback?.invoke(null, null) }
        }
    }
}
fun androidx.fragment.app.FragmentActivity.finishDelay(delay: Long = 1) {
    LifecycleHandler(this).postDelayed({ finish() }, delay)
}

//post, postDelay
fun androidx.fragment.app.FragmentActivity.post(action: ()->Unit){
    LifecycleHandler(this).post { action() }
}

fun androidx.fragment.app.FragmentActivity.postDelay(delay:Long = 0, action: ()->Unit){
    LifecycleHandler(this).postDelayed({ action() }, delay)
}