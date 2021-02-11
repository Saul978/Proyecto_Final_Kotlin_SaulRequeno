package com.example.main_activity.Dialogos

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.AdapterView
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.main_activity.Activities.Product_Details
import com.example.main_activity.Adapter.Adapter_Busqueda
import com.example.main_activity.Adapter.Adapter_Mod_Elim
import com.example.main_activity.Entities.producto
import com.example.main_activity.R
import com.example.main_activity.Utiles.Utiles
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
import kotlinx.android.synthetic.main.cardview_busqueda.view.*
import kotlinx.android.synthetic.main.fullscreen_dialog.*
import kotlinx.android.synthetic.main.fullscreen_dialog.toolbar
import kotlinx.android.synthetic.main.fullscreen_dialog_mod_elim.*
import java.util.logging.Filter



class Dialogo_Mod_Elim : DialogFragment(){

    private var  adapter : Adapter_Mod_Elim? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme_FullScreenDialog)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fullscreen_dialog_mod_elim, container, false)
        val recycler =view.findViewById<RecyclerView>(R.id.Recycler_Mod_Elim)
        val layoutmanager : LinearLayoutManager = LinearLayoutManager(layoutInflater.context)
        recycler.layoutManager= layoutmanager
        cargar(recycler)

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.setNavigationOnClickListener { dismiss() }


    }



    override fun onStart() {
        super.onStart()
        var dialog = dialog
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog.window!!.setLayout(width, height)
        }
    }




    private fun cargar(Recycler:RecyclerView){
        var productos= ArrayList<producto>()
        val ref = FirebaseDatabase.getInstance().getReference("/productos")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {

                p0.children.forEach {
                    val Producto = it.getValue(producto::class.java)
                    if (Producto != null) {
                        productos.add(Producto)
                    }

                }
                adapter= Adapter_Mod_Elim(layoutInflater.context,productos)
                Recycler.adapter=adapter
            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })

    }





}