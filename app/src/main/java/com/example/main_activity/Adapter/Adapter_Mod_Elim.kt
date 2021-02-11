package com.example.main_activity.Adapter

import android.content.Context
import android.content.Intent
import android.media.Image
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.view.menu.MenuView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.main_activity.Activities.Modificar_Activity
import com.example.main_activity.Activities.Pago_Activity
import com.example.main_activity.Activities.Product_Details
import com.example.main_activity.Dialogos.Dialogo_Add
import com.example.main_activity.Entities.producto
import com.example.main_activity.Fragments.ProductoItem
import com.example.main_activity.Fragments.ref
import com.example.main_activity.Fragments.uid
import com.example.main_activity.R
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.cardview_busqueda.view.*


class Adapter_Mod_Elim (internal var context: Context, internal var itemList:ArrayList<producto>):
    RecyclerView.Adapter<Adapter_Mod_Elim.ViewHolder>() {

     val Lista: ArrayList<producto> = itemList

    class ViewHolder(itemView: View,Lista :ArrayList<producto>):RecyclerView.ViewHolder(itemView),View.OnCreateContextMenuListener {
        internal var Lista = Lista
        internal var Busqueda_Imagen:ImageView
        internal var Busqueda_Producto:TextView
        internal var Busqueda_Precio:TextView
        init {
            Busqueda_Imagen=itemView.findViewById<ImageView>(R.id.Busqueda_Imagen)
            Busqueda_Producto=itemView.findViewById<TextView>(com.example.main_activity.R.id.Busqueda_Producto)
            Busqueda_Precio=itemView.findViewById<TextView>(com.example.main_activity.R.id.Busqueda_Precio)
            itemView.setOnCreateContextMenuListener(this)
        }

        override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
            var Borrar =menu!!.add(this.adapterPosition,R.id.Borrar,1,"Borrar")
            var Modificar= menu!!.add(this.adapterPosition,R.id.Modificar,2,"Modificar")


            Borrar.setOnMenuItemClickListener(MenuItem.OnMenuItemClickListener {
                var ref= FirebaseDatabase.getInstance().getReference("productos")
                ref.child(Lista.get(this.adapterPosition).id).removeValue()
                Toast.makeText(itemView.context,"Producto borrado",Toast.LENGTH_SHORT).show()

                true
            })


            Modificar.setOnMenuItemClickListener(MenuItem.OnMenuItemClickListener {

                Log.d("contextmenu","Modificar")

               val intent  = Intent(itemView.context,Modificar_Activity::class.java)
                intent.putExtra("producto",Lista.get(this.adapterPosition))
                itemView.context.startActivity(intent)
                true
            })
        }
    }



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val Producto:producto = Lista.get(position)
        holder.Busqueda_Precio.text = Producto.precio
        holder.Busqueda_Producto.text = Producto.nombre
        Picasso.get().load(Producto.imagen)
            .into(holder.Busqueda_Imagen)


    }

    override fun getItemCount(): Int {
        return Lista.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView:View = LayoutInflater.from(context).inflate(R.layout.cardview_busqueda,parent,false)

        return ViewHolder(itemView,Lista)
    }




}