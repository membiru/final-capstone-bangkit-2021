package com.bangkit.whatdish.ui.main

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.bangkit.whatdish.R
import com.bangkit.whatdish.data.source.local.FoodEntity
import com.bangkit.whatdish.databinding.ActivityMainBinding
import com.bangkit.whatdish.ui.detail.DetailActivity
import com.bangkit.whatdish.ui.intro.IntroActivity
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var storageReference: StorageReference
    private lateinit var currentPhotoPath: String
    private lateinit var mainActivityBinding: ActivityMainBinding

    companion object {
        const val CAMERA_PERM_CODE = 101
        const val CAMERA_REQUEST_CODE = 102
        const val GALLERY_REQUEST_CODE = 105
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivityBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainActivityBinding.root)

        storageReference = FirebaseStorage.getInstance().reference

        mainActivityBinding.cvItem1.setOnClickListener {
            setPrefsData()
            startActivity(Intent(this@MainActivity, IntroActivity::class.java))
            finish()
        }
        mainActivityBinding.cvItem2.setOnClickListener {
            askCameraPemission()
        }
        mainActivityBinding.cvItem3.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(gallery, GALLERY_REQUEST_CODE)
        }

    }

    private fun askCameraPemission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), CAMERA_PERM_CODE)
        } else {
            dispatchTakePictureIntent()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERM_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                dispatchTakePictureIntent()
            } else {
                show(getString(R.string.t_camera_required))
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                val f = File(currentPhotoPath)
                Log.d("tag", "Absolute Url of Image is " + Uri.fromFile(f))
                val mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
                val contentUri = Uri.fromFile(f)
                mediaScanIntent.data = contentUri
                this.sendBroadcast(mediaScanIntent)

                uploadImageToFirebase(f.name, contentUri)
            }
        }
        if (requestCode == GALLERY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                val contentUri = data!!.data
                val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
                val imageFileName = "JPEG_" + timeStamp + "." + contentUri?.let { getFileExt(it) }
                Log.d("tag", "onActivityResult: Gallery Image Uri:  $imageFileName")

                if (contentUri != null) {
                    uploadImageToFirebase(imageFileName, contentUri)
                }

            }
        }
    }

    private fun goDetailFood(name: String, image: Uri) {
        val intent = Intent(applicationContext, DetailActivity::class.java)
        val food = FoodEntity(
            imageUri = image,
            imageTitle = name,
            nameFood = null,
            infoFood = null
        )
        intent.putExtra(DetailActivity.EXTRA_DATA, food)
        startActivity(intent)
    }

    private fun uploadImageToFirebase(name: String, contentUri: Uri) {
        val image = storageReference.child("/$name")
        image.putFile(contentUri).addOnSuccessListener {
            image.downloadUrl.addOnSuccessListener { uri ->
                Log.d("tag", "onSuccess: Uploaded Image URl is $uri")
                goDetailFood(name,contentUri)
            }
        }.addOnFailureListener {
            show(getString(R.string.t_upload_failed))
        }
    }

    private fun getFileExt(contentUri: Uri): String? {
        val c = contentResolver
        val mime = MimeTypeMap.getSingleton()
        return mime.getExtensionFromMimeType(c.getType(contentUri))
    }

    @Throws(IOException::class)
    private fun createImageFile(): File? {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        val storageDir =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile(
            imageFileName,  /* prefix */
            ".jpg",  /* suffix */
            storageDir /* directory */
        )
        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.absolutePath
        return image
    }

    private fun setPrefsData() {
        val pref = applicationContext.getSharedPreferences("myPrefs", MODE_PRIVATE)
        val editor = pref.edit()
        editor.putBoolean("isIntroOpnend", false)
        editor.commit()
    }

    private fun dispatchTakePictureIntent(){
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            var photoFile: File? = null
            try {
                photoFile = createImageFile()
            } catch (ex: IOException) {

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                val photoURI = FileProvider.getUriForFile(
                    this,
                    "com.bangkit.android.fileprovider",
                    photoFile
                )
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE)
            }
        }
    }


    private fun show(message: String) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
    }
}