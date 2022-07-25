package com.shop.android.pujacart.ui.bottom.order

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.shop.android.pujacart.MainActivity
import com.shop.android.pujacart.R
import com.shop.android.pujacart.adapter.AddressAdapter
import com.shop.android.pujacart.api.MainViewmodel
import com.shop.android.pujacart.data.cart.GetTempCartBody
import com.shop.android.pujacart.data.cart.GetTempCartResponseItem
import com.shop.android.pujacart.data.order.InsertOrderBody
import com.shop.android.pujacart.data.user.GetUserAddressResponseItem
import com.shop.android.pujacart.data.user.LoginUserResponseItem
import com.shop.android.pujacart.databinding.FragmentSelectaddressBinding
import com.shop.android.pujacart.other.Constants
import com.shop.android.pujacart.other.MyDialog
import com.shop.android.pujacart.other.SharedPref

class SelectAddressFragment : Fragment(R.layout.fragment_selectaddress) {

    lateinit var binding : FragmentSelectaddressBinding
    lateinit var viewmodel : MainViewmodel
    lateinit var myDialog : MyDialog
    lateinit var glide : RequestManager
    lateinit var addresAdapter: AddressAdapter
    lateinit var sharedPref : SharedPref
    lateinit var userData: LoginUserResponseItem
    lateinit var getTempCartResponseItem: GetTempCartResponseItem
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSelectaddressBinding.bind(view)

        setUI(view)

    }

    private fun setUI(view: View) {

        viewmodel = ViewModelProvider(this).get(MainViewmodel::class.java)

        myDialog = MyDialog(requireContext())

        glide = Glide.with(requireContext())

        sharedPref = SharedPref(requireContext())

        userData = sharedPref.getUserData()

        getTempCartResponseItem = sharedPref.getTempCartData()

        addresAdapter = AddressAdapter()

        binding.rvAddress.adapter = addresAdapter
        binding.rvAddress.layoutManager = LinearLayoutManager(requireContext())

        viewmodel.getUserAddress(GetTempCartBody(userData.UserID))

        setCallbacks()


        binding.fabAddaddress.setOnClickListener {
            Navigation.findNavController(requireActivity(),R.id.nav_host_fragment_content_main).navigate(R.id.action_selectAddressFragment_to_addAddressFragment)
        }

        addresAdapter.setOnItemClickListener {
            insertOrder(it)
        }

    }

    private fun insertOrder(getUserAddressResponseItem: GetUserAddressResponseItem) {

        viewmodel.insertOrder(InsertOrderBody("","",getUserAddressResponseItem.DelAddressID,getTempCartResponseItem.NoofItems,0,getTempCartResponseItem.PayableAmount.toInt(),0,"0",0,getTempCartResponseItem.CartID,getTempCartResponseItem.TotalPrice.toInt(),userData.UserID))

        myDialog.showProgressDialog("Order Placing...Please wait",this)

        viewmodel.insertOrderLive.observe(viewLifecycleOwner, Observer {

            myDialog.dismissProgressDialog()

            sharedPref.saveItems(0)

            (activity as MainActivity).setCartBadge()

          //  Toast.makeText(requireContext(), it[0].Status, Toast.LENGTH_SHORT).show()

            Navigation.findNavController(requireActivity(),R.id.nav_host_fragment_content_main).navigate(R.id.myOrdersFragment)

        })

        viewmodel.errorInsertOrderLive.observe(viewLifecycleOwner, Observer {

            myDialog.dismissProgressDialog()

            myDialog.showErrorAlertDialog(it)


        })

    }

    private fun setCallbacks() {

        viewmodel.getUserAddressLive.observe(viewLifecycleOwner, Observer {

            binding.progressbar.visibility = View.GONE

            addresAdapter.categoryList = it

        })

        viewmodel.errorGetUserAddressLive.observe(viewLifecycleOwner, Observer {

            binding.progressbar.visibility = View.GONE

            myDialog.showErrorAlertDialog(it)


        })


    }

}