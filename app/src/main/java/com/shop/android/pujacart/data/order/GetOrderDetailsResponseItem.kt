package com.shop.android.pujacart.data.order

data class GetOrderDetailsResponseItem(
    val CartID: Int,
    val DeliveryAddress: String,
    val DeliveryCharge: Double,
    val Discount: Double,
    val NoofItems: Int,
    val OrderCreatedDate: String,
    val OrderID: Int,
    val OrderNo: Int,
    val OrderOTP: String,
    val OrderStatus: String,
    val PayableAmount: Double,
    val PaymentDone: String,
    val Status: String,
    val TotalPrice: Double,
    val UserName: String
)