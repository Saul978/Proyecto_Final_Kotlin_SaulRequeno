package com.example.main_activity.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.main_activity.Entities.User
import com.example.main_activity.R
import com.example.main_activity.Utiles.Utiles
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.Email
import kotlinx.android.synthetic.main.activity_register.Password
import java.util.regex.Pattern



class Register_Activity : AppCompatActivity() {
    private val PASSWORD_PATTERN = Pattern.compile(
        "^" +
                "(?=.*[0-9])" +         //al menos un digito
                "(?=.*[a-z])" +         //al menos una letra  minuscula
                "(?=.*[A-Z])" +         //al menos una letra  mayuscula
                "(?=.*[a-zA-Z])" +      //cualquier letra
                "(?=\\S+$)" +           //no espacios en blanco
                ".{4,}" +               //como minimo 4 caracteres

                "$"
    )
    private val EMAIL_ADDRESS_PATTERN = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
        "\\@" +
        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
        "(" +
        "\\." +
        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
        ")+"
)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        button.setOnClickListener{
            Registrar()
        }
        Ya_tienes_cuenta.setOnClickListener{
        val intent = Intent(this,Login_Activity::class.java)
            startActivity(intent)
        }
    }

    private fun Registrar(){
        if (validar()){

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(EtEmail.text.toString().trim(),EtPassword.text.toString().trim())
            .addOnCompleteListener{
                if (!it.isSuccessful)return@addOnCompleteListener
                guardarDatos()
            }
            .addOnFailureListener{
                Toast.makeText(this, it.message,Toast.LENGTH_SHORT).show()
            }


        }
    }

    private fun guardarDatos(){
        val uid = Utiles(this).usuario_Actual()
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")
        val User= User(uid,EtUsuario.text.toString().trim(),false)

        ref.setValue(User)
            .addOnSuccessListener {
                 intent = Intent(this,MainActivity::class.java)
                intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                 startActivity(intent)
            }
    }
    private fun validar():Boolean{
        if(!validarEmail()or !validarUsuario()or !validarPassword()){
            return false
        }
        return true
    }
    private fun validarEmail():Boolean{
        val ETemail= EtEmail.text.toString().trim()
        if (ETemail.isEmpty()){
            Email.error="El email no puede estar vacio"
            return false
        }else if (!EMAIL_ADDRESS_PATTERN.matcher(ETemail).matches()){
            Email.error="Forma un email valido"
            return false
        }else{
            Email.error=null
            return true
        }
    }

    private fun validarUsuario():Boolean{
        val usuario = EtUsuario.text.toString().trim()
        if (usuario.isEmpty()) {
            return false
        }
            return true

    }


    private fun validarPassword():Boolean{
        val password= EtPassword.text.toString().trim()
        if (password.isEmpty()){
            Password.error="La contraseña no puede estar vacia"
            return false
        } else if (!PASSWORD_PATTERN.matcher(password).matches()) {
            Password.error="1 Letra mayuscula,1 minuscula,1 numero y 4 caracteres minimo"
            return false
        }else{
            Password.error=null
            return true
        }
    }


}
