package com.shop.android.pujacart.data.order

data class InsertOrderBody(
    val DeliveryWindowDate: String,
    val DeliveryWindowTime: String,
    val delAddID: Int,
    val itemsCount: Int,
    val itemsDiscount: Int,
    val payableAmt: Int,
    val redeemDiscount: Int,
    val redeemPoints: String,
    val rewDiscount: Int,
    val tCartID: Int,
    val totPrice: Int,
    val userID: Int
)