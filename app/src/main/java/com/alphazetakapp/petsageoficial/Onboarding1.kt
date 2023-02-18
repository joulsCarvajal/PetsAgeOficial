package com.alphazetakapp.petsageoficial

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_onboarding1.*


class Onboarding1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding1)

        buttonNext.setOnClickListener { abrir() }
    }

    private fun abrir() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}