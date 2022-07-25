package com.shop.android.pujacart.data.user



class LoginUserResponse : ArrayList<LoginUserResponseItem>()

data class LoginUserResponseItem(
    val UserID : Int = 0,
    val UserName : String = "",
    val UserEmailID : String = "",
    val CustomerTypeID : Int = 0,
    val RegionTypeID : Int = 0,
    val SiteID : String = "",
    val Status : String = ""
)