package com.shop.android.pujacart.data.user

data class UserDetailsResponseItem(
    val BillingAddress: String,
    val BillingCity: String,
    val BillingPostcode: String,
    val BillingState: String,
    val City: Any,
    val CompanyName: String,
    val ContactNo: String,
    val CreditLimit: Double,
    val CustomerType: String,
    val DeliveryAddress: Any,
    val DisplayName: String,
    val EmailID: String,
    val OutletName: String,
    val Password: String,
    val Region: String,
    val SiteID: String,
    val State: Any,
    val Status: String,
    val StoreID: String,
    val UserID: Int,
    val UserName: String,
    val ZoneCode: String
)