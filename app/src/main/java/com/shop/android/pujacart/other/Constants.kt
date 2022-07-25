package com.shop.android.pujacart.other

import com.shop.android.pujacart.data.category.CategoryResponseItem
import com.shop.android.pujacart.data.category.SubCategoryResponseItem
import com.shop.android.pujacart.data.item.GetItemBySubCatResponseItem
import com.shop.android.pujacart.data.order.GetOrderDetailsResponseItem
import com.shop.android.pujacart.data.order.GetOrderItemDetailsResponse

object Constants {

    var categoryID = 0
    var categoryName = ""


    var subCategory = SubCategoryResponseItem()

    lateinit var curProduct : GetItemBySubCatResponseItem


    val SUCCESS = "Success"


    lateinit var getOrderDetailsResponseItem : GetOrderDetailsResponseItem

}