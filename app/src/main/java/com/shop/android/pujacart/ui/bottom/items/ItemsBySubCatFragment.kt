package com.shop.android.pujacart.ui.bottom.items

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.shop.android.pujacart.MainActivity
import com.shop.android.pujacart.R
import com.shop.android.pujacart.adapter.CategoryAdapter
import com.shop.android.pujacart.adapter.ItemBySubCategoryAdapter
import com.shop.android.pujacart.adapter.SubCategoryAdapter
import com.shop.android.pujacart.api.MainViewmodel
import com.shop.android.pujacart.data.item.GetItemBySubCatBody
import com.shop.android.pujacart.databinding.FragmentCategoryBinding
import com.shop.android.pujacart.other.Constants
import com.shop.android.pujacart.other.MyDialog

class ItemsBySubCatFragment : Fragment(R.layout.fragment_category) {

    lateinit var binding : FragmentCategoryBinding
    lateinit var viewmodel : MainViewmodel
    lateinit var adapter : ItemBySubCategoryAdapter
    lateinit var myDialog : MyDialog
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentCategoryBinding.bind(view)

        (activity as MainActivity).setToolbarSubCategoryName()

        setUI(view)

    }

    private fun setUI(view: View) {

        viewmodel = ViewModelProvider(this).get(MainViewmodel::class.java)

        myDialog = MyDialog(requireContext())

        val glide = Glide.with(requireContext())

        adapter = ItemBySubCategoryAdapter(glide)

        binding.rvCategory.adapter = adapter
        binding.rvCategory.layoutManager = GridLayoutManager(requireContext(),2)

        viewmodel.getItemBySubCategory(GetItemBySubCatBody(0,Constants.subCategory.PSubCategoryID))

        viewmodel.itemBysubCategoryLive.observe(viewLifecycleOwner, Observer {

            binding.progressbar.visibility = View.INVISIBLE

            adapter.categoryList = it

        })

        viewmodel.errorItemBysubCategoryLive.observe(viewLifecycleOwner, Observer {

            binding.progressbar.visibility = View.INVISIBLE

            myDialog.showErrorAlertDialog(it)

        })

        adapter.setOnItemClickListener {
            Constants.curProduct = it
            Navigation.findNavController(requireActivity(),R.id.nav_host_fragment_content_main).navigate(R.id.action_itemsBySubCatFragment_to_viewProductFragment)
        }


    }


}