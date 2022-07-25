package com.shop.android.pujacart.ui.navigationdrawer

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.shop.android.pujacart.R
import com.shop.android.pujacart.adapter.OrderItemAdapter
import com.shop.android.pujacart.api.MainViewmodel
import com.shop.android.pujacart.data.cart.GetTempCartBody
import com.shop.android.pujacart.data.user.LoginUserResponseItem
import com.shop.android.pujacart.data.user.UserDetailsResponse
import com.shop.android.pujacart.databinding.*
import com.shop.android.pujacart.other.Constants
import com.shop.android.pujacart.other.MyDialog
import com.shop.android.pujacart.other.SharedPref

class MyProfileFragment : Fragment(R.layout.fragment_profile) {

    lateinit var binding: FragmentProfileBinding
    lateinit var viewmodel: MainViewmodel
    lateinit var myDialog: MyDialog
    lateinit var sharedPref: SharedPref
    lateinit var userData: LoginUserResponseItem
    lateinit var orderItemAdapter: OrderItemAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentProfileBinding.bind(view)

        setUI(view)

    }

    private fun setUI(view: View) {

        viewmodel = ViewModelProvider(this).get(MainViewmodel::class.java)

        myDialog = MyDialog(requireContext())

        sharedPref = SharedPref(requireContext())

        if(sharedPref.getUserAuthStatus()){

            userData = sharedPref.getUserData()

            viewmodel.getUserDetails(GetTempCartBody(userData.UserID))

        }else{
            Toast.makeText(requireContext(), "Please login", Toast.LENGTH_SHORT).show()
            Navigation.findNavController(requireActivity(),R.id.nav_host_fragment_content_main).navigate(R.id.action_myProfileFragment_to_loginFragment)
        }



        setCallbacks()

    }

    private fun setCallbacks() {

        viewmodel.getUserDetailsLive.observe(viewLifecycleOwner, Observer {

            setData(it)

        })

        viewmodel.errorGetUserDetailsLive.observe(viewLifecycleOwner, Observer {


            myDialog.showErrorAlertDialog(it)

        })


    }

    private fun setData(it: UserDetailsResponse) {

        binding.tvEmailData.text = it[0].EmailID
        binding.tvFirstnameData.text =  it[0].DisplayName
        binding.tvLastnameData.text =  it[0].DisplayName
        binding.tvPasswordData.text =  it[0].Password
        binding.tvMobilenumberData.text =  it[0].ContactNo

        binding.layoutAddress.tvAddress.text = "${it[0].DeliveryAddress}\n${it[0].BillingCity}\n${it[0].BillingState}\n${it[0].BillingPostcode}\nPhone : ${it[0].ContactNo}."


    }

}