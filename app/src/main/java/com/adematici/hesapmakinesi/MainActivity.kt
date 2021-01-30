package com.adematici.hesapmakinesi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.adematici.hesapmakinesi.databinding.ActivityMainBinding
import java.lang.Exception
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    var anlikNumara: String? = null
    var anlikDeger: String? = null
    var eskiNumara: Double? = null
    var islemSonucu: Double? = 0.0

    var hesaplamaString: String? = null
    var hesaplamaString2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        ekraniTemizle()

        hesaplamaString = ""
        hesaplamaString2 = ""

        anlikNumara = ""
        anlikDeger = ""
        binding.editText.inputType = InputType.TYPE_NULL
    }

    fun tiklamalar(view: View){
        when(view.id){
            R.id.button0 -> {sayiTiklama(0)}
            R.id.button1 -> {sayiTiklama(1)}
            R.id.button2 -> {sayiTiklama(2)}
            R.id.button3 -> {sayiTiklama(3)}
            R.id.button4 -> {sayiTiklama(4)}
            R.id.button5 -> {sayiTiklama(5)}
            R.id.button6 -> {sayiTiklama(6)}
            R.id.button7 -> {sayiTiklama(7)}
            R.id.button8 -> {sayiTiklama(8)}
            R.id.button9 -> {sayiTiklama(9)}
            R.id.buttonNokta -> {sayiTiklama(100)}
            R.id.buttonToplama -> {islemlerTiklama("+")}
            R.id.buttonCikarma -> {islemlerTiklama("-")}
            R.id.buttonCarpma -> {islemlerTiklama("*")}
            R.id.buttonBolme -> {islemlerTiklama("/")}
            R.id.buttonSonuc -> { sonucBilgisi() }
            R.id.buttonTemizle -> { ekraniTemizle() }
            R.id.buttonArtiEksi -> {
                anlikNumara = "-" + anlikNumara
                hesaplamaString = "-" + hesaplamaString
                binding.editText.setText(hesaplamaString)
            }
            R.id.buttonMod -> { modHesaplayıcı() }
            R.id.buttonGeriAl -> { geriAl() }
        }
    }

    fun sayiTiklama(sayi: Int){
        if(sayi == 100){
            anlikNumara += "."
        } else {
            anlikNumara += sayi
        }

        if(anlikDeger != null && eskiNumara != 0.0){
            binding.editText.setText(hesaplamaString + anlikNumara)
            return
        }
        hesaplamaString = anlikNumara
        hesaplamaString2 = anlikNumara
        binding.editText.setText(anlikNumara)
        binding.editText1.setText(anlikNumara)
    }

    fun islemlerTiklama(deger: String){
        if(anlikNumara != null){
            eskiNumara = anlikNumara!!.toDouble()
            anlikNumara = ""
        }
        when(deger){
            "+" -> { anlikDeger = "+" }
            "-" -> { anlikDeger = "-" }
            "*" -> { anlikDeger = "*" }
            "/" -> { anlikDeger = "/" }
        }
        hesaplamaString += anlikDeger
        hesaplamaString2 += anlikDeger
        binding.editText.setText(hesaplamaString)
        binding.editText1.setText(hesaplamaString2)
    }

    fun sonucBilgisi(){
        try {
            if(eskiNumara != null && anlikNumara != null){
                hesaplamaString = ""
                hesaplamaString2 += anlikNumara
                when(anlikDeger){
                    "+" -> { islemSonucu = eskiNumara!!.toDouble() + anlikNumara!!.toDouble() }
                    "-" -> { islemSonucu = eskiNumara!!.toDouble() - anlikNumara!!.toDouble() }
                    "*" -> { islemSonucu = eskiNumara!!.toDouble() * anlikNumara!!.toDouble() }
                    "/" -> { islemSonucu = eskiNumara!!.toDouble() / anlikNumara!!.toDouble() }
                }
            }
            val format = DecimalFormat("0.#")
            format.format(islemSonucu)
            anlikNumara = format.format(islemSonucu)
            hesaplamaString = anlikNumara
            binding.editText.setText(format.format(islemSonucu))
            binding.editText1.setText(hesaplamaString2.toString())
            anlikDeger = ""
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun ekraniTemizle(){
        binding.editText1.setText("")
        binding.editText.setText("")
        anlikNumara = ""
        anlikDeger = ""
        eskiNumara = 0.0
        islemSonucu = 0.0
        hesaplamaString2 = ""
        hesaplamaString = ""
    }

    fun modHesaplayıcı(){
        var text = binding.editText.text.toString()
        if(text == ""){
            return
        } else {
            var modDeger = text.toDouble()/100
            anlikNumara = modDeger.toString()
            binding.editText.setText(anlikNumara)
        }
    }

    fun geriAl(){
        val text = binding.editText.text.toString()
        try {
            if(!TextUtils.isEmpty(text) && text.length>1){
                val yeniText = text.substring(0,text.length-1)
                if(anlikNumara!!.isNotEmpty() && !TextUtils.isEmpty(anlikNumara)){
                    anlikNumara = anlikNumara!!.substring(0,anlikNumara!!.length-1)
                    islemSonucu = islemSonucu!!/10
                    hesaplamaString = hesaplamaString!!.substring(0,hesaplamaString!!.length-1)
                    hesaplamaString2 = hesaplamaString2!!.substring(0,hesaplamaString2!!.length-1)
                }
                binding.editText.setText(yeniText)
                binding.editText1.setText(hesaplamaString2)
            } else {
                ekraniTemizle()
            }
        } catch (e: Exception){
            Toast.makeText(applicationContext,""+e.printStackTrace(),Toast.LENGTH_SHORT).show()
        }
    }

}