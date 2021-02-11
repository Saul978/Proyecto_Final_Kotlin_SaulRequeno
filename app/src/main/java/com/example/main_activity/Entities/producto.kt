package com.example.main_activity.Entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable


 class producto(
     var id:String,
     var nombre:String,
     var marca:String,
     var categoria:String,
     var precio:String,
     var imagen:String,
     var descripcion:String


):Serializable{
     constructor():this("","","","","","","")
}