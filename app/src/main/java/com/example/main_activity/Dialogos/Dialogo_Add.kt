package com.example.main_activity.Dialogos

import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.content.Intent.getIntent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.main_activity.Activities.Product_Details
import com.example.main_activity.Adapter.Adapter_Busqueda
import com.example.main_activity.Entities.producto
import com.example.main_activity.R
import com.example.main_activity.Utiles.Utiles
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.activity_product__details.*
import kotlinx.android.synthetic.main.cardview.view.*
import kotlinx.android.synthetic.main.cardview_busqueda.view.*
import kotlinx.android.synthetic.main.fullscreen_dialog.*
import kotlinx.android.synthetic.main.fullscreen_dialog.toolbar
import kotlinx.android.synthetic.main.fullscreen_dialog_add.*
import java.util.*
import java.util.logging.Filter



class Dialogo_Add : DialogFragment() {
    var uriimagen : Uri? = null
    private var  adapter : Adapter_Busqueda? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme_FullScreenDialog)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fullscreen_dialog_add, container, false)

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.setNavigationOnClickListener { v -> dismiss() }
        toolbar.inflateMenu(R.menu.menu_add)
        toolbar.menu.findItem(R.id.action_add).setOnMenuItemClickListener {
            if (validar()){
                Utiles(view.context).Add_Producto(uriimagen, producto(UUID.randomUUID().toString(),
                    EtAdd_Nombre.text?.trim().toString(), EtAdd_Marca.text?.trim().toString(),EtAdd_Categoria.text?.trim().toString(),EtAdd_Precio.text?.trim().toString()+"€","",EtAdd_Descripcion.text?.trim().toString()

                ))
                dismiss()
            }else{

            }

            true
        }

        Add_Imagen.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type="image/*"
            startActivityForResult(intent,0)
        }

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==0&&resultCode==Activity.RESULT_OK && data!=null){
             uriimagen =data.data
            val bitmap=MediaStore.Images.Media.getBitmap(activity?.contentResolver,uriimagen)
            val bitmapDrawable=BitmapDrawable(bitmap)
            Add_Imagen.setBackgroundDrawable(bitmapDrawable)
            Add_Imagen.height=900
            Add_Imagen.setText("")

        }
    }

    fun validar():Boolean{
        if(!validarDescripcion()or
            !validarCategoria()or
            !validarMarca()or
            !validarImagen()or
            !validarPrecio()or
            !validarNombre()){
            return false
        }else{
            return true
        }
    }

    fun validarNombre():Boolean{
        if (EtAdd_Nombre.text.toString().trim().isEmpty()){
            Add_Nombre.error="El nombre no puede estar vacio"
            return false
        }else{
            Add_Nombre.error=null
            return true
        }

    }
    fun validarImagen():Boolean{
        if (uriimagen==null){
            Toast.makeText(context,"Escoge una foto, es obligatoria",Toast.LENGTH_SHORT).show()
            return false
        }else{
            return true
        }

    }
    fun validarCategoria():Boolean{
        if (EtAdd_Categoria.text.toString().trim().isEmpty()){
            Add_Categoria.error="La categoria no puede estar vacia"
            return false
        }else{
            Add_Categoria.error=null
            return true
        }

    }
    fun validarPrecio():Boolean{
        if (EtAdd_Precio.text.toString().trim().isEmpty()){
            Add_Precio.error="El precio no puede estar vacio"
            return false
        }else{
            Add_Precio.error=null
            return true
        }

    }
    fun validarDescripcion():Boolean{
        if (EtAdd_Descripcion.text.toString().trim().isEmpty()){
            Add_Descripcion.error="La descripcion no puede estar vacia"
            return false
        }else{
            Add_Descripcion.error=null
            return true
        }

    }
    fun validarMarca():Boolean{
        if (EtAdd_Marca.text.toString().trim().isEmpty()){
            Add_Marca.error="La marca no puede estar vacia"
            return false
        }else{
            Add_Marca.error=null
            return true
        }

    }

}