package com.example.main_activity.Adapter

import android.content.Context
import android.content.Intent
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.main_activity.Activities.Product_Details
import com.example.main_activity.Entities.producto
import com.example.main_activity.Fragments.ProductoItem
import com.example.main_activity.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.cardview_busqueda.view.*


class Adapter_Busqueda (internal var context: Context,internal var itemList:ArrayList<producto>):
    RecyclerView.Adapter<Adapter_Busqueda.ViewHolder>(),Filterable  {

    private val exampleList: ArrayList<producto> = itemList
    private val exampleListFull: ArrayList<producto> = ArrayList(itemList)

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        internal var Busqueda_Imagen:ImageView
        internal var Busqueda_Producto:TextView
        internal var Busqueda_Precio:TextView
        init {
            Busqueda_Imagen=itemView.findViewById<ImageView>(R.id.Busqueda_Imagen)
            Busqueda_Producto=itemView.findViewById<TextView>(com.example.main_activity.R.id.Busqueda_Producto)
            Busqueda_Precio=itemView.findViewById<TextView>(com.example.main_activity.R.id.Busqueda_Precio)

        }
    }

    private val filtro = object : Filter() {
        override fun performFiltering(constraint: CharSequence): FilterResults? {
            val filtrado:ArrayList<producto> = ArrayList()
            if (constraint==null|| constraint.length==0){
                filtrado.addAll(exampleListFull)
            }else {
                val filterPattern = constraint.toString().toLowerCase().trim()

                exampleListFull.forEach(){
                    if (it.nombre.toLowerCase().contains(filterPattern)||it.categoria.toLowerCase().contains(filterPattern)||it.marca.toLowerCase().contains(filterPattern)){
                        filtrado.add(it)
                    }
                }
            }
            val resultados= FilterResults()
            resultados.values= filtrado
            return resultados
        }

        override fun publishResults(constraint: CharSequence, results: FilterResults) {
            exampleList.clear()
            exampleList.addAll(results.values as ArrayList<producto>)
            notifyDataSetChanged()
        }
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val Producto:producto = exampleList.get(position)
        holder.Busqueda_Precio.text = Producto.precio
        holder.Busqueda_Producto.text = Producto.nombre
        Picasso.get().load(Producto.imagen)
            .into(holder.Busqueda_Imagen)

        holder.itemView.setOnClickListener{

            val intent = Intent(context, Product_Details::class.java)
            intent.putExtra("producto",Producto)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return exampleList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView:View = LayoutInflater.from(context).inflate(R.layout.cardview_busqueda,parent,false)
        return ViewHolder(itemView)
    }

    override fun getFilter(): Filter {
        return filtro
    }





}