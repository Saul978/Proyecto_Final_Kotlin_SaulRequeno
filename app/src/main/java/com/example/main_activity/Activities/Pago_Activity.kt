package com.example.main_activity.Activities

import android.app.NotificationManager
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.main_activity.Entities.carrito
import com.example.main_activity.Entities.pedido
import com.example.main_activity.Entities.producto
import com.example.main_activity.Fragments.Fragment_shopping_cart
import com.example.main_activity.Fragments.ProductoItem
import com.example.main_activity.R
import com.example.main_activity.Utiles.Utiles
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.activity_pago.*
import kotlinx.android.synthetic.main.fullscreen_dialog.*
import java.lang.StringBuilder
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*
import kotlin.math.round
import kotlin.math.truncate
import kotlinx.android.synthetic.main.fullscreen_dialog.toolbar as toolbar1

class Pago_Activity() : AppCompatActivity() {

    val df = DecimalFormat("#.##")
    var total=0.0
    val uid = Utiles(this).usuario_Actual()
    val list = arrayListOf<carrito>()
    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_pago)
        super.onCreate(savedInstanceState)
        total()

        toolbar.setNavigationOnClickListener {finish() }
        credito.cardRequired(true)
            .expirationRequired(true)
            .cvvRequired(true)
            .actionLabel("Compra")
            .setup(this)

        Boton_Confirmar_Pago.setOnClickListener {
            if (Et_Pagar_Direccion.text.toString().trim().isEmpty()){
                Pagar_Direccion.error="La direccion no puede estar vacia"
            }else{
                Pagar_Direccion.error=null
                if (credito.isValid){
                    val id = UUID.randomUUID().toString()
                        val  format =  SimpleDateFormat("EEEE, MMMM d, yyyy 'at' h:mm a")
                        var ref =FirebaseDatabase.getInstance().getReference("/users/$uid").child("/pedidos/$id")
                        var refcarrito =FirebaseDatabase.getInstance().getReference("/users/$uid").child("/carrito")

                        var pedido = pedido(id, format.format(Calendar.getInstance().time),df.format(total).replace(",",".").toDouble(),list,Et_Pagar_Direccion.text.toString())
                        ref.setValue(pedido)
                        refcarrito.removeValue()
                        mandarNot(pedido)

                    val intent = Intent(layoutInflater.context, MainActivity::class.java)
                    startActivity(intent)


                }else{
                    Toast.makeText(this,"Rellena todos los campos correctamente", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }
    fun total(){
        var ref=FirebaseDatabase.getInstance().getReference("/users/${uid}").child("/carrito")
        ref.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {

                p0.children.forEach{
                    val Carrito= it.getValue(carrito::class.java)
                    Carrito?.key= it?.key!!

                    if (Carrito!=null){

                        total += Carrito.cantidad.toDouble() * Carrito.producto!!.precio.substring(
                            0,
                            Carrito.producto!!.precio.length - 1
                        ).toDouble()
                        list?.add(Carrito)
                    }
                }

                Total.append(" "+df.format(total)+"€")
            }



            override fun onCancelled(p0: DatabaseError) {
            }
        })

    }
    public fun mandarNot(Pedido:pedido){
        var productos= StringBuilder()
        Pedido.carrito!!.forEach {
        productos.append( it.producto!!.nombre+" x "+it.cantidad +"\n")
        }
        var notif= NotificationCompat.Builder(this,Notificaciones.canal())
            .setSmallIcon(R.mipmap.ic_launcher)
            .setLargeIcon(BitmapFactory.decodeResource(this.getResources(),
                R.mipmap.ic_launcher))
            .setContentTitle("Gracias por la compra")
            .setStyle( NotificationCompat.BigTextStyle()
                     .bigText("Total: "+Pedido.total+"€"+"\n" +productos)
                ).build()
        NotificationManagerCompat.from(this).notify(1,notif)

    }
}
