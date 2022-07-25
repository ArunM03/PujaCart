package com.shop.android.pujacart.data.order

data class GetOrderItemDetailsResponseItem(
    val Brand: String,
    val GST: Double,
    val OrderDeliveryTime: String,
    val OrderID: Int,
    val OrderNo: Int,
    val PSID: Int,
    val ProductID: Int,
    val ProductName: String,
    val ProductQuantity: Int,
    val ProductSize: String,
    val ProductType: String,
    val Returned: Int,
    val Status: String,
    val UnitPrice: Double
)