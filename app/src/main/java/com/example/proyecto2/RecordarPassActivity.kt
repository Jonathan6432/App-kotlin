package com.example.proyecto2

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RecordarPassActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    // Variable que tiene todos los metodos de firebase
    private lateinit var authStateListener: FirebaseAuth.AuthStateListener

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        firebaseAuth = Firebase.auth
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recordar_pass)

        val BtnRecordarC : Button = findViewById(R.id.Btn_Enviar)
        val BtnAtras : Button = findViewById(R.id.Btn_Atras)
        val txtEmail2 : TextView = findViewById(R.id.txtEmail2)


        BtnRecordarC.setOnClickListener {
            EnviarCorreo(txtEmail2.text.toString())
        }

        BtnAtras.setOnClickListener {
            val A = Intent (this,Login::class.java)
            startActivity(A)
        }

    }

    private fun EnviarCorreo(email:String) {
        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener() { task->
            // Usamos metodo de firebase para enviar correo de verificacion
            if (task.isSuccessful){
                Toast.makeText(baseContext, "Correo de cambio de contraseña enviado", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(baseContext, "Error : no se pudo completar el proceso", Toast.LENGTH_SHORT).show()
            }

        }

    }
}