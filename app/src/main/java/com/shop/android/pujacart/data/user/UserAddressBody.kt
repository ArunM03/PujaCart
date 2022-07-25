package com.shop.android.pujacart.data.user

data class UserAddressBody(
    val userID : Int = 0,
    val DelAddressName : String = "",
    val DelAddress : String = "",
    val DelDistrict : String = "",
    val DelState : String = "",
    val DelPostCode : String = "",
    val DelContactNo : String = "",
    val DelLatitude : String = "",
    val DelLongitude : String = "",
    val userName : String = ""
)