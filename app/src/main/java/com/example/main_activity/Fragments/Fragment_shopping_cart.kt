package com.example.main_activity.Fragments


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.main_activity.Activities.Pago_Activity
import com.example.main_activity.Entities.carrito

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
import kotlinx.android.synthetic.main.cardiew_carrito.view.*
import kotlinx.android.synthetic.main.fragment_shopping_cart.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
val uid = FirebaseAuth.getInstance().uid ?:""
var ref=FirebaseDatabase.getInstance().getReference("/users/$uid").child("/carrito")
class Fragment_shopping_cart : Fragment() {
    companion object{
        fun newInstance(): Fragment_shopping_cart=Fragment_shopping_cart()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view = inflater.inflate(R.layout.fragment_shopping_cart, container, false)
        val recycler =view.findViewById<RecyclerView>(R.id.Carrito_RecyclerView)
        cargar(recycler)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Pagar_Button.setOnClickListener {
            if (Carrito_RecyclerView.adapter!!.itemCount==0){
                Toast.makeText(context,"Todavia no tienes productos",Toast.LENGTH_SHORT).show()
            }else{

                val intent = Intent(context, Pago_Activity::class.java)
                startActivity(intent)
            }
        }
    }
    private fun cargar(recycler: RecyclerView){
        ref.addListenerForSingleValueEvent(object: ValueEventListener {

            override fun onDataChange(p0: DataSnapshot) {
                val adapter = GroupAdapter<GroupieViewHolder>()
                p0.children.forEach{
                    val Carrito= it.getValue(carrito::class.java)
                    Carrito?.key= it?.key!!
                    if (Carrito!=null){
                        adapter.add(CarritoItem(Carrito,adapter))
                    }
                }
                recycler.adapter=adapter
            }

            override fun onCancelled(p0: DatabaseError) {
            }
        })
    }

    class CarritoItem(var carrito: carrito,var adaptere: GroupAdapter<GroupieViewHolder>): Item<GroupieViewHolder>(){

        override fun bind(viewHolder: GroupieViewHolder, position: Int) {
            viewHolder.itemView.Carrito_Producto.text=carrito.producto?.nombre
            Picasso.get().load(carrito.producto?.imagen).into(viewHolder.itemView.Carrito_Imagen)
            viewHolder.itemView.Carrito_Precio.text=carrito.producto?.precio
           val spinner =  viewHolder.itemView.findViewById<Spinner>(R.id.Carrito_Spinner)
            spinner.setSelection(carrito.cantidad-1)
            spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    Utiles(view!!.context).cambiarCantidad(spinner.selectedItem.toString().toInt(),carrito.key)
                }

            }

            viewHolder.itemView.Carrito_Delete.setOnClickListener{
                adaptere.remove(viewHolder.item)
                ref.child(carrito.key).removeValue()
                Toast.makeText(it.context,"Producto borrado",Toast.LENGTH_SHORT).show()

            }
        }


        fun Carritoget(): carrito {
            return carrito
        }
        override fun getLayout(): Int {
            return R.layout.cardiew_carrito
        }

    }

}
