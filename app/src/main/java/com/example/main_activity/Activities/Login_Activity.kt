package com.example.main_activity.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.main_activity.Entities.User
import com.example.main_activity.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import java.util.regex.Pattern
import com.google.firebase.auth.FirebaseUser



class Login_Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        SesionAnterior()
        Registrate.setOnClickListener{
            val intent = Intent(this,Register_Activity::class.java)
            startActivity(intent)
        }

        Iniciar_Sesion.setOnClickListener{
            IniciarSesion()
        }
    }

    private fun IniciarSesion(){
        if (validar()){

            FirebaseAuth.getInstance().signInWithEmailAndPassword(EtEmail_Login.text.toString().trim(),EtPassword_Login.text.toString().trim())
                .addOnCompleteListener{
                    if (!it.isSuccessful)return@addOnCompleteListener

                    intent = Intent(this,MainActivity::class.java)
                    intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
                .addOnFailureListener{
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }


        }
    }


    private fun validar():Boolean{
        if(!validarEmail()or !validarPassword()){
            return false
        }
        return true
    }
    private fun validarEmail():Boolean{
        val ETemail= EtEmail_Login.text.toString().trim()
        if (ETemail.isEmpty()){
            Email_Login.error="El email no puede estar vacio"
            return false
        }else{
            Email_Login.error=null
            return true
        }
    }

    private fun validarPassword():Boolean{
        val password= EtPassword_Login.text.toString().trim()
        if (password.isEmpty()){
            Password_Login.error="La contraseña no puede estar vacia"
            return false
        } else{
            Password_Login.error=null
            return true
        }
    }
    private fun SesionAnterior(){
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            val i = Intent(this, MainActivity::class.java)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(i)
        } else {
        }
    }
}
