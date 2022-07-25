package com.shop.android.pujacart.api

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shop.android.pujacart.data.banner.MainBannerResponse
import com.shop.android.pujacart.data.banner.MainBannerResponseItem
import com.shop.android.pujacart.data.cart.*
import com.shop.android.pujacart.data.category.CategoryResponseItem
import com.shop.android.pujacart.data.category.SubCategoryBody
import com.shop.android.pujacart.data.category.SubCategoryResponseItem
import com.shop.android.pujacart.data.item.*
import com.shop.android.pujacart.data.order.*
import com.shop.android.pujacart.data.user.*
import com.shop.android.pujacart.other.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewmodel : ViewModel() {


    private var _categoriesLive = MutableLiveData<List<CategoryResponseItem>>()
    var categoriesLive : LiveData<List<CategoryResponseItem>> = _categoriesLive
    private var _errorCategoriesLive = MutableLiveData<String>()
    var errorCategoriesLive : LiveData<String> = _errorCategoriesLive


    fun getCategories() = viewModelScope.launch(Dispatchers.IO) {

        try {

            val response = RetrofitInstance.api.getCategories()

            if(response.isSuccessful){

                response.body()?.let { it ->

                    _categoriesLive.postValue(it)

                }

            }else{
                _errorCategoriesLive.postValue("Response not Successful ${response.errorBody()}")
            }


        }catch (e:Exception){
            _errorCategoriesLive.postValue(e.message)
        }
    }

    private var _subCategoriesLive = MutableLiveData<List<SubCategoryResponseItem>>()
    var subCategoriesLive : LiveData<List<SubCategoryResponseItem>> = _subCategoriesLive
    private var _errorSubCategoriesLive = MutableLiveData<String>()
    var errorSubCategoriesLive : LiveData<String> = _errorSubCategoriesLive


    fun getSubCategories(categoryID : Int) = viewModelScope.launch(Dispatchers.IO) {

        try {

            val response = RetrofitInstance.api.getSubCategories(SubCategoryBody(categoryID))

            if(response.isSuccessful){

                response.body()?.let { it ->

                    _subCategoriesLive.postValue(it)

                }

            }else{
                _errorSubCategoriesLive.postValue("Response not Successful ${response.errorBody()}")
            }


        }catch (e:Exception){
            _errorSubCategoriesLive.postValue(e.message)
        }
    }

    private var _itemBysubCategoryLive = MutableLiveData<List<GetItemBySubCatResponseItem>>()
    var itemBysubCategoryLive : LiveData<List<GetItemBySubCatResponseItem>> = _itemBysubCategoryLive
    private var _errorItemBysubCategoryLive = MutableLiveData<String>()
    var errorItemBysubCategoryLive : LiveData<String> = _errorItemBysubCategoryLive


    fun getItemBySubCategory(getItemBySubCatBody: GetItemBySubCatBody) = viewModelScope.launch(Dispatchers.IO) {

        try {

            val response = RetrofitInstance.api.getItemListBySubCategory(getItemBySubCatBody)

            if(response.isSuccessful){

                response.body()?.let { it ->

                    _itemBysubCategoryLive.postValue(it)

                }

            }else{
                _errorItemBysubCategoryLive.postValue("Response not Successful ${response.errorBody()}")
            }


        }catch (e:Exception){
            _errorItemBysubCategoryLive.postValue(e.message)
        }
    }


    private var _homeProductsLive = MutableLiveData<List<GetItemBySubCatResponseItem>>()
    var homeProductsLive : LiveData<List<GetItemBySubCatResponseItem>> = _homeProductsLive
    private var _errorHomeProductsLive = MutableLiveData<String>()
    var errorHomeProductsLive : LiveData<String> = _errorHomeProductsLive


    fun getHomeProducts(homeProductsBody: GetHomeProductsBody) = viewModelScope.launch(Dispatchers.IO) {

        try {

            val response = RetrofitInstance.api.getHomeProducts(homeProductsBody)

            if(response.isSuccessful){

                response.body()?.let { it ->

                    _homeProductsLive.postValue(it)

                }

            }else{
                _errorHomeProductsLive.postValue("Response not Successful ${response.errorBody()}")
            }


        }catch (e:Exception){
            _errorHomeProductsLive.postValue(e.message)
        }
    }



    private var _guestRegisterLive = MutableLiveData<GuestRegisterResponse>()
    var guestRegisterLive : LiveData<GuestRegisterResponse> = _guestRegisterLive
    private var _errorGuestRegisterLive = MutableLiveData<String>()
    var errorGuestRegisterLive : LiveData<String> = _errorGuestRegisterLive

    fun guestRegister(homeProductsBody: GetHomeProductsBody) = viewModelScope.launch(Dispatchers.IO) {

        try {

            val response = RetrofitInstance.api.guestRegister(homeProductsBody)

            if(response.isSuccessful){

                response.body()?.let { it ->

                    _guestRegisterLive.postValue(it)

                }

            }else{
                _errorGuestRegisterLive.postValue("Response not Successful ${response.errorBody()}")
            }


        }catch (e:Exception){
            _errorGuestRegisterLive.postValue(e.message)
        }
    }

    private var _insertGuestCartItemLive = MutableLiveData<GuestInsertCartResponse>()
    var insertGuestCartItemLive : LiveData<GuestInsertCartResponse> = _insertGuestCartItemLive
    private var _errorInsertGuestCartItemLive = MutableLiveData<String>()
    var errorInsertGuestCartItemLive : LiveData<String> = _errorInsertGuestCartItemLive

    fun guestInserCartItem(guestCartItemInserBody: GuestCartItemInserBody) = viewModelScope.launch(Dispatchers.IO) {

        try {

            val response = RetrofitInstance.api.insertGuestCartItem(guestCartItemInserBody)

            if(response.isSuccessful){

                response.body()?.let { it ->

                    _insertGuestCartItemLive.postValue(it)

                }

            }else{
                _errorInsertGuestCartItemLive.postValue("Response not Successful ${response.errorBody()}")
            }


        }catch (e:Exception){
            _errorInsertGuestCartItemLive.postValue(e.message)
        }
    }

    fun userInserCartItem(userCartItemInserBody: UserCartItemInserBody) = viewModelScope.launch(Dispatchers.IO) {

        try {

            val response = RetrofitInstance.api.insertUserCartItem(userCartItemInserBody)

            if(response.isSuccessful){

                response.body()?.let { it ->

                    _insertGuestCartItemLive.postValue(it)

                }

            }else{
                _errorInsertGuestCartItemLive.postValue("Response not Successful ${response.errorBody()}")
            }


        }catch (e:Exception){
            _errorInsertGuestCartItemLive.postValue(e.message)
        }
    }


    private var _getGuestCartItemLive = MutableLiveData<GetGuestCartItemResponse>()
    var getGuestCartItemLive : LiveData<GetGuestCartItemResponse> = _getGuestCartItemLive
    private var _errorGetGuestCartItemLive = MutableLiveData<String>()
    var errorGetGuestCartItemLive : LiveData<String> = _errorGetGuestCartItemLive

    fun getGuestCartItems(getGuestCartItemsBody: GetGuestCartItemsBody) = viewModelScope.launch(Dispatchers.IO) {

        try {

            val response = RetrofitInstance.api.getGuestCartItems(getGuestCartItemsBody)

            if(response.isSuccessful){

                response.body()?.let { it ->

                    if(it.isNotEmpty()){
                        if (it[0].Status != "Failed"){
                            _getGuestCartItemLive.postValue(it)
                        }else{
                            _getGuestCartItemLive.postValue(GetGuestCartItemResponse().apply {
                                emptyList<GetGuestCartItemResponseItem>()
                            })

                        }
                    }

                }

            }else{
                _errorGetGuestCartItemLive.postValue("Response not Successful ${response.errorBody()}")
            }


        }catch (e:Exception){
            _errorGetGuestCartItemLive.postValue(e.message)
        }
    }

    fun getUserCartItems(getUserCartItemsBody: GetUserCartItemsBody) = viewModelScope.launch(Dispatchers.IO) {

        try {

            val response = RetrofitInstance.api.getUserCartItems(getUserCartItemsBody)

            if(response.isSuccessful){

                response.body()?.let { it ->

                    if(it.isNotEmpty()){
                        if (it[0].Status != "Failed"){
                            _getGuestCartItemLive.postValue(it)
                        }else{
                            _getGuestCartItemLive.postValue(GetGuestCartItemResponse().apply {
                                emptyList<GetGuestCartItemResponseItem>()
                            })

                        }
                    }

                }

            }else{
                _errorGetGuestCartItemLive.postValue("Response not Successful ${response.errorBody()}")
            }


        }catch (e:Exception){
            _errorGetGuestCartItemLive.postValue(e.message)
        }
    }


    private var _updateGuestCartItemsLive = MutableLiveData<String>()
    var updateGuestCartItemsLive : LiveData<String> = _updateGuestCartItemsLive
    private var _errorUpdateGuestCartItemsLive = MutableLiveData<String>()
    var errorUpdateGuestCartItemsLive : LiveData<String> = _errorUpdateGuestCartItemsLive

    fun updateGuestCartItems(guestCartItemInserBody: GuestCartItemInserBody) = viewModelScope.launch(Dispatchers.IO) {

        try {

            val response = RetrofitInstance.api.updateGuestCartItems(guestCartItemInserBody)

            if(response.isSuccessful){

                response.body()?.let { it ->

                    _updateGuestCartItemsLive.postValue("Success")

                }

            }else{
                _errorUpdateGuestCartItemsLive.postValue("Response not Successful ${response.errorBody()}")
            }


        }catch (e:Exception){
            _errorUpdateGuestCartItemsLive.postValue(e.message)
        }
    }

    fun updateUserCartItems(userCartItemInserBody: UserCartItemInserBody) = viewModelScope.launch(Dispatchers.IO) {

        try {

            val response = RetrofitInstance.api.updateUserCartItem(userCartItemInserBody)

            if(response.isSuccessful){

                response.body()?.let { it ->

                    _updateGuestCartItemsLive.postValue("Success")

                }

            }else{
                _errorUpdateGuestCartItemsLive.postValue("Response not Successful ${response.errorBody()}")
            }


        }catch (e:Exception){
            _errorUpdateGuestCartItemsLive.postValue(e.message)
        }
    }

    private var _createUserLive = MutableLiveData<String>()
    var createUserLive : LiveData<String> = _createUserLive
    private var _errorCreateUserLive = MutableLiveData<String>()
    var errorCreateUserLive : LiveData<String> = _errorCreateUserLive

    fun createUser(createUserBody: CreateUserBody) = viewModelScope.launch(Dispatchers.IO) {

        try {

            val response = RetrofitInstance.api.createUser(createUserBody)

            if(response.isSuccessful){

                response.body()?.let { it ->

                    if(it.isNotEmpty()){
                        _createUserLive.postValue(it[0].Status)
                    }

                }

            }else{
                _errorCreateUserLive.postValue("Response not Successful ${response.errorBody()}")
            }


        }catch (e:Exception){
            _errorCreateUserLive.postValue(e.message)
        }
    }

    fun insertUserAddress(userAddressBody: UserAddressBody) = viewModelScope.launch(Dispatchers.IO) {

        try {

            val response = RetrofitInstance.api.insertDeliveryAddress(userAddressBody)

            if(response.isSuccessful){

                response.body()?.let { it ->

                    if(it.isNotEmpty()){
                        _createUserLive.postValue(it[0].Status)
                    }

                }

            }else{
                _errorCreateUserLive.postValue("Response not Successful ${response.errorBody()}")
            }


        }catch (e:Exception){
            _errorCreateUserLive.postValue(e.message)
        }
    }




    private var _loginUserLive = MutableLiveData<LoginUserResponse>()
    var loginUserLive : LiveData<LoginUserResponse> = _loginUserLive
    private var _errorLoginUserLive = MutableLiveData<String>()
    var errorLoginUserLive : LiveData<String> = _errorLoginUserLive

    fun loginUser(loginUserBody: LoginUserBody) = viewModelScope.launch(Dispatchers.IO) {

        try {

            val response = RetrofitInstance.api.loginUser(loginUserBody)

            if(response.isSuccessful){

                response.body()?.let { it ->

                    if(it.isNotEmpty()){
                            _loginUserLive.postValue(it)
                    }

                }

            }else{
                _errorLoginUserLive.postValue("Response not Successful ${response.errorBody()}")
            }


        }catch (e:Exception){
            _errorLoginUserLive.postValue(e.message)
        }
    }


    private var _cartTransferLive = MutableLiveData<String>()
    var cartTransferLive : LiveData<String> = _cartTransferLive
    private var _errorCartTransferLive = MutableLiveData<String>()
    var errorCartTransferLive : LiveData<String> = _errorCartTransferLive

    fun cartTransfer(gCartTransferBody: GCartTransferBody) = viewModelScope.launch(Dispatchers.IO) {

        try {

            val response = RetrofitInstance.api.cartTransfer(gCartTransferBody)

            if(response.isSuccessful){

                response.body()?.let { it ->

                    if(it.isNotEmpty()){
                        if(it[0].Status == Constants.SUCCESS){
                            _cartTransferLive.postValue(it[0].Status)
                        }
                    }

                }

            }else{
                _errorCartTransferLive.postValue("Response not Successful ${response.errorBody()}")
            }


        }catch (e:Exception){
            _errorCartTransferLive.postValue(e.message)
        }
    }

    private var _getTempCartItemLive = MutableLiveData<GetTempCartResponse>()
    var getTempCartItemLive : LiveData<GetTempCartResponse> = _getTempCartItemLive
    private var _errorGetTempCartItemLive = MutableLiveData<String>()
    var errorGetTempCartItemLive : LiveData<String> = _errorGetTempCartItemLive

    fun getTempCartItems(getTempCartBody: GetTempCartBody) = viewModelScope.launch(Dispatchers.IO) {

        try {

            val response = RetrofitInstance.api.getTempCart(getTempCartBody)

            if(response.isSuccessful){

                response.body()?.let { it ->

                    if(it.isNotEmpty()){
                        if (it[0].Status != "Failed"){
                            _getTempCartItemLive.postValue(it)
                        }else{
                            _getTempCartItemLive.postValue(GetTempCartResponse().apply {
                                emptyList<GetTempCartResponseItem>()
                            })

                        }
                    }

                }

            }else{
                _errorGetTempCartItemLive.postValue("Response not Successful ${response.errorBody()}")
            }


        }catch (e:Exception){
            _errorGetTempCartItemLive.postValue(e.message)
        }
    }


    private var _getUserAddressLive = MutableLiveData<GetUserAddressResponse>()
    var getUserAddressLive : LiveData<GetUserAddressResponse> = _getUserAddressLive
    private var _errorGetUserAddressLive = MutableLiveData<String>()
    var errorGetUserAddressLive : LiveData<String> = _errorGetUserAddressLive

    fun getUserAddress(getTempCartBody: GetTempCartBody) = viewModelScope.launch(Dispatchers.IO) {

        try {

            val response = RetrofitInstance.api.getDeliveryAddress(getTempCartBody)

            if(response.isSuccessful){

                response.body()?.let { it ->

                    if(it.isNotEmpty()){
                        if (it[0].Status != "Failed"){
                            _getUserAddressLive.postValue(it)
                        }else{
                            _getUserAddressLive.postValue(GetUserAddressResponse().apply {
                                emptyList<GetUserAddressResponseItem>()
                            })

                        }
                    }

                }

            }else{
                _errorGetUserAddressLive.postValue("Response not Successful ${response.errorBody()}")
            }


        }catch (e:Exception){
            _errorGetUserAddressLive.postValue(e.message)
        }
    }


    private var _insertOrderLive = MutableLiveData<CreateUserResponse>()
    var insertOrderLive : LiveData<CreateUserResponse> = _insertOrderLive
    private var _errorInsertOrderLive = MutableLiveData<String>()
    var errorInsertOrderLive : LiveData<String> = _errorInsertOrderLive

    fun insertOrder(insertOrderBody: InsertOrderBody) = viewModelScope.launch(Dispatchers.IO) {

        try {

            val response = RetrofitInstance.api.insertOrder(insertOrderBody)

            if(response.isSuccessful){

                response.body()?.let { it ->

                    if(it.isNotEmpty()){
                        if (it[0].Status != "Failed"){
                            _insertOrderLive.postValue(it)
                        }else{
                            _insertOrderLive.postValue(CreateUserResponse().apply {
                                emptyList<CreateUserResponseItem>()
                            })

                        }
                    }

                }

            }else{
                _errorInsertOrderLive.postValue("Response not Successful ${response.errorBody()}")
            }


        }catch (e:Exception){
            _errorInsertOrderLive.postValue(e.message)
        }
    }

    private var _getOrderDetailsLive = MutableLiveData<GetOrderDetailsResponse>()
    var getOrderDetailsLive : LiveData<GetOrderDetailsResponse> = _getOrderDetailsLive
    private var _errorGetOrderDetailsLive = MutableLiveData<String>()
    var errorGetOrderDetailsLive : LiveData<String> = _errorGetOrderDetailsLive

    fun getOrderDetails(getTempCartBody: GetTempCartBody) = viewModelScope.launch(Dispatchers.IO) {

        try {

            val response = RetrofitInstance.api.getOrderDetails(getTempCartBody)

            if(response.isSuccessful){

                response.body()?.let { it ->

                    if(it.isNotEmpty()){
                        if (it[0].Status != "Failed"){
                            _getOrderDetailsLive.postValue(it)
                        }else{
                            _getOrderDetailsLive.postValue(GetOrderDetailsResponse().apply {
                                emptyList<GetOrderDetailsResponseItem>()
                            })

                        }
                    }

                }

            }else{
                _errorGetOrderDetailsLive.postValue("Response not Successful ${response.errorBody()}")
            }


        }catch (e:Exception){
            _errorGetOrderDetailsLive.postValue(e.message)
        }
    }

    private var _getOrderItemDetailsLive = MutableLiveData<GetOrderItemDetailsResponse>()
    var getOrderItemDetailsLive : LiveData<GetOrderItemDetailsResponse> = _getOrderItemDetailsLive
    private var _errorGetOrderItemDetailsLive = MutableLiveData<String>()
    var errorGetOrderItemDetailsLive : LiveData<String> = _errorGetOrderItemDetailsLive

    fun getOrderItemDetails(getOrderItemDetailsBody: GetOrderItemDetailsBody) = viewModelScope.launch(Dispatchers.IO) {

        try {

            val response = RetrofitInstance.api.getOrderItemDetails(getOrderItemDetailsBody)

            if(response.isSuccessful){

                response.body()?.let { it ->

                    if(it.isNotEmpty()){
                        if (it[0].Status != "Failed"){
                            _getOrderItemDetailsLive.postValue(it)
                        }else{
                            _getOrderItemDetailsLive.postValue(GetOrderItemDetailsResponse().apply {
                                emptyList<GetOrderItemDetailsResponseItem>()
                            })

                        }
                    }

                }

            }else{
                _errorGetOrderItemDetailsLive.postValue("Response not Successful ${response.errorBody()}")
            }


        }catch (e:Exception){
            _errorGetOrderItemDetailsLive.postValue(e.message)
        }
    }


    private var _getUserDetailsLive = MutableLiveData<UserDetailsResponse>()
    var getUserDetailsLive : LiveData<UserDetailsResponse> = _getUserDetailsLive
    private var _errorGetUserDetailsLive = MutableLiveData<String>()
    var errorGetUserDetailsLive : LiveData<String> = _errorGetUserDetailsLive

    fun getUserDetails(getTempCartBody: GetTempCartBody) = viewModelScope.launch(Dispatchers.IO) {

        try {

            val response = RetrofitInstance.api.getUserDetails(getTempCartBody)

            if(response.isSuccessful){

                response.body()?.let { it ->

                    if(it.isNotEmpty()){
                        if (it[0].Status != "Failed"){
                            _getUserDetailsLive.postValue(it)
                        }else{
                            _getUserDetailsLive.postValue(UserDetailsResponse().apply {
                                emptyList<UserDetailsResponseItem>()
                            })

                        }
                    }

                }

            }else{
                _errorGetOrderItemDetailsLive.postValue("Response not Successful ${response.errorBody()}")
            }


        }catch (e:Exception){
            _errorGetOrderItemDetailsLive.postValue(e.message)
        }
    }

    private var _searchItemsLive = MutableLiveData<GetItemBySubCatResponse>()
    var searchItemsLive : LiveData<GetItemBySubCatResponse> = _searchItemsLive
    private var _errorSearchItemsLive = MutableLiveData<String>()
    var errorSearchItemsLive : LiveData<String> = _errorSearchItemsLive

    fun searchItem(searchItem : SearchItemBody) = viewModelScope.launch(Dispatchers.IO) {

        try {

            val response = RetrofitInstance.api.searchItem(searchItem)

            if(response.isSuccessful){

                response.body()?.let { it ->

                    if(it.isNotEmpty()){
                        if (it[0].Status != "Failed"){
                            _searchItemsLive.postValue(it)
                        }else{
                            _searchItemsLive.postValue(GetItemBySubCatResponse().apply {
                                emptyList<GetItemBySubCatResponseItem>()
                            })

                        }
                    }

                }

            }else{
                _errorSearchItemsLive.postValue("Response not Successful ${response.errorBody()}")
            }


        }catch (e:Exception){
            _errorSearchItemsLive.postValue(e.message)
        }
    }


    private var _appMainBannersLive = MutableLiveData<MainBannerResponse>()
    var appMainBannersLive : LiveData<MainBannerResponse> = _appMainBannersLive
    private var _errorAppMainBannersLive = MutableLiveData<String>()
    var errorAppMainBannersLive : LiveData<String> = _errorAppMainBannersLive

    fun getAppMainBanners() = viewModelScope.launch(Dispatchers.IO) {

        try {

            val response = RetrofitInstance.api.getAppMainBanners()

            if(response.isSuccessful){

                response.body()?.let { it ->

                    if(it.isNotEmpty()){
                        if (it[0].Status != "Failed"){
                            _appMainBannersLive.postValue(it)
                        }else{
                            _appMainBannersLive.postValue(MainBannerResponse().apply {
                                emptyList<MainBannerResponseItem>()
                            })

                        }
                    }

                }

            }else{
                _errorAppMainBannersLive.postValue("Response not Successful ${response.errorBody()}")
            }


        }catch (e:Exception){
            _errorAppMainBannersLive.postValue(e.message)
        }
    }


}