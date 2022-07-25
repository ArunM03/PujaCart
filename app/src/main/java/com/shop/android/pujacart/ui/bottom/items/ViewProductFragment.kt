package com.shop.android.pujacart.ui.bottom.items

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.shop.android.pujacart.R
import com.shop.android.pujacart.adapter.ItemBySubCategoryAdapter
import com.shop.android.pujacart.api.MainViewmodel
import com.shop.android.pujacart.data.item.GetHomeProductsBody
import com.shop.android.pujacart.data.item.GetItemBySubCatResponseItem
import com.shop.android.pujacart.databinding.FragmentCategoryBinding
import com.shop.android.pujacart.databinding.FragmentViewhistoryBinding
import com.shop.android.pujacart.other.Constants
import com.shop.android.pujacart.other.MyDialog
import android.provider.Settings.Secure
import androidx.lifecycle.Observer
import com.shop.android.pujacart.MainActivity
import com.shop.android.pujacart.data.cart.GuestCartItemInserBody
import com.shop.android.pujacart.data.cart.UserCartItemInserBody
import com.shop.android.pujacart.data.user.LoginUserResponseItem
import com.shop.android.pujacart.other.SharedPref


class ViewProductFragment   : Fragment(R.layout.fragment_viewhistory) {

    lateinit var binding : FragmentViewhistoryBinding
    lateinit var viewmodel : MainViewmodel
    lateinit var myDialog : MyDialog
    lateinit var glide : RequestManager
    lateinit var sharedPref : SharedPref
    var guestID = ""
    var quantity = 1
    lateinit var userData: LoginUserResponseItem
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentViewhistoryBinding.bind(view)

        setUI(view)

    }

    private fun setUI(view: View) {


        viewmodel = ViewModelProvider(this).get(MainViewmodel::class.java)

        myDialog = MyDialog(requireContext())

        sharedPref = SharedPref(requireContext())

        if(sharedPref.getUserAuthStatus()){
            userData = sharedPref.getUserData()
        }

        glide = Glide.with(requireContext())

        setData(Constants.curProduct)

    }

    @SuppressLint("SetTextI18n", "HardwareIds")
    private fun setData(curProduct: GetItemBySubCatResponseItem) {

        glide.load(curProduct.PImage).into(binding.ivProduct)

        glide.load(curProduct.PImage).into(binding.ivBrand)

        binding.tvProductname.text = curProduct.ProductName

        binding.tvProductprice.text = "₹${curProduct.PPriceTag}"

        binding.tvDescription.text = curProduct.PDescription

        binding.tvStock.text = "Instock : ${curProduct.PAvlQty} items"

        binding.tvBrandname.text = curProduct.BrandName



        val size = curProduct.PSizeType.split("/").toTypedArray()

        setSpinner(binding.spSize,size.toList())

        guestID = sharedPref.getGuestID()


        binding.fabAddtocart.setOnClickListener {

            if(sharedPref.getUserAuthStatus() && sharedPref.getCartTransferStatus()){
                viewmodel.userInserCartItem(
                    UserCartItemInserBody(userData.UserID,curProduct.ProductID,curProduct.PSizeID.toInt(),curProduct.SupplierID,
                    curProduct.BrandID,quantity,curProduct.PPriceTag.toFloat().toInt(),curProduct.DiscountPrice.toInt(),"",0,0,0,0)
                )
            }else{
                viewmodel.guestInserCartItem(GuestCartItemInserBody(guestID.toInt(),curProduct.ProductID,curProduct.PSizeID.toInt(),curProduct.SupplierID,
                    curProduct.BrandID,quantity,curProduct.PPriceTag.toFloat().toInt(),curProduct.DiscountPrice.toInt(),"",0,0,0,0))
            }

            myDialog.showProgressDialog("Please wait...",this)

        }

        binding.ibAdd.setOnClickListener {
            quantity++
            binding.edQuantity.text = quantity.toString()
        }

        binding.ibMinus.setOnClickListener {
            if(quantity > 1){
                quantity--
                binding.edQuantity.text = quantity.toString()
            }else{
                Toast.makeText(requireContext(), "Cannot able to reduce it", Toast.LENGTH_SHORT).show()
            }
        }

        setCallbacks()

    }

    private fun setCallbacks() {


        viewmodel.insertGuestCartItemLive.observe(viewLifecycleOwner, Observer {

            myDialog.dismissProgressDialog()

            sharedPref.saveItems(sharedPref.getItems() + 1)

            (activity as MainActivity).setCartBadge()

            requireActivity().onBackPressed()

        })

        viewmodel.errorInsertGuestCartItemLive.observe(viewLifecycleOwner, Observer {

            myDialog.dismissProgressDialog()

            myDialog.showErrorAlertDialog(it)

        })

    }

    fun setSpinner(spinner: Spinner, spinnerList : List<String>) {
        val adapter = object :
            ArrayAdapter<Any>(
                requireContext(), R.layout.sp_layout,
                spinnerList
            ) {
            override fun getDropDownView(
                position: Int,
                convertView: View?,
                parent: ViewGroup
            ): View {
                return super.getDropDownView(position, convertView, parent).also {
                    if (position == spinner.selectedItemPosition) {
                        it.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.grey_100))
                    }
                }
            }
        }
        adapter.setDropDownViewResource(R.layout.sp_layout)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onNothingSelected(parent: AdapterView<*>?) {



            }

            override fun onItemSelected(parent: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

            }
        }
    }


}