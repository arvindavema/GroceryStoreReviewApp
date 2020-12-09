package com.example.fromsscratch


data class Review (
    val userId: String? = "",
    val storeId: String? = "",
    val storeName: String? = "",
    val username: String? = "",
    val body:String? = "",
    val rating:Float? = 0f,
    val date:String? = "",
    val image:String? =  "",
    val timeStamp: String?,
    val id: String? = timeStamp
)