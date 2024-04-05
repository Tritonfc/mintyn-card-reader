package com.starter.app.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import com.starter.app.MainActivity
import com.starter.app.databinding.ActivityCameraScannerBinding


class CameraScannerActivity : AppCompatActivity() {
    private lateinit var  cameraScannerBinding:ActivityCameraScannerBinding
    private var imageCapture: ImageCapture? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cameraScannerBinding = ActivityCameraScannerBinding.inflate(layoutInflater)
        val view = cameraScannerBinding.root
        setContentView(view)

        if (allPermissionsGranted()) {
            startCamera()
        } else {
            requestPermissions()
        }

        cameraScannerBinding.imageCaptureButton.setOnClickListener {
            takePhoto()
        }
    }

    private fun takePhoto(){
        val imageCapture = imageCapture ?: return
        imageCapture.takePicture(
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageCapturedCallback(){
                override fun onCaptureSuccess(image: ImageProxy) {
                    super.onCaptureSuccess(image)
                    runTextRecognition(image)
                    Toast.makeText(this@CameraScannerActivity,"Scanning Please wait...",Toast.LENGTH_SHORT).show()
                }
            }
        )
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener(Runnable {
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(cameraScannerBinding.viewFinder.surfaceProvider)
                }

            imageCapture = ImageCapture.Builder().build()
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()

                // Bind use cases to camera
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview,imageCapture)

            } catch(exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }
        }, ContextCompat.getMainExecutor(this))




    }


    //Text recognition funtion using MLKIT

    @SuppressLint("UnsafeOptInUsageError")
    private fun runTextRecognition(imageProxy:ImageProxy) {
        val image = imageProxy.image?.let { InputImage.fromMediaImage(it, imageProxy.imageInfo.rotationDegrees) }
        val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

        if (image != null) {
            recognizer.process(image)
                .addOnSuccessListener { texts ->

                    processTextRecognitionResult(texts)
                }
                .addOnFailureListener { e -> // Task failed with an exception
                    Toast.makeText(this,"Error detecting image, Please try again",Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
        }
    }

    private fun processTextRecognitionResult(texts: Text){
        val sb = StringBuilder()
        val blocks = texts.textBlocks
        if(blocks.isEmpty()){
            Toast.makeText(this, "Unable to Extract Card Information",Toast.LENGTH_SHORT).show()
        }

        for(i in 0 until blocks.size){
            val lines = blocks[i].lines
            for (j in 0 until  lines.size){
                val elements = lines[j].elements
                for(k in 0 until  elements.size){
                    val value = elements[k].text

                    if(isCardNumber(value) && sb.length<6){
                        sb.append(value)
                    }

                }

            }
        }

        if(sb.length >=6){
            val intent = Intent(this@CameraScannerActivity, CardInfoActivity::class.java)
            intent.putExtra(MainActivity.BUNDLE_KEY, sb.toString())
            startActivity(intent)
        }else{
            Toast.makeText(this, "Unable to Extract Card Information",Toast.LENGTH_SHORT).show()
        }


    }


    //Regex function to only select card number after scanning card
    private fun isCardNumber(text:String):Boolean{
        val regex = "-?\\d+(\\.\\d+)?"


        return text.matches(regex.toRegex())
    }


    private fun requestPermissions() {
        activityResultLauncher.launch(CameraScannerActivity.REQUIRED_PERMISSIONS)
    }

    private fun allPermissionsGranted() = CameraScannerActivity.REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            baseContext, it) == PackageManager.PERMISSION_GRANTED
    }
    private val activityResultLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions())
        { permissions ->
            // Handle Permission granted/rejected
            var permissionGranted = true
            permissions.entries.forEach {
                if (it.key in CameraScannerActivity.REQUIRED_PERMISSIONS && !it.value)
                    permissionGranted = false
            }
            if (!permissionGranted) {
                Toast.makeText(baseContext,
                    "Permission request denied",
                    Toast.LENGTH_SHORT).show()
            } else {
                startCamera()
            }
        }

    companion object {

        private const val TAG = "CameraXApp"

        private val REQUIRED_PERMISSIONS =
            mutableListOf (
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO
            ).apply {
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                    add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }
            }.toTypedArray()
    }
}