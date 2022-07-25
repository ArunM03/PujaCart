package com.shop.android.pujacart.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.bumptech.glide.RequestManager
import com.shop.android.pujacart.MainActivity
import com.shop.android.pujacart.R
import com.shop.android.pujacart.adapter.CartAdapter
import com.shop.android.pujacart.api.MainViewmodel
import com.shop.android.pujacart.data.cart.GetGuestCartItemsBody
import com.shop.android.pujacart.data.user.CreateUserBody
import com.shop.android.pujacart.data.user.LoginUserBody
import com.shop.android.pujacart.databinding.FragmentCartBinding
import com.shop.android.pujacart.databinding.FragmentRegisterBinding
import com.shop.android.pujacart.other.MyDialog
import com.shop.android.pujacart.other.SharedPref

class RegisterFragment : Fragment(R.layout.fragment_register) {

    lateinit var binding : FragmentRegisterBinding
    lateinit var viewmodel : MainViewmodel
    lateinit var myDialog : MyDialog
    lateinit var sharedPref : SharedPref
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentRegisterBinding.bind(view)

        setUI(view)

    }

    private fun setUI(view: View) {

        viewmodel = ViewModelProvider(this).get(MainViewmodel::class.java)

        myDialog = MyDialog(requireContext())

        sharedPref = SharedPref(requireContext())

        binding.cdCreateaccount.setOnClickListener {

            createUser()

        }

        setCallbacks()


        binding.tvLoginClick.setOnClickListener {
            Navigation.findNavController(requireActivity(),R.id.nav_host_fragment_content_main).navigate(R.id.action_registerFragment_to_loginFragment)
        }

    }

    private fun setCallbacks() {

        viewmodel.createUserLive.observe(viewLifecycleOwner, Observer {

           myDialog.dismissProgressDialog()

            Toast.makeText(requireContext(), "$it", Toast.LENGTH_SHORT).show()

            if(it == "Success"){

               loginUser()
            }else{
                Toast.makeText(requireContext(), it[0].toString(), Toast.LENGTH_SHORT).show()
            }

        })

        viewmodel.errorCreateUserLive.observe(viewLifecycleOwner, Observer {

            myDialog.dismissProgressDialog()

            myDialog.showErrorAlertDialog(it)

        })


        viewmodel.loginUserLive.observe(viewLifecycleOwner, Observer {

            myDialog.dismissProgressDialog()

           // Toast.makeText(requireContext(), "$it", Toast.LENGTH_SHORT).show()

            if (it.isNotEmpty()){

                if(it[0].Status == "Success"){

                    sharedPref.setUserData(it[0])

                    startActivity(Intent(requireContext(), MainActivity::class.java))
                    requireActivity().finish()

                }else{
                    Toast.makeText(requireContext(), it[0].Status, Toast.LENGTH_SHORT).show()
                }

            }

        })

        viewmodel.errorLoginUserLive.observe(viewLifecycleOwner, Observer {

            myDialog.dismissProgressDialog()

            myDialog.showErrorAlertDialog(it)

        })

    }

    private fun loginUser() {

        val gmail = binding.edEmail.text.toString()
        val password = binding.edPassword.text.toString()

        if(gmail.isNotEmpty() && password.isNotEmpty()){

            viewmodel.loginUser(LoginUserBody(gmail,password))

            myDialog.showProgressDialog("Loging In...Please wait",this)

        }else{
            Toast.makeText(requireContext(), "Please enter all details", Toast.LENGTH_SHORT).show()
        }

    }

    private fun createUser() {

        val gmail = binding.edEmail.text.toString()
        val password = binding.edPassword.text.toString()
        val confirmPassword = binding.edConfirmpassword.text.toString()
        val mobile = binding.edMobilenumber.text.toString()

        if(gmail.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty() && mobile.isNotEmpty()){

            if(password != confirmPassword){
                Toast.makeText(requireContext(), "Password not matching", Toast.LENGTH_SHORT).show()
                return
            }

            viewmodel.createUser(CreateUserBody(gmail,password,mobile))

            myDialog.showProgressDialog("Registering...Please wait",this)

        }else{
            Toast.makeText(requireContext(), "Please enter all details", Toast.LENGTH_SHORT).show()
        }

    }


}