package com.shop.android.pujacart.other

import android.content.Context
import com.google.gson.Gson
import com.shop.android.pujacart.data.cart.GetTempCartResponseItem
import com.shop.android.pujacart.data.user.LoginUserResponseItem

class SharedPref(context: Context) {

    val sharedpref = context.getSharedPreferences("pujacart_pref", Context.MODE_PRIVATE)
    val editor = sharedpref.edit()

    val CARTITEMS = "CartItems"
    val GUESTID = "GuestId"
    val USERAUTHSTATUS = "UserAuthStatus"
    val USERDATA = "UserData"
    val TEMPCARTDATA = "TempCartData"
    val CARTTRANSFERSTATUS = "CartTransferStatus"

    fun saveItems(itemsCount : Int) {
        editor.apply {
            putInt(CARTITEMS, itemsCount)
            apply()
        }
    }


    fun getItems(): Int {
        return sharedpref.getInt(CARTITEMS, 0)
    }

    fun setUserData(userData: LoginUserResponseItem){
        val gson =  Gson()
        val json = gson.toJson(userData)
        editor.putString(USERDATA, json)
        editor.putBoolean(USERAUTHSTATUS,true)
        editor.commit()
    }

    fun setUserAuthStatus(status : Boolean){
        editor.putBoolean(USERAUTHSTATUS,status)
        editor.commit()
    }

    fun getUserData() : LoginUserResponseItem{
        val gson = Gson()
        val json = sharedpref.getString(USERDATA, "null")
        return gson.fromJson(json, LoginUserResponseItem::class.java)
    }

    fun setCartTransferStatus(status : Boolean){
        editor.putBoolean(CARTTRANSFERSTATUS,status)
        editor.commit()
    }

    fun setTempCartData(getTempCartResponseItem: GetTempCartResponseItem){
        val gson =  Gson()
        val json = gson.toJson(getTempCartResponseItem)
        editor.putString(TEMPCARTDATA, json)
        editor.commit()
    }

    fun getTempCartData() : GetTempCartResponseItem{
        val gson = Gson()
        val json = sharedpref.getString(TEMPCARTDATA, "null")
        return gson.fromJson(json, GetTempCartResponseItem::class.java)
    }

    fun getUserAuthStatus() : Boolean{
        return sharedpref.getBoolean(USERAUTHSTATUS, false)
    }

    fun getCartTransferStatus() : Boolean{
        return sharedpref.getBoolean(CARTTRANSFERSTATUS, false)
    }





    fun saveGuestID(guestID : String) {
        editor.apply {
            putString(GUESTID, guestID)
            apply()
        }
    }

    fun getGuestID(): String {
        return sharedpref.getString(GUESTID, "null") ?: "null"
    }

}