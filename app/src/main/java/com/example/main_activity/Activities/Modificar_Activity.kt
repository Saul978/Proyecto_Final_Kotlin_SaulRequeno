package com.example .main_activity.Activities

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import com.example.main_activity.Entities.producto
import com.example.main_activity.R
import com.example.main_activity.Utiles.Utiles
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_modificar.*
import kotlinx.android.synthetic.main.activity_product__details.*

import kotlinx.android.synthetic.main.bottom_sheet_dialog.*
import kotlinx.android.synthetic.main.fullscreen_dialog.*
import kotlinx.android.synthetic.main.fullscreen_dialog_add.*
import kotlinx.android.synthetic.main.fullscreen_dialog_add.EtAdd_Marca
import java.util.*

class Modificar_Activity : AppCompatActivity() {
    var uriimagen : Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modificar)
        val producto = intent.getSerializableExtra("producto") as? producto
        if (producto != null) {
            EtMod_Precio.setText(producto.precio.substring(0,producto.precio.length-1))
            EtMod_Categoria.setText(producto.categoria)
            EtMod_Descripcion.setText(producto.descripcion)
            EtMod_Marca.setText(producto.marca)
            EtMod_Nombre.setText(producto.nombre)
            Picasso.get().load(producto.imagen).into(Mod_Imagen)
        }
        toolbar_mod.setNavigationOnClickListener { finish()}
        toolbar_mod.inflateMenu(R.menu.menu_add)
        toolbar_mod.menu.findItem(R.id.action_add).setOnMenuItemClickListener {
            if (validar()){
                producto?.nombre =EtMod_Nombre.text?.trim().toString()
                producto?.categoria =EtMod_Categoria.text?.trim().toString()
                producto?.precio =EtMod_Precio.text?.trim().toString()+"€"
                producto?.marca =EtMod_Marca.text?.trim().toString()
                producto?.descripcion =EtMod_Descripcion.text?.trim().toString()

                FirebaseDatabase.getInstance().getReference("/productos").child(producto!!.id).setValue(producto)

                Utiles(applicationContext).Add_Producto(uriimagen, producto )
                finish()
            }else{

            }

            true
        }



        Mod_Imagen.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type="image/*"
            startActivityForResult(intent,0)
        }

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==0&&resultCode== Activity.RESULT_OK && data!=null){
            uriimagen =data.data
            Mod_Imagen.background=null
            Picasso.get().load(uriimagen).into(Mod_Imagen)

        }
    }
    fun validar():Boolean{
        if(!validarDescripcion()or
            !validarCategoria()or
            !validarMarca()or
            !validarPrecio()or
            !validarNombre()){
            return false
        }else{
            return true
        }
    }

    fun validarNombre():Boolean{
        if (EtMod_Nombre.text.toString().trim().isEmpty()){
            Mod_Nombre.error="El nombre no puede estar vacio"
            return false
        }else{
            Mod_Nombre.error=null
            return true
        }

    }

    fun validarCategoria():Boolean{
        if (EtMod_Categoria.text.toString().trim().isEmpty()){
            Mod_Categoria.error="La categoria no puede estar vacia"
            return false
        }else{
            Mod_Categoria.error=null
            return true
        }

    }
    fun validarPrecio():Boolean{
        if (EtMod_Precio.text.toString().trim().isEmpty()){
            Mod_Precio.error="El precio no puede estar vacio"
            return false
        }else{
            Mod_Precio.error=null
            return true
        }

    }
    fun validarDescripcion():Boolean{
        if (EtMod_Descripcion.text.toString().trim().isEmpty()){
            Mod_Descripcion.error="La descripcion no puede estar vacia"
            return false
        }else{
            Mod_Descripcion.error=null
            return true
        }

    }
    fun validarMarca():Boolean{
        if (EtMod_Marca.text.toString().trim().isEmpty()){
            Mod_Marca.error="La marca no puede estar vacia"
            return false
        }else{
            Mod_Marca.error=null
            return true
        }

    }
}
