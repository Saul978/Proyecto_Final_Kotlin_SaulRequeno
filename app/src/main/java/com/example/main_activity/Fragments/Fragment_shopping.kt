package com.example.main_activity.Fragments


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.main_activity.Activities.Product_Details
import com.example.main_activity.Dialogos.Dialogo_Busqueda
import com.example.main_activity.Entities.producto

import com.example.main_activity.R
import com.example.main_activity.Utiles.Utiles
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.cardview.view.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
/**
 * A simple [Fragment] subclass.
 *
 */

class Fragment_shopping : Fragment() {
    companion object{
        fun newInstance(): Fragment_shopping=Fragment_shopping();
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_fragment_shopping, container, false)
        val recycler =view.findViewById<RecyclerView>(R.id.Recycler_View)
        val floating = view.findViewById<FloatingActionButton>(R.id.Boton_Busqueda)
        floating.setOnClickListener {
            val Dialogo_Busqueda = Dialogo_Busqueda()
            Dialogo_Busqueda.show(parentFragmentManager,"d")
        }
        cargar(recycler)
        return view
    }


    }

private fun cargar(recycler:RecyclerView){
    val ref=FirebaseDatabase.getInstance().getReference("/productos")
    ref.addListenerForSingleValueEvent(object:ValueEventListener{

        override fun onDataChange(p0: DataSnapshot) {
            val adapter = GroupAdapter<GroupieViewHolder>()
                p0.children.forEach{
                    val Producto= it.getValue(producto::class.java)
                    if (Producto!=null){
                        adapter.add(ProductoItem(Producto))
                    }
                }

            adapter.setOnItemClickListener{item,view->

                val Producto_Intent = item as  ProductoItem
                val intent = Intent(view.context,Product_Details::class.java)
                intent.putExtra("producto",Producto_Intent.Prodcutoget())
                view.context.startActivity(intent)
            }
            recycler.adapter=adapter
        }

        override fun onCancelled(p0: DatabaseError) {
        }
    })
}

class ProductoItem(val Producto:producto):Item<GroupieViewHolder>(){
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.Producto_nombre.text=Producto.nombre
        Picasso.get().load(Producto.imagen).resize(0,750).onlyScaleDown().into(viewHolder.itemView.Producto_imagen)

        val Producto_precio= viewHolder.itemView.Producto_precio
            Producto_precio.text=Producto.precio
            Producto_precio.setOnClickListener {
              Utiles(it.context).mostrarCantidad(Producto)
            }


    }
     fun Prodcutoget():producto{
        return Producto
    }
    override fun getLayout(): Int {
    return R.layout.cardview
    }

}



