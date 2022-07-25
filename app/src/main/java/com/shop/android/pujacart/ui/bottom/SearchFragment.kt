package com.shop.android.pujacart.ui.bottom

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.shop.android.pujacart.R
import com.shop.android.pujacart.adapter.CartAdapter
import com.shop.android.pujacart.adapter.ItemBySubCategoryAdapter
import com.shop.android.pujacart.adapter.OrderHistoryAdapter
import com.shop.android.pujacart.api.MainViewmodel
import com.shop.android.pujacart.data.cart.GetTempCartBody
import com.shop.android.pujacart.data.item.SearchItemBody
import com.shop.android.pujacart.data.user.LoginUserResponseItem
import com.shop.android.pujacart.databinding.FragmentCartBinding
import com.shop.android.pujacart.databinding.FragmentCategoryBinding
import com.shop.android.pujacart.databinding.FragmentSearchBinding
import com.shop.android.pujacart.databinding.FragmentShopBinding
import com.shop.android.pujacart.other.Constants
import com.shop.android.pujacart.other.MyDialog
import com.shop.android.pujacart.other.SharedPref

class SearchFragment : Fragment(R.layout.fragment_search) {

    lateinit var binding : FragmentSearchBinding
    lateinit var viewmodel : MainViewmodel
    lateinit var myDialog : MyDialog
    lateinit var glide : RequestManager
    lateinit var itemsBySubCategoryAdapter: ItemBySubCategoryAdapter
    lateinit var sharedPref : SharedPref
    lateinit var userData: LoginUserResponseItem
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSearchBinding.bind(view)

        setUI(view)

    }

    private fun setUI(view: View) {

        viewmodel = ViewModelProvider(this).get(MainViewmodel::class.java)

        myDialog = MyDialog(requireContext())

        sharedPref = SharedPref(requireContext())

        val glide = Glide.with(requireContext())

        if(sharedPref.getUserAuthStatus()){
            userData = sharedPref.getUserData()
        }

        itemsBySubCategoryAdapter = ItemBySubCategoryAdapter(glide)

        setCallbacks()

        binding.rvItems.adapter = itemsBySubCategoryAdapter
        binding.rvItems.layoutManager = LinearLayoutManager(requireContext())

        binding.ibSearch.setOnClickListener {

            val searchWord = binding.edSearchword.text.toString()

            if (searchWord.isNotEmpty()){

                binding.ctEmtpylist.visibility = View.GONE
                binding.progressbarItems.visibility = View.VISIBLE

                if(sharedPref.getUserAuthStatus()){
                    viewmodel.searchItem(SearchItemBody(userData.UserID,searchWord))
                }else{
                    viewmodel.searchItem(SearchItemBody(0,searchWord))
                }


            }else{

                Toast.makeText(requireContext(), "Please enter text", Toast.LENGTH_SHORT).show()

            }

        }

        itemsBySubCategoryAdapter.setOnItemClickListener {
            Constants.curProduct = it
            Navigation.findNavController(requireActivity(),R.id.nav_host_fragment_content_main).navigate(R.id.action_searchFragment_to_viewProductFragment)
        }


    }

    private fun setCallbacks() {

        viewmodel.searchItemsLive.observe(viewLifecycleOwner, Observer {

            binding.progressbarItems.visibility = View.INVISIBLE

            itemsBySubCategoryAdapter.categoryList = it

            if(it.isEmpty()){
                binding.ctItemslist.visibility = View.GONE
                binding.ctEmtpylist.visibility = View.VISIBLE
                binding.tvNote.text = "No items found"
            }else{
                binding.ctItemslist.visibility = View.VISIBLE
                binding.ctEmtpylist.visibility = View.GONE
            }

        })

        viewmodel.errorSearchItemsLive.observe(viewLifecycleOwner, Observer {

            binding.progressbarItems.visibility = View.INVISIBLE

            myDialog.showErrorAlertDialog(it)

        })


    }


}