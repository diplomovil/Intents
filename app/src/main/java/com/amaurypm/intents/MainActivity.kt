package com.amaurypm.intents

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun click(view: View) {
        //Toast.makeText(this, "Se hizo click en el botón", Toast.LENGTH_SHORT).show()

        //Intent con un URL
        /*var uri = Uri.parse("https://www.tic.unam.mx")
        startActivity(Intent(Intent.ACTION_VIEW, uri))*/

        //Con un teléfono  Click-to-call
        /*var uri = Uri.parse("tel:5556581111")
        startActivity(Intent(Intent.ACTION_DIAL, uri))*/

        //Una localización
        /*var uri = Uri.parse("geo:0,0?q=FI+UNAM")
        startActivity(Intent(Intent.ACTION_VIEW, uri))*/

        //Un SMS (Click-to-sms)
        /*val intent = Intent(Intent.ACTION_SENDTO).apply{
            data = Uri.parse("smsto:5556581111")
            putExtra("sms_body", "Hola, me gustaría obtener más información del producto")
        }

        if(intent.resolveActivity(packageManager)!=null){
            startActivity(intent)
        }else{
            Toast.makeText(this, "No hay aplicación compatible", Toast.LENGTH_SHORT).show()
        }*/

        //click to mail
        //correos: amaury@comunidad.unam.mx y moviles.diplomado@gmail.com
        //asunto: Prueba diplo
        //body: "Esto es una prueba para participación del diplomado de móviles"

        val emails = arrayOf("amaury@comunidad.unam.mx", "moviles.diplomado@gmail.com")

        val intent = Intent(Intent.ACTION_SENDTO).apply {
            type = "*/*"
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, emails)
            putExtra(Intent.EXTRA_SUBJECT, "Prueba diplo")
            putExtra(Intent.EXTRA_TEXT, "Esto es una probate")
        }

        if(intent.resolveActivity(packageManager)!=null){
            startActivity(intent)
        }else{
            Toast.makeText(this, "No hay aplicación compatible", Toast.LENGTH_SHORT).show()
        }


    }
}