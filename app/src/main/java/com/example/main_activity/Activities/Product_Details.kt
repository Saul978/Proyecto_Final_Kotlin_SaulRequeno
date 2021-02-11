package com.example.main_activity.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.main_activity.Entities.producto
import com.example.main_activity.R
import com.example.main_activity.Utiles.Utiles
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_product__details.*

class Product_Details : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product__details)
        val producto = intent.getSerializableExtra("producto") as? producto
        if (producto != null) {
            supportActionBar?.title=producto.nombre
            Nombre_Descripcion.text=producto.nombre
            Marca_Descripcion.text=producto.marca
            Precio_Descripcion.text=producto.precio

            Picasso.get().load(producto.imagen).into(Imagen_Descripcion)
            Descripcion_Descripcion.text=producto.descripcion

            Precio_Descripcion.setOnClickListener {
                Utiles(this).mostrarCantidad(producto)
            }
        }
    }
}
