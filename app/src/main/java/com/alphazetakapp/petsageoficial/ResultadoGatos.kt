package com.alphazetakapp.petsageoficial

import android.app.PendingIntent
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.core.content.FileProvider
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import kotlinx.android.synthetic.main.activity_resultado_gatos.*
import java.io.File
import java.io.FileOutputStream
import java.util.*

class ResultadoGatos : AppCompatActivity() {

    //private var interstitial: InterstitialAd? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resultado_gatos)
        //initLoadAds()
        getAndShowResult()
        btnBackCat.setOnClickListener { returnBack() }
        btnsharecat.setOnClickListener{
            //interstitial?.show(this)
            sharecat()
        }
    }
    private fun sharecat() {

        val canvas_custom = findViewById<LinearLayout>(R.id.framecat)

        val formato_fecha = Date()
        val fecha_actual = android.text.format.DateFormat.format("yyy-mm-dd_hh:mm:ss",formato_fecha)
        //val ruta_dir_externo = getExternalFilesDir(Environment.DIRECTORY_DCIM)?.absolutePath + "/" + fecha_actual + ".jpg"
        val ruta_dir_externo = getExternalFilesDir(null)?.absolutePath + "/" + fecha_actual + ".jpg"
        //val ruta_dir_externo = getFilesDir()?.absolutePath + "/" + fecha_actual + ".jpg"
        //val ruta_dir_externo = getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        val bitmap_screenshot = Bitmap.createBitmap(canvas_custom.width, canvas_custom.height,
            Bitmap.Config.ARGB_8888)
        val canvas2 = Canvas(bitmap_screenshot)
        canvas_custom.draw(canvas2)
        val imagefile = File(ruta_dir_externo)
        val outputStream = FileOutputStream(imagefile)
        bitmap_screenshot.compress(Bitmap.CompressFormat.JPEG,100,outputStream)
        outputStream.flush()
        outputStream.close()
        //val URI = FileProvider.getUriForFile(applicationContext, "com.alphazetakapp.agepetsofficial.fileprovider",imagefile )
        val URI = FileProvider.getUriForFile(this, "com.alphazetakapp.agepetsofficial.fileprovider",imagefile )
        //val URI = Uri.parse(ruta_dir_externo.toUri().toString())

///////////////
        val intentshare = Intent()
        intentshare.action = Intent.ACTION_SEND
        intentshare.putExtra(Intent.EXTRA_STREAM, URI)
        intentshare.type = "image/*"
        intentshare.putExtra(Intent.EXTRA_TEXT,"https://play.google.com/store/apps/details?id=com.alphazetakapp.agepetsofficial")
        intentshare.type = "text/plain"

        val shareIntent = Intent.createChooser(intentshare, null)
        startActivity(shareIntent)
        //initLoadAds()

    }

    private fun getAndShowResult(){
        val bundle = intent.extras
        val name = bundle?.get("age_cat")
        tv1ResultCat.text = "$name"
        //initLoadAds()
    }

    private fun returnBack() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
    /*private fun initListeners() {
        interstitial?.fullScreenContentCallback = object : FullScreenContentCallback(){
            override fun onAdShowedFullScreenContent() {
                interstitial = null
            }
            override fun onAdDismissedFullScreenContent() {
                interstitial = null
                sharecat()
            }
            override fun onAdImpression() {
                interstitial = null
            }
        }
    }*/
    /*private fun initLoadAds() {
        var adRequest: AdRequest = AdRequest.Builder().build()
        InterstitialAd.load(this, "ca-app-pub-7874686337995143/7519334115", adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    interstitial = interstitialAd
                    initListeners()
                }
                override fun onAdFailedToLoad(p0: LoadAdError) {
                    interstitial = null
                    sharecat()
                }
            })
    }*/
}