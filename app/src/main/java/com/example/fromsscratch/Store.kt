package com.example.fromsscratch

data class Store(
    val name: String?="",
    val id: String? = "",
    val latitude:String? = "",
    val longitude:String?="",
    val address: String?="",
    var reviews: List<Review>? = emptyList()
)