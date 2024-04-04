package com.starter.app

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.Preview
import androidx.core.content.ContextCompat
import androidx.camera.lifecycle.ProcessCameraProvider
import com.starter.app.databinding.ActivityMainBinding
import com.starter.app.ui.CameraScannerActivity
import com.starter.app.ui.CardInfoActivity

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = mainBinding.root
        setContentView(view)


        mainBinding.apply {
            numberScan.setOnClickListener{
                val intent = Intent(this@MainActivity, CameraScannerActivity::class.java)
                startActivity(intent)
            }

            cameraImage.setOnClickListener {
                val intent = Intent(this@MainActivity, CameraScannerActivity::class.java)
                startActivity(intent)

            }
            button.setOnClickListener {
                if(editText.text.isNullOrEmpty()){
                    Toast.makeText(this@MainActivity,"Please enter card number before proceeding",Toast.LENGTH_SHORT).show()
                }else if(editText.text?.length!! < 6){
                    Toast.makeText(this@MainActivity,"Please enter card number greater than 6 before proceeding",Toast.LENGTH_SHORT).show()
                }else{
                    val number = editText.text?.trim()
                    val intent = Intent(this@MainActivity, CardInfoActivity::class.java)
                    intent.putExtra(BUNDLE_KEY, number.toString())
                    startActivity(intent)
                }
            }
        }

    }



    companion object {
        const val BUNDLE_KEY = "Card Number Key"
    }

}