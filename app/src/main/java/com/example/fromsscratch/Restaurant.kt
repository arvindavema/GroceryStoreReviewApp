package com.example.fromsscratch

class Restaurant(n: String, _id:String,lat:String,lon:String,add:String) {
    val name: String
    lateinit  var id: String
    lateinit var address:String
    lateinit var latitude:String
    lateinit var longitude:String
    // initializer block
    init {
        name = n.capitalize()
        id = _id
        address=add
        latitude=lat
        longitude=lon


    }
}