package com.shop.android.pujacart.data.cart

data class GetTempCartResponseItem(
    val CartID: Int,
    val CreatedDateTime: String,
    val DeliveryCharge: String,
    val Discount: String,
    val ItemsDiscount: String,
    val NoofItems: Int,
    val PayableAmount: String,
    val Remarks: String,
    val Status: String,
    val TotalPrice: String,
    val TotalSST: String,
    val UpdateDateTime: String,
    val UserID: Int
)