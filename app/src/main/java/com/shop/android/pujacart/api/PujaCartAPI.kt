package com.shop.android.pujacart.api

import com.shop.android.pujacart.data.banner.MainBannerResponse
import com.shop.android.pujacart.data.cart.*
import com.shop.android.pujacart.data.category.CategoryResponse
import com.shop.android.pujacart.data.category.SubCategoryBody
import com.shop.android.pujacart.data.category.SubCategoryResponse
import com.shop.android.pujacart.data.item.GetHomeProductsBody
import com.shop.android.pujacart.data.item.GetItemBySubCatBody
import com.shop.android.pujacart.data.item.GetItemBySubCatResponse
import com.shop.android.pujacart.data.item.SearchItemBody
import com.shop.android.pujacart.data.order.GetOrderDetailsResponse
import com.shop.android.pujacart.data.order.GetOrderItemDetailsBody
import com.shop.android.pujacart.data.order.GetOrderItemDetailsResponse
import com.shop.android.pujacart.data.order.InsertOrderBody
import com.shop.android.pujacart.data.user.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface PujaCartAPI {

    @GET("api/Home/GetCategories")
    suspend fun getCategories(): Response<CategoryResponse>

    @POST("api/Home/GetSubCategories")
    suspend fun getSubCategories(
        @Body
        subCategoryBody: SubCategoryBody
    ): Response<SubCategoryResponse>

    @POST("api/Home/GetItemListBySubCategory")
    suspend fun getItemListBySubCategory(
        @Body
        getItemBySubCatBody: GetItemBySubCatBody
    ): Response<GetItemBySubCatResponse>

    @POST("api/Home/GetHomeProducts")
    suspend fun getHomeProducts(
        @Body
        getHomeProductsBody: GetHomeProductsBody
    ): Response<GetItemBySubCatResponse>

    @POST("api/Home/GuestRegistration")
    suspend fun guestRegister(
        @Body
        getHomeProductsBody: GetHomeProductsBody
    ): Response<GuestRegisterResponse>

    @POST("api/Home/GuestCartItemInsert")
    suspend fun insertGuestCartItem(
        @Body
        guestCartItemInserBody: GuestCartItemInserBody
    ): Response<GuestInsertCartResponse>

    @POST("api/Home/GetGuestCartItems")
    suspend fun getGuestCartItems(
        @Body
        getGuestCartItemsBody: GetGuestCartItemsBody
    ): Response<GetGuestCartItemResponse>

    @POST("api/Home/GuestCartItemUpdate")
    suspend fun updateGuestCartItems(
        @Body
        guestCartItemInserBody: GuestCartItemInserBody
    ): Response<GuestInsertCartResponse>


    @POST("api/Home/UserInsert")
    suspend fun createUser(
        @Body
        createUserBody: CreateUserBody
    ): Response<CreateUserResponse>


    @POST("api/Home/UserCheck")
    suspend fun loginUser(
        @Body
        loginUserBody: LoginUserBody
    ): Response<LoginUserResponse>


    @POST("api/Home/GCartTransfer")
    suspend fun cartTransfer(
        @Body
        gCartTransferBody: GCartTransferBody
    ): Response<CreateUserResponse>

    @POST("api/Home/GetTempCartItems")
    suspend fun getUserCartItems(
        @Body
        getUserCartItemsBody: GetUserCartItemsBody
    ): Response<GetGuestCartItemResponse>


    @POST("api/Home/GetTempCart")
    suspend fun getTempCart(
        @Body
        getTempCartBody: GetTempCartBody
    ): Response<GetTempCartResponse>

    @POST("api/Home/TempCartItemInsert")
    suspend fun insertUserCartItem(
        @Body
        userCartItemInserBody: UserCartItemInserBody
    ): Response<GuestInsertCartResponse>

    @POST("api/Home/TempCartItemUpdate")
    suspend fun updateUserCartItem(
        @Body
        userCartItemInserBody: UserCartItemInserBody
    ): Response<GuestInsertCartResponse>

    @POST("api/Home/UserDeliveryAddressInsert")
    suspend fun insertDeliveryAddress(
        @Body
        userAddressBody: UserAddressBody
    ): Response<CreateUserResponse>

    @POST("api/Home/GetUserDeliveryAddresses")
    suspend fun getDeliveryAddress(
        @Body
        getTempCartBody: GetTempCartBody
    ): Response<GetUserAddressResponse>


    @POST("api/Home/OrderInsert")
    suspend fun insertOrder(
        @Body
        insertOrderBody: InsertOrderBody
    ): Response<CreateUserResponse>


    @POST("api/Home/GetOrderDetails")
    suspend fun getOrderDetails(
        @Body
        getTempCartBody: GetTempCartBody
    ): Response<GetOrderDetailsResponse>

    @POST("api/Home/GetOrderItemDetails")
    suspend fun getOrderItemDetails(
        @Body
        getOrderItemDetailsBody: GetOrderItemDetailsBody
    ): Response<GetOrderItemDetailsResponse>

    @POST("api/Home/GetUserDetails")
    suspend fun getUserDetails(
        @Body
        getTempCartBody: GetTempCartBody
    ): Response<UserDetailsResponse>


    @POST("api/Home/GetSearchItemList")
    suspend fun searchItem(
        @Body
        searchItemBody: SearchItemBody
    ): Response<GetItemBySubCatResponse>


    @POST("api/Home/GetAppMainBanners")
    suspend fun getAppMainBanners(
    ): Response<MainBannerResponse>


}