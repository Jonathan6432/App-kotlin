package com.example.proyecto2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.lang.Error

class MainActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        firebaseAuth = Firebase.auth
        // Inicializar variable


        val txtNombres = findViewById<EditText>(R.id.Nombres)
        val txtApellidos = findViewById<EditText>(R.id.Apellidos)
        val txtEmail = findViewById<EditText>(R.id.EmailNuevo)
        val txtContraseña1 = findViewById<EditText>(R.id.ContraseñaNueva)
        val txtContraseña2 = findViewById<EditText>(R.id.Conf_Contraseña)
        val BtnCrear = findViewById<Button>(R.id.Btn_Resgistro)
        val BtnIniciar = findViewById<TextView>(R.id.Btn_Login)

        // Boton para iniciar sesion

        BtnIniciar.setOnClickListener {
            val I = Intent(this,Login::class.java)
         startActivity(I)
        }

        BtnCrear.setOnClickListener {
            var Cont1 = txtContraseña1.text.toString()
            var Cont2 = txtContraseña2.text.toString()
            if (Cont1.equals(Cont2)) {
                // Si las dos contraseñas son iguales  se ejecuta esta funcion
                CrearCuenta(txtEmail.text.toString(), txtContraseña2.text.toString())

            } else {
                Toast.makeText(baseContext, "Error: La contraseña no coinciden", Toast.LENGTH_SHORT).show()
                txtContraseña1.requestFocus()
            }
        }
    }

    private fun CrearCuenta(email: String, ContraseñaN: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, ContraseñaN)
                // Para crear un usuario nuevo en tu proyecto de Firebase, llama al método createUserWithEmailAndPassword
            .addOnCompleteListener(this) { task ->

                if (task.isSuccessful) {
                    VerificacionEmail()
                    // Se llama a la funcion que contiene el metodo para enviar mensaje de verificacion
                    Toast.makeText(baseContext, "Para iniciar sesiòn se requiere verificacion del correo electronico", Toast.LENGTH_SHORT).show()
                    // Se muestra el mensaje y se envia la funcion de ir a la pagina de login
                    GoLogin()

                } else {
                    // Si no se cumple la validacion de correo y contraseña que sean iguales se envia mensaje de error
                    Toast.makeText(baseContext, "Error: Algo Salio mal " + task.exception, Toast.LENGTH_SHORT).show()
                    // exception:  Devuelve la excepción que provocó el error de la tarea.
                }
            }
    }

    private fun GoLogin() {
        val i : Intent = Intent(this,Login::class.java)
        startActivity(i)
    }

    private fun VerificacionEmail(){
        val user = firebaseAuth.currentUser!!
        user.sendEmailVerification()
        // Para enviarle un mensaje de verificación de dirección a un usuario, puedes usar el método sendEmailVerification.

        }
    }
