package com.starter.app

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.starter.app.databinding.ActivityMainBinding
import com.starter.app.ui.CardInfoActivity

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = mainBinding.root
        setContentView(view)


        mainBinding.apply {
            button.setOnClickListener {
                if(editText.text.isNullOrEmpty()){
                    Toast.makeText(this@MainActivity,"Please enter card number before proceeding",Toast.LENGTH_LONG).show()
                }else{
                    val number = editText.text?.trim()
                    val intent = Intent(this@MainActivity, CardInfoActivity::class.java)
                    intent.putExtra(BUNDLE_KEY, number)
                    startActivity(intent)

                }
            }
        }

    }
    companion object{
        const val BUNDLE_KEY = "Card Number Key"
    }

}