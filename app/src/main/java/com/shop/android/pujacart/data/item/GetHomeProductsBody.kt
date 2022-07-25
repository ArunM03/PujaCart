package com.shop.android.pujacart.data.item

data class GetHomeProductsBody(
    var userID : Int = 1,
    var userEmail : String = "",
    var deviceID : String = ""
)