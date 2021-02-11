package com.example.main_activity.Utiles

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.braintreepayments.cardform.view.CardForm
import com.example.main_activity.Entities.carrito
import com.example.main_activity.Entities.producto
import com.example.main_activity.R
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import java.util.*


class Utiles(val context:Context){

    fun mostrarCantidad(producto:producto){
        val bottomSheetDialog = BottomSheetDialog(context)
        val view = LayoutInflater.from(context).inflate(R.layout.bottom_sheet_dialog,null)
        bottomSheetDialog.setContentView(view)
        bottomSheetDialog.show()
         view.findViewById<Button>(R.id.Boton_Aceptar).setOnClickListener {
             comprobarCarrito(producto,view.findViewById<Spinner>(R.id.Spinner_cantidad).selectedItem.toString().toInt())
             bottomSheetDialog.cancel()
         }
    }


    fun comprobarCarrito(productoComprar:producto, cantidad:Int){
        val uid = FirebaseAuth.getInstance().uid ?:""
        var ref=FirebaseDatabase.getInstance().getReference("/users/$uid").child("/carrito")
        ref.addListenerForSingleValueEvent(object:ValueEventListener{

            override fun onDataChange(p0: DataSnapshot) {
                if (!p0.hasChildren()){
                    ref=ref.push()
                    val carrito = carrito(cantidad,productoComprar)
                    ref.setValue(carrito)

                }else{
                    var bool :Boolean = false
                    p0.children.forEach{

                        var ProductoCarrito = it.child("producto").getValue(producto::class.java)
                        var ProductoCarritoKey = it.key


                        if (ProductoCarrito != null && ProductoCarrito.id.equals(productoComprar.id)) {
                            bool =true
                            cambiarCantidad(cantidad, ProductoCarritoKey!!)
                        }

                    }
                    if (!bool){
                            ref= ref.push()
                            Toast.makeText(context,"EL producto no esta introducido",Toast.LENGTH_SHORT).show()
                            val carrito = carrito(cantidad,productoComprar)
                            ref.setValue(carrito)

                    }
                }



            }

            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(context,"Error al añadir item al carrito",Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun cambiarCantidad(cantidad:Int,key:String){
        val uid = FirebaseAuth.getInstance().uid ?:""
        FirebaseDatabase.getInstance().getReference("/users/$uid").child("/carrito")
            .child(key).child("cantidad").setValue(cantidad)
    }

    fun cerrarSesion(){
        FirebaseAuth.getInstance().signOut()
    }
    fun ValidarCorreo(){
        val user = FirebaseAuth.getInstance().currentUser
            if (user!!.isEmailVerified) {
                Toast.makeText(context, "Su cuenta esta ya validada \ud83d\ude03 ", Toast.LENGTH_SHORT).show()

            } else {
                user.sendEmailVerification().addOnCompleteListener{
                    Toast.makeText(context, "Revisa tu correo electronico", Toast.LENGTH_SHORT).show()

                }

            }

    }

    fun Add_Producto(uri:Uri?,Producto: producto){
        val nombre= UUID.randomUUID().toString()
        val refoto = FirebaseStorage.getInstance().getReference(nombre)
        val refProducto = FirebaseDatabase.getInstance().getReference("/productos/${Producto.id}")
        if (uri != null) {
            refoto.putFile(uri).addOnSuccessListener {
                refoto.downloadUrl.addOnSuccessListener {
                    Producto.imagen=it.toString()
                    Log.d("imagen",it.toString())
                    refProducto.setValue(Producto)
                }
            }

        }


    }


    fun usuario_Actual():String{
        val uid= FirebaseAuth.getInstance().uid?:""
        return uid
    }


}