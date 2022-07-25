package com.shop.android.pujacart.ui.navigationdrawer.orders

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.shop.android.pujacart.R
import com.shop.android.pujacart.adapter.OrderHistoryAdapter
import com.shop.android.pujacart.api.MainViewmodel
import com.shop.android.pujacart.data.cart.GetTempCartBody
import com.shop.android.pujacart.data.user.LoginUserResponseItem
import com.shop.android.pujacart.databinding.*
import com.shop.android.pujacart.other.Constants
import com.shop.android.pujacart.other.MyDialog
import com.shop.android.pujacart.other.SharedPref

class MyOrdersFragment : Fragment(R.layout.fragment_myorders) {

    lateinit var binding : FragmentMyordersBinding
    lateinit var viewmodel : MainViewmodel
    lateinit var myDialog : MyDialog
    lateinit var sharedPref : SharedPref
    lateinit var userData: LoginUserResponseItem
    lateinit var orderHistoryAdapter: OrderHistoryAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMyordersBinding.bind(view)

        setUI(view)

    }

    private fun setUI(view: View) {

        viewmodel = ViewModelProvider(this).get(MainViewmodel::class.java)

        myDialog = MyDialog(requireContext())

        sharedPref = SharedPref(requireContext())

        if(sharedPref.getUserAuthStatus()){

            userData = sharedPref.getUserData()

            viewmodel.getOrderDetails(GetTempCartBody(userData.UserID))

        }else{

            binding.ctEmtpylist.visibility = View.VISIBLE
            binding.ctItems.visibility = View.GONE

        }

        orderHistoryAdapter = OrderHistoryAdapter()



        setCallbacks()


        binding.rvItems.adapter = orderHistoryAdapter
        binding.rvItems.layoutManager = LinearLayoutManager(requireContext())


        orderHistoryAdapter.setOnItemClickListener {
            Constants.getOrderDetailsResponseItem = it

            Navigation.findNavController(requireActivity(),R.id.nav_host_fragment_content_main).navigate(R.id.action_myOrdersFragment_to_orderDetailsFragment)

        }

    }


    private fun setCallbacks() {

        viewmodel.getOrderDetailsLive.observe(viewLifecycleOwner, Observer {

            binding.progressbar.visibility = View.GONE

            if(it.isNotEmpty()){
                binding.ctEmtpylist.visibility = View.GONE
                binding.ctItems.visibility = View.VISIBLE
            }else{
                binding.ctEmtpylist.visibility = View.VISIBLE
                binding.ctItems.visibility = View.GONE
            }

            orderHistoryAdapter.categoryList = it


        })

        viewmodel.errorCreateUserLive.observe(viewLifecycleOwner, Observer {

            binding.progressbar.visibility = View.GONE

            myDialog.showErrorAlertDialog(it)

        })

    }


    }