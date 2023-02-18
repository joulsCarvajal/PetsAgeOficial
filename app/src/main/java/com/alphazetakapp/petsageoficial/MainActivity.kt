package com.alphazetakapp.petsageoficial

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.alphazetakapp.petsageoficial.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonCalcular.setOnClickListener {
            val ageString: String = binding.editText1.text.toString().ifEmpty { "0" } //Asigno el valor ingresado del evento
            val ageInt: Int = ageString.toInt() * 7
            val ageCat: Double = ageString.toDouble()
            val ageCatP2: Double = ageCat*ageCat //Son las potencias de la ecuación porque no sirve .pow
            val ageCatP3: Double = ageCat*ageCat*ageCat //Son las potencias de la ecuación porque no sirve .pow

            fun calcAgeCatLow17(): Int {
                var ageCatRLow17: Double = ((-0.00004 * ageCatP3) + (0.006 * ageCatP2) - (0.0366 * ageCat) + 0.199) * 10
                return  ageCatRLow17.toInt()
            }

            fun calcAgeCat(): String {
                var ageCatR: Double = (-0.00004 * ageCatP3) + (0.006 * ageCatP2) - (0.0366 * ageCat) + 0.199
                val result = (ageCatR * 100.0).roundToInt() / 100.0
                return result.toInt().toString()
            }

            if (binding.editText1.text.isNotEmpty()) {
                if(ageCat < 17){
                    val ageCatRe = calcAgeCatLow17()

                    if (btnSelDog.isChecked and btnSelCat.isChecked) {
                        Toast.makeText(this, "Selecciona solo uno", Toast.LENGTH_LONG).show()
                    } else if (!btnSelDog.isChecked and !btnSelCat.isChecked) {
                        Toast.makeText(this, "Selecciona al menos uno de los dos pets", Toast.LENGTH_LONG
                        ).show()
                    } else if (btnSelDog.isChecked) {
                        openDogR(ageInt)
                    } else if(btnSelCat.isChecked) {
                        openCatRLow17(ageCatRe)
                    }
                }else{
                    val ageCatR = calcAgeCat()

                    if (btnSelDog.isChecked and btnSelCat.isChecked) {
                        Toast.makeText(this, "Selecciona solo uno", Toast.LENGTH_LONG).show()
                    } else if (!btnSelDog.isChecked and !btnSelCat.isChecked) {
                        Toast.makeText(this, "Selecciona al menos uno de los dos pets", Toast.LENGTH_LONG).show()
                    } else if (btnSelDog.isChecked) {
                        openDogR(ageInt)
                    } else if(btnSelCat.isChecked) {
                        openCatR(ageCatR)
                    }
                }
            } else {
                Toast.makeText(this, getString(R.string.inputAge), Toast.LENGTH_LONG).show()
            }
        }


    }

    fun openDogR(ageInt: Int) {
        val intent = Intent(    this, ResultadoPerros::class.java)
        val a = getString(R.string.Your_age_Dog, ageInt)
        intent.putExtra("age_dog", a)
        startActivity(intent)
    }

    fun openCatR(ageCatR: String){
        val intent = Intent(this, ResultadoGatos::class.java)
        val b = getString(R.string.Your_age_Cat, ageCatR)
        intent.putExtra("age_cat", b)
        startActivity(intent)
    }

    fun openCatRLow17(ageCatRe: Int){
        val intent = Intent(this, ResultadoGatos::class.java)
        val c = getString(R.string.Your_age_Cat2, ageCatRe)
        intent.putExtra("age_cat", c)
        startActivity(intent)
    }
}