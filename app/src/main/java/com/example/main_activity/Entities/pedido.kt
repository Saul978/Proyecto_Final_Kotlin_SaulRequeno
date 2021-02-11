package com.example.main_activity.Entities

import java.io.Serializable
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList

class pedido(
    var id:String,
    var fecha: String,
    var total:Double,
    var carrito: ArrayList<carrito>? =null,
    var direccion :String
):Serializable{
    constructor():this("","",0.0,null,"")
}