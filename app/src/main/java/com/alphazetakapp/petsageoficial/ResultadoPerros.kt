package com.alphazetakapp.petsageoficial

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.FileProvider
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import kotlinx.android.synthetic.main.activity_resultado_perros.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

class ResultadoPerros : AppCompatActivity() {

    private var interstitial: InterstitialAd? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resultado_perros)
        initLoadAds()
        getAndShowResult()
        btnBackDog.setOnClickListener { returnBack() }
        btnsharedog.setOnClickListener{
            interstitial?.show(this)
        }
    }

    private fun sharedog() {
        val customCanvasView = findViewById<LinearLayout>(R.id.framedog)
        val currentDate = Date()
        val currentDateString = android.text.format.DateFormat.format("yyy-mm-dd_hh:mm:ss", currentDate)
        val externalDirPath = getExternalFilesDir(null)?.absolutePath + "/" + currentDateString + ".jpg"
        val bitmapScreenshot = Bitmap.createBitmap(customCanvasView.width, customCanvasView.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmapScreenshot)
        customCanvasView.draw(canvas)
        val imageFile = File(externalDirPath)

        FileOutputStream(imageFile).use { outputStream ->
            bitmapScreenshot.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        }

        val fileProviderAuthority = "com.alphazetakapp.agepetsofficial.fileprovider"
        val uri = FileProvider.getUriForFile(this, fileProviderAuthority, imageFile)
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            putExtra(Intent.EXTRA_STREAM, uri)
            putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.alphazetakapp.agepetsofficial")
            type = "image/*"
        }
        val chooserIntent = Intent.createChooser(shareIntent, null)
        startActivity(chooserIntent)
        initLoadAds()
    }

    private fun getAndShowResult(){
        val bundle = intent.extras
        val name = bundle?.get("age_dog")
        tv1ResultDog.text = "$name"
    }
    private fun returnBack() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
    private fun initListeners() {
        interstitial?.fullScreenContentCallback = object : FullScreenContentCallback(){
            override fun onAdShowedFullScreenContent() {
                interstitial = null
            }
            override fun onAdDismissedFullScreenContent() {
                interstitial = null
                sharedog()
            }
            override fun onAdImpression() {
                interstitial = null
            }
        }
    }
    private fun initLoadAds() {
        var adRequest: AdRequest = AdRequest.Builder().build()
        InterstitialAd.load(this, "ca-app-pub-3940256099942544/1033173712", adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    interstitial = interstitialAd
                    initListeners()
                }
                override fun onAdFailedToLoad(p0: LoadAdError) {
                    interstitial = null
                    sharedog()
                }
            })
    }
}