package com.example.main_activity.Entities

import com.example.main_activity.Fragments.ProductoItem
import java.io.Serializable

class carrito():Serializable{
    var key:String=""
    var cantidad:Int=0
    var producto:producto?=null

    constructor(key:String,cantidad:Int,producto: producto) : this() {
        this.key=key
        this.cantidad=cantidad
        this.producto=producto
    }
    constructor(cantidad:Int,producto: producto) : this() {
        this.cantidad=cantidad
        this.producto=producto
    }

}
