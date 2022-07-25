package com.shop.android.pujacart.data.cart

data class GetGuestCartItemResponseItem(
    val AvailableQuantity: Int,
    val BrandID: Int,
    val BuyQty: Int,
    val CreatedDateTime: String,
    val DiscountAmount: String,
    val GCartID: Int,
    val CartID: Int,
    val GST: String,
    val GetQty: Int,
    val OfferType: String,
    val PCID: Int,
    val PGID: Int,
    val PSID: Int,
    val PSize: String,
    val PType: String,
    val PTypeID: Int,
    val ProductID: Int,
    val ProductImage: String,
    val ProductName: String,
    var Quantity: Int,
    val SalePrice: String,
    val Status: String,
    val SupplierID: Int,
    val TotalPrice: String,
    val UnitPrice: String
)