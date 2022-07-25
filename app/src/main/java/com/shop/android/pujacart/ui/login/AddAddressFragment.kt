package com.shop.android.pujacart.ui.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.shop.android.pujacart.R
import com.shop.android.pujacart.api.MainViewmodel
import com.shop.android.pujacart.data.user.LoginUserResponseItem
import com.shop.android.pujacart.data.user.UserAddressBody
import com.shop.android.pujacart.databinding.FragmentAddaddressBinding
import com.shop.android.pujacart.databinding.FragmentLoginBinding
import com.shop.android.pujacart.other.MyDialog
import com.shop.android.pujacart.other.SharedPref

class AddAddressFragment : Fragment(R.layout.fragment_addaddress) {

    lateinit var binding : FragmentAddaddressBinding
    lateinit var viewmodel : MainViewmodel
    lateinit var myDialog : MyDialog
    lateinit var sharedPref : SharedPref
    lateinit var userData: LoginUserResponseItem
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentAddaddressBinding.bind(view)

        setUI(view)

    }

    private fun setUI(view: View) {

        viewmodel = ViewModelProvider(this).get(MainViewmodel::class.java)

        myDialog = MyDialog(requireContext())

        sharedPref = SharedPref(requireContext())

        if(sharedPref.getUserAuthStatus()){
            userData = sharedPref.getUserData()
        }

        setCallbacks()

        binding.btAddaddress.setOnClickListener {

            addAddress()

        }

    }

    private fun addAddress() {

        val addressName = binding.edAddressname.text.toString()
        val address = binding.edAddress.text.toString()
        val district = binding.edDistrict.text.toString()
        val state = binding.edState.text.toString()
        val postCode = binding.edPostcode.text.toString()
        val contactNo = binding.edContactno.text.toString()

        if(addressName.isNotEmpty() && address.isNotEmpty() && district.isNotEmpty() && state.isNotEmpty() && postCode.isNotEmpty() && contactNo.isNotEmpty()){

            viewmodel.insertUserAddress(UserAddressBody(userData.UserID,addressName,address,district,state,postCode,contactNo,"0.0","0.0",userData.UserName))

            myDialog.showProgressDialog("Adding...Please wait",this)

        }else{
            Toast.makeText(requireContext(), "Please enter all details", Toast.LENGTH_SHORT).show()
        }

    }

    private fun setCallbacks() {

        viewmodel.createUserLive.observe(viewLifecycleOwner, Observer {

            myDialog.dismissProgressDialog()

            if (it == "Success") {

                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()

                requireActivity().onBackPressed()

            } else {
                Toast.makeText(requireContext(), it[0].toString(), Toast.LENGTH_SHORT).show()
            }

        })

        viewmodel.errorCreateUserLive.observe(viewLifecycleOwner, Observer {

            myDialog.dismissProgressDialog()

            myDialog.showErrorAlertDialog(it)

        })

    }

 }