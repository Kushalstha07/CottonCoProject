package com.example.cottonco.model

data class ProductModel(
    // Product data model for Firebase storage
    val productId: String = "",
    var productName: String=" ",
    var price: Double=0.0 ,
    var description: String=" ",
    var image : String ="",

)
