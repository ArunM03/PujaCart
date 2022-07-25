package com.shop.android.pujacart.ui.bottom

import android.os.Bundle
import android.service.autofill.UserData
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.shop.android.pujacart.MainActivity
import com.shop.android.pujacart.R
import com.shop.android.pujacart.adapter.CartAdapter
import com.shop.android.pujacart.api.MainViewmodel
import com.shop.android.pujacart.data.cart.*
import com.shop.android.pujacart.data.user.LoginUserResponseItem
import com.shop.android.pujacart.databinding.FragmentCartBinding
import com.shop.android.pujacart.other.Constants
import com.shop.android.pujacart.other.MyDialog
import com.shop.android.pujacart.other.SharedPref

class CartFragment : Fragment(R.layout.fragment_cart) {

    lateinit var binding : FragmentCartBinding
    lateinit var viewmodel : MainViewmodel
    lateinit var myDialog : MyDialog
    lateinit var glide : RequestManager
    lateinit var cartAdapter: CartAdapter
    lateinit var sharedPref : SharedPref
    lateinit var userData: LoginUserResponseItem
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentCartBinding.bind(view)

        setUI(view)

    }

    private fun setUI(view: View) {

        viewmodel = ViewModelProvider(this).get(MainViewmodel::class.java)

        myDialog = MyDialog(requireContext())

        glide = Glide.with(requireContext())

        sharedPref = SharedPref(requireContext())

        if(sharedPref.getUserAuthStatus()){
            userData = sharedPref.getUserData()
        }


        getCartItems()


        cartAdapter = CartAdapter(glide)

        binding.rvItems.adapter = cartAdapter
        binding.rvItems.layoutManager = LinearLayoutManager(requireContext())

        setCallbacks()

        cartAdapter.setOnItemClickListenerForDelete {
            showAlertDialogForDelete(it)
        }

        cartAdapter.setOnItemClickListenerForQuantity {

       //     Toast.makeText(requireContext(), "GcartId : ${it.GCartID} Quantity : ${it.Quantity}", Toast.LENGTH_SHORT).show()

            if(sharedPref.getUserAuthStatus() && sharedPref.getCartTransferStatus()){
                viewmodel.updateUserCartItems(
                    UserCartItemInserBody(
                        userData.UserID,
                        it.ProductID,
                        it.PSID,
                        it.SupplierID,
                        it.BrandID,
                        it.Quantity, //this is the quantity i'm sending
                        it.SalePrice.toFloat().toInt(),
                        it.DiscountAmount.toFloat().toInt(),
                        it.OfferType,
                        it.SalePrice.toFloat().toInt(),
                        it.BuyQty,
                        it.GetQty,
                        0
                    )
                )
            }else{
                viewmodel.updateGuestCartItems(
                    GuestCartItemInserBody(
                        it.GCartID,
                        it.ProductID,
                        it.PSID,
                        it.SupplierID,
                        it.BrandID,
                        it.Quantity, //this is the quantity i'm sending
                        it.SalePrice.toFloat().toInt(),
                        it.DiscountAmount.toFloat().toInt(),
                        it.OfferType,
                        it.SalePrice.toFloat().toInt(),
                        it.BuyQty,
                        it.GetQty,
                        0
                    )
                )
            }

            myDialog.showProgressDialog("Updating the cart...", this)
        }


        binding.fabProceedtobuy.setOnClickListener {

            if(!sharedPref.getUserAuthStatus()){

                Navigation.findNavController(requireActivity(),R.id.nav_host_fragment_content_main).navigate(R.id.action_cartFragment_to_loginFragment)

            }else{

                Navigation.findNavController(requireActivity(),R.id.nav_host_fragment_content_main).navigate(R.id.action_cartFragment_to_selectAddressFragment)

            }

        }

    }

    private fun getCartItems() {
        if(sharedPref.getUserAuthStatus() && sharedPref.getCartTransferStatus()){
            viewmodel.getTempCartItems(GetTempCartBody(userData.UserID))
        }else{
            viewmodel.getGuestCartItems(GetGuestCartItemsBody(sharedPref.getGuestID().toInt()))
        }
    }

    private fun setCallbacks() {

        viewmodel.getGuestCartItemLive.observe(viewLifecycleOwner, Observer {

            binding.progressbar.visibility = View.INVISIBLE

            sharedPref.saveItems(it.size)

         //   Toast.makeText(requireContext(), "$it", Toast.LENGTH_SHORT).show()

            if(it.isNotEmpty()){
                binding.ctEmtpylist.visibility = View.GONE
                binding.ctItems.visibility = View.VISIBLE
                binding.fabProceedtobuy.visibility  = View.VISIBLE
                binding.fabProceedtobuy.text = "Proceed to Buy (${it.size} items)"
            }else{
                binding.ctEmtpylist.visibility = View.VISIBLE
                binding.ctItems.visibility = View.GONE
                binding.fabProceedtobuy.visibility  = View.GONE
            }

            (activity as MainActivity).setCartBadge()

            cartAdapter.categoryList = it


        })

        viewmodel.errorGetGuestCartItemLive.observe(viewLifecycleOwner, Observer {

            binding.progressbar.visibility = View.INVISIBLE

            myDialog.showErrorAlertDialog(it)

        })

        viewmodel.updateGuestCartItemsLive.observe(viewLifecycleOwner, Observer {

            myDialog.dismissProgressDialog()

            getCartItems()

        })

        viewmodel.errorUpdateGuestCartItemsLive.observe(viewLifecycleOwner, Observer {

            myDialog.dismissProgressDialog()

            myDialog.showErrorAlertDialog(it)


        })

        viewmodel.getTempCartItemLive.observe(viewLifecycleOwner, Observer {

         //   Toast.makeText(requireContext(), "$it", Toast.LENGTH_SHORT).show()

            if(it.isNotEmpty()){

                binding.ctEmtpylist.visibility = View.GONE
                binding.ctItems.visibility = View.VISIBLE

                viewmodel.getUserCartItems(GetUserCartItemsBody(it[0].UserID,it[0].CartID))

                sharedPref.setTempCartData(it[0])

            }else{

                binding.ctEmtpylist.visibility = View.VISIBLE
                binding.ctItems.visibility = View.GONE

            }

        })

        viewmodel.errorGetTempCartItemLive.observe(viewLifecycleOwner, Observer {



            myDialog.showErrorAlertDialog(it)


        })

    }


    fun showAlertDialogForDelete(it: GetGuestCartItemResponseItem) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Hold on!")
            .setMessage("Are you sure you want to Delete this item  ?")
            .setPositiveButton("Yes") { _, _ ->
                myDialog.showProgressDialog("Please wait...Deleting", this)
                if(sharedPref.getUserAuthStatus() && sharedPref.getCartTransferStatus()){
                    viewmodel.updateUserCartItems(
                        UserCartItemInserBody(
                            userData.UserID,
                            it.ProductID,
                            it.PSID,
                            it.SupplierID,
                            it.BrandID,
                            0,
                            it.SalePrice.toFloat().toInt(),
                            it.DiscountAmount.toFloat().toInt(),
                            it.OfferType,
                            it.SalePrice.toFloat().toInt(),
                            it.BuyQty,
                            it.GetQty,
                            0
                        )
                    )
                }else{
                    viewmodel.updateGuestCartItems(
                        GuestCartItemInserBody(
                            it.GCartID,
                            it.ProductID,
                            it.PSID,
                            it.SupplierID,
                            it.BrandID,
                            0,
                            it.SalePrice.toFloat().toInt(),
                            it.DiscountAmount.toFloat().toInt(),
                            it.OfferType,
                            it.SalePrice.toFloat().toInt(),
                            it.BuyQty,
                            it.GetQty,
                            0
                        )
                    )
                }


            }
            .setNegativeButton("No",null)
            .show()
    }


}