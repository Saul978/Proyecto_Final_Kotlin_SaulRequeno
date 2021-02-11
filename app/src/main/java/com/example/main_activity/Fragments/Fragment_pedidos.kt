package com.example.main_activity.Fragments


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.main_activity.Activities.Pedido_Details
import com.example.main_activity.Activities.Product_Details
import com.example.main_activity.Dialogos.Dialogo_Busqueda
import com.example.main_activity.Entities.pedido
import com.example.main_activity.Entities.producto

import com.example.main_activity.R
import com.example.main_activity.Utiles.Utiles
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.cardview.view.*
import kotlinx.android.synthetic.main.cardview_pedidos.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class Fragment_pedidos : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_pedidos, container, false)
        val recycler =view.findViewById<RecyclerView>(R.id.Pedido_RecyclerView)
        cargar(recycler)
        return view
    }

    private fun cargar(recycler: RecyclerView){
        val uid = FirebaseAuth.getInstance().uid ?:""
        val ref= FirebaseDatabase.getInstance().getReference("/users/$uid").child("pedidos")
        ref.addListenerForSingleValueEvent(object: ValueEventListener {

            override fun onDataChange(p0: DataSnapshot) {
                val adapter = GroupAdapter<GroupieViewHolder>()
                p0.children.forEach{
                    val Pedido= it.getValue(pedido::class.java)
                    if (Pedido!=null){
                        adapter.add(PedidoItem(Pedido))
                    }
                }
                adapter.setOnItemClickListener{item,view->

                    val PedidoIntent = item as  PedidoItem
                    val intent = Intent(view.context, Pedido_Details::class.java)
                    intent.putExtra("pedido",PedidoIntent.Pedidoget())
                    view.context.startActivity(intent)
                }
                recycler.adapter=adapter
            }

            override fun onCancelled(p0: DatabaseError) {
            }
        })
    }

    class PedidoItem(val Pedido: pedido): Item<GroupieViewHolder>(){
        override fun bind(viewHolder: GroupieViewHolder, position: Int) {
            viewHolder.itemView.Pedido_Precio.append(" "+Pedido.total.toString()+"€")
            viewHolder.itemView.Pedido_Date.append(" "+Pedido.fecha)
            viewHolder.itemView.Pedido_Num.append( Pedido.carrito!!.size.toString())


        }
        fun Pedidoget(): pedido {
            return Pedido
        }
        override fun getLayout(): Int {
            return R.layout.cardview_pedidos
        }

    }
}
