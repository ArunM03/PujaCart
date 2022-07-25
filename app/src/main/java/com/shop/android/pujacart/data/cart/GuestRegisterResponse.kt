package com.shop.android.pujacart.data.cart


class GuestRegisterResponse : ArrayList<GuestRegisterResponseItem>()
data class GuestRegisterResponseItem(
    val GCartID : String = "",
    val Status : String = ""
)