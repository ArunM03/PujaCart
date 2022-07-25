package com.shop.android.pujacart.ui.navigationdrawer.orders

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.shop.android.pujacart.R
import com.shop.android.pujacart.adapter.OrderHistoryAdapter
import com.shop.android.pujacart.adapter.OrderItemAdapter
import com.shop.android.pujacart.api.MainViewmodel
import com.shop.android.pujacart.data.order.GetOrderDetailsResponse
import com.shop.android.pujacart.data.order.GetOrderItemDetailsBody
import com.shop.android.pujacart.data.order.GetOrderItemDetailsResponse
import com.shop.android.pujacart.data.user.LoginUserResponseItem
import com.shop.android.pujacart.databinding.FragmentMyordersBinding
import com.shop.android.pujacart.databinding.FragmentOrderdetailsBinding
import com.shop.android.pujacart.other.Constants
import com.shop.android.pujacart.other.MyDialog
import com.shop.android.pujacart.other.SharedPref

class OrderDetailsFragment : Fragment(R.layout.fragment_orderdetails) {

    lateinit var binding : FragmentOrderdetailsBinding
    lateinit var viewmodel : MainViewmodel
    lateinit var myDialog : MyDialog
    lateinit var sharedPref : SharedPref
    lateinit var userData: LoginUserResponseItem
    lateinit var orderItemAdapter: OrderItemAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentOrderdetailsBinding.bind(view)

        setUI(view)

    }

    private fun setUI(view: View) {

        viewmodel = ViewModelProvider(this).get(MainViewmodel::class.java)

        myDialog = MyDialog(requireContext())

        sharedPref = SharedPref(requireContext())

        userData = sharedPref.getUserData()

        orderItemAdapter = OrderItemAdapter()

        viewmodel.getOrderItemDetails(GetOrderItemDetailsBody(Constants.getOrderDetailsResponseItem.OrderID))


        binding.rvItems.adapter = orderItemAdapter
        binding.rvItems.layoutManager = LinearLayoutManager(requireContext())

        binding.rvItems.isNestedScrollingEnabled = false


        setCallbacks()


    }

    private fun setCallbacks() {

        viewmodel.getOrderItemDetailsLive.observe(viewLifecycleOwner, Observer {

            binding.progressbar.visibility = View.INVISIBLE
            binding.ctParent.visibility = View.VISIBLE

            setData(it)

            orderItemAdapter.categoryList = it

        })

        viewmodel.errorCreateUserLive.observe(viewLifecycleOwner, Observer {

            binding.progressbar.visibility = View.INVISIBLE

            myDialog.showErrorAlertDialog(it)

        })

    }

    private fun setData(it: GetOrderItemDetailsResponse) {

        binding.tvOrdernoData.text = it[0].OrderNo.toString()

        val orderDetails = Constants.getOrderDetailsResponseItem

        binding.tvDateData.text = orderDetails.OrderCreatedDate

        binding.tvAddressData.text = orderDetails.DeliveryAddress

        orderItemAdapter.categoryList = it

        binding.tvTotalpriceData.text = orderDetails.TotalPrice.toString()
        binding.tvDeliverychargeData.text = orderDetails.DeliveryCharge.toString()
        binding.tvPayableamountData.text = orderDetails.PayableAmount.toString()
        binding.tvTotaldiscountData.text = orderDetails.Discount.toString()

    }

}