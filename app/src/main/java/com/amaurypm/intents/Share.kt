package com.amaurypm.intents

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream

class Share : AppCompatActivity() {

    //Uri para la imagen que seleccione el usuario
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share)
    }

    fun clicks(view: View) {

        when(view.id){

            //Click en la imagen
            R.id.ivSharedImage -> {
                val pickIntent = Intent(Intent.ACTION_PICK)
                pickIntent.type = "image/*"
                gallery.launch(pickIntent)
            }

            //Click en el botón para compartir
            R.id.btnShare -> {
                /*val shareIntent = Intent().apply{
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, "Este es mi texto a compartir")
                    type = "text/plain"
                }

                startActivity(shareIntent)*/

                if(imageUri==null){
                    Toast.makeText(this, "Por favor selecciona una imagen primero", Toast.LENGTH_LONG).show()
                }else{
                    val shareIntent = Intent(Intent.ACTION_SEND)
                    shareIntent.type = "image/jpeg"
                    shareIntent.putExtra(Intent.EXTRA_STREAM, customizeContentUri())
                    shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    startActivity(Intent.createChooser(shareIntent, "Compartir via:"))
                }

            }

        }

    }

    val gallery: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){
        if(it.resultCode == Activity.RESULT_OK){
            //Se seleccionó una imagen
            Toast.makeText(this, "Imagen seleccionada exitosamente", Toast.LENGTH_SHORT).show()

            val data = it.data
            imageUri = data?.data

            //Asignando la imagen al imageview
            val ivSharedImage = findViewById<ImageView>(R.id.ivSharedImage)
            ivSharedImage.setImageURI(imageUri)

        }else{
            //Lo canceló sin seleccionar una imagen
            Toast.makeText(this, "No se seleccionó ninguna imagen", Toast.LENGTH_SHORT).show()
        }
    }

    private fun customizeContentUri(): Uri? {
        var bitmap: Bitmap? = null
        var contentUri: Uri? = null

        //Obtenemos el bitmap desde el uri
        try{
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){
                val source = ImageDecoder.createSource(contentResolver, imageUri!!)
                bitmap = ImageDecoder.decodeBitmap(source)
            }else{
                bitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
            }
        }catch(e: Exception){
            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
        }

        val imagesFolder = File(cacheDir, "images")

        try{
            imagesFolder.mkdirs() //creamos el directorio si no existe. Se usa la función en plural para que cree los directorio padres si se requiriera
            val file = File(imagesFolder, "imagen_comprimida.jpg") //Se le da el nombre a la imagen
            val stream = FileOutputStream(file)
            bitmap?.compress(Bitmap.CompressFormat.JPEG, 50, stream)
            stream.flush()
            stream.close()
            contentUri = FileProvider.getUriForFile(this, "com.amaurypm.intents.fileprovider", file)
        }catch (e: Exception){
            Toast.makeText(this, "Error ${e.message}", Toast.LENGTH_SHORT).show()
        }

        return contentUri
    }


}