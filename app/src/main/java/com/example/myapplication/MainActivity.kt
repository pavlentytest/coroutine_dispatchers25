package com.example.myapplication

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL

class MainActivity : AppCompatActivity() {
    private val IMAGE_URL = "https://image.freepik.com/free-vector/cute-dog-with-bone_23-2147515747.jpg"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        CoroutineScope(Dispatchers.IO).launch {
            // так можно
            val bitmap = getOriginalBitmap()
            withContext(Dispatchers.Main) {
                // так нельзя
                //val bitmap = getOriginalBitmap()

               loadImage(bitmap)
            }


          /*  val originalDeferred = async(Dispatchers.IO) {
                getOriginalBitmap()
            }
            val originalBitmap = originalDeferred.await()
            loadImage(originalBitmap)
            val originalDeferredBnW = async(Dispatchers.Default) {
                delay(3000)
                applyFilter(originalBitmap)
            }
            loadImageBnW(originalDeferredBnW.await())

           */
        }
    }

    private fun getOriginalBitmap() = URL(IMAGE_URL).openStream().use { BitmapFactory.decodeStream(it) }

    private fun applyFilter(bitmap: Bitmap) = Filter.apply(bitmap)

    private fun loadImage(bitmap: Bitmap) {
        findViewById<ImageView>(R.id.imageView).apply {
            setImageBitmap(bitmap)
            visibility = View.VISIBLE
        }
    }

    private fun loadImageBnW(bitmap: Bitmap) {
        findViewById<ProgressBar>(R.id.progressBar).visibility = View.GONE
        findViewById<ImageView>(R.id.imageViewBnW).apply {
            setImageBitmap(bitmap)
            visibility = View.VISIBLE
        }
    }
}