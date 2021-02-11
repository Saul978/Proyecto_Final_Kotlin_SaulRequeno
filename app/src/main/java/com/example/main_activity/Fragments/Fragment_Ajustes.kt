package com.example.main_activity.Fragments


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TextView
import android.widget.Toast
import androidx.viewpager.widget.ViewPager
import com.example.main_activity.Activities.Login_Activity
import com.example.main_activity.Activities.MainActivity
import com.example.main_activity.Activities.Register_Activity
import com.example.main_activity.Adapter.Adapter_Pages
import com.example.main_activity.Dialogos.Dialogo_Add
import com.example.main_activity.Dialogos.Dialogo_Busqueda
import com.example.main_activity.Dialogos.Dialogo_Mod_Elim

import com.example.main_activity.R
import com.example.main_activity.Utiles.Utiles
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import org.w3c.dom.Text

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class Fragment_Ajustes : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment__ajustes, container, false)
            view.findViewById<TextView>(R.id.Cerrar_Sesion).setOnClickListener{
                Utiles(view.context).cerrarSesion()
                val i = Intent(view.context, Login_Activity::class.java)
                i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(i)
            }

            view.findViewById<TextView>(R.id.Validar_Usuario).setOnClickListener{
               Utiles(view.context).ValidarCorreo()

            }
        val uid= Utiles(view.context).usuario_Actual()
        FirebaseDatabase.getInstance().getReference("/users/$uid").child("admin")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(p0: DataSnapshot) {
                    if (p0.getValue(Boolean::class.java)!!){


                        val add:TextView= view.findViewById<TextView>(R.id.Add_Producto)
                        add.visibility=View.VISIBLE
                        add.setOnClickListener{
                            val Dialogo = Dialogo_Add()
                            Dialogo.show(parentFragmentManager,"dialogo")

                        }
                        val mod:TextView= view.findViewById<TextView>(R.id.Mod_Eli_Producto)
                        mod.visibility=View.VISIBLE
                        mod.setOnClickListener{
                            val Dialogo = Dialogo_Mod_Elim()
                            Dialogo.show(parentFragmentManager,"dialogo")
                        }


                    }
                }

                override fun onCancelled(p0: DatabaseError) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }
            }
            )

        return view

    }



    }


