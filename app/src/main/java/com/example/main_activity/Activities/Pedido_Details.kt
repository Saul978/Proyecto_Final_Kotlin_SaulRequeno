package com.example.main_activity.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.main_activity.Entities.carrito
import com.example.main_activity.Entities.pedido
import com.example.main_activity.Entities.producto
import com.example.main_activity.R
import com.example.main_activity.Utiles.Utiles
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_pedido_details.*
import kotlinx.android.synthetic.main.activity_product__details.*
import kotlinx.android.synthetic.main.cardview_pedidos.view.*
import kotlinx.android.synthetic.main.cardview_pedidos_details.view.*

class Pedido_Details : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pedido_details)

        val pedido = intent.getSerializableExtra("pedido") as? pedido
        if (pedido != null) {
            supportActionBar?.title="Informacion pedido"
            cargar(Recycler_View_Detalles_pedido,pedido)

        }
    }
    private fun cargar(recycler: RecyclerView,Pedido: pedido){
                val adapter = GroupAdapter<GroupieViewHolder>()
                Pedido.carrito!!.forEach{

                    if (it!=null){
                        adapter.add(PedidoItem(it))
                    }
                }

                recycler.adapter=adapter



    }

    class PedidoItem(val Carrito: carrito): Item<GroupieViewHolder>(){
        override fun bind(viewHolder: GroupieViewHolder, position: Int) {
            Picasso.get().load(Carrito.producto!!.imagen).into(viewHolder.itemView.Pedido_details_Imagen)
            viewHolder.itemView.Pedido_details_Nombre.text= Carrito.producto?.nombre
            viewHolder.itemView.Pedido_details_Cantidad.text= Carrito.cantidad.toString()
            viewHolder.itemView.Pedido_details_Precio.text= Carrito.producto?.precio



        }
        override fun getLayout(): Int {
            return R.layout.cardview_pedidos_details
        }

    }
}
