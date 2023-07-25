package com.example.proyecto2

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.proyecto2.R.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.lang.Error

class Login : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var authStateListener: FirebaseAuth.AuthStateListener
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        firebaseAuth = Firebase.auth
        // Inicializar variable
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_login)
        val txtemail:TextView = findViewById(id.Email)
        val txtContraseña:TextView = findViewById(id.LContraseña)
        val btnIngresar : Button = findViewById(id.Btn_Login)
        val BtnCrear: Button = findViewById(id.Btn_Registrarse)
        val BtnRecordar : TextView = findViewById(id.Btn_Recordar)

        btnIngresar.setOnClickListener {
            // Enviar parametros
            // Se llama a la funcion de iniciar
            Iniciar(txtemail.text.toString(),txtContraseña.text.toString())
        }

        // Boton para registrarse
        BtnCrear.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // Boton para restablecer la contraseña
        BtnRecordar.setOnClickListener {
            val R = Intent (this,RecordarPassActivity::class.java)
            startActivity(R)
        }

    }

    private fun Iniciar(email:String, Contraseña:String) {
        // ir a firebase a verificar los datos email y contraseña
        firebaseAuth.signInWithEmailAndPassword(email,Contraseña).addOnCompleteListener (this){task ->

                if (task.isSuccessful){
                    // task.isSuccessful :  Devuelve true si la tarea se completó con éxito false de lo contrario.
                    Verif_Email()
                }
                else{
                    DatosNo()
                }
        }
    }
    private fun Verif_Email() {
        val User = firebaseAuth.currentUser
        // puedes usar la propiedad currentUser para obtener el usuario que accedió.
        // Si no accedió ningún usuario, el valor de currentUser es nulo
        val verifica = User?.isEmailVerified
        // isEmailVerifiel = verifica si el usuario ya ingreso al correo y confirmo devuelve verdadero o falso
        if (verifica == true){
            
            Toast.makeText(baseContext, "Cuenta Verificada Con Exito", Toast.LENGTH_SHORT).show()
            DatosSi()
        }else{
            Toast.makeText(baseContext, "Error: Su correo no ha sido verificado", Toast.LENGTH_SHORT).show()
            
        }
    }

    private fun DatosSi() {
    val User = firebaseAuth.currentUser
        //  la propiedad currentUser para verificar el usuario que inicio sesion
        val intentB:Intent = Intent(this,ConfirmarDatos::class.java)
        startActivity(intentB)
    }
    private fun DatosNo() {
        Toast.makeText(baseContext,"Error de Email y/o Contraseña", Toast.LENGTH_SHORT).show()
    }
}