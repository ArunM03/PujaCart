package com.shop.android.pujacart.data.cart

data class GuestCartItemInserBody(
    val gcartid : Int = 1,
    val itemID : Int = 1,
    val psizeID : Int = 1,
    val suppID : Int = 1,
    val brandID : Int = 1,
    val selQuantity : Int = 1,
    val pPrice : Int = 1,
    val discount : Int = 1,
    val offerType : String = "1",
    val salePrice : Int = 1,
    val buyQty : Int = 1,
    val getQty : Int = 1,
    val totGetQty : Int = 1
)