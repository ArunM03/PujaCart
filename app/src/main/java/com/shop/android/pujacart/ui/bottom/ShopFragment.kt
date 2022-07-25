package com.shop.android.pujacart.ui.bottom

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.annotation.DimenRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.shop.android.pujacart.R
import com.shop.android.pujacart.adapter.CategoryAdapter
import com.shop.android.pujacart.adapter.ItemBySubCategoryAdapter
import com.shop.android.pujacart.adapter.ViewPageAdapter
import com.shop.android.pujacart.api.MainViewmodel
import com.shop.android.pujacart.data.item.GetHomeProductsBody
import com.shop.android.pujacart.data.item.GetItemBySubCatBody
import com.shop.android.pujacart.databinding.FragmentCategoryBinding
import com.shop.android.pujacart.databinding.FragmentShopBinding
import com.shop.android.pujacart.other.Constants
import com.shop.android.pujacart.other.MyDialog
import java.util.*

class ShopFragment : Fragment(R.layout.fragment_shop) {

    lateinit var binding : FragmentShopBinding
    lateinit var viewmodel : MainViewmodel
    lateinit var adapter : ItemBySubCategoryAdapter
    lateinit var categoryAdapter : CategoryAdapter
    lateinit var viewPageAdapter : ViewPageAdapter
    lateinit var myDialog : MyDialog

    var currentPage = 0
    var timer: Timer? = null
    val DELAY_MS: Long = 500 //delay in milliseconds before task is to be executed
    val PERIOD_MS: Long = 3000 // time in milliseconds between successive task executions.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentShopBinding.bind(view)

        setUI(view)

    }

    private fun setUI(view: View) {

        viewmodel = ViewModelProvider(this).get(MainViewmodel::class.java)

        myDialog = MyDialog(requireContext())

        val glide = Glide.with(requireContext())

        adapter = ItemBySubCategoryAdapter(glide)
        viewPageAdapter = ViewPageAdapter(glide)
        categoryAdapter = CategoryAdapter(glide)

        binding.rvItems.adapter = adapter
        binding.rvItems.layoutManager = GridLayoutManager(requireContext(),2)

        binding.rvCategories.adapter = categoryAdapter
        binding.rvCategories.layoutManager = GridLayoutManager(requireContext(),2)

        binding.vpMainBanner.adapter = viewPageAdapter

        viewmodel.getAppMainBanners()

        viewmodel.getHomeProducts(GetHomeProductsBody(454,"user@test.com"))

        viewmodel.getCategories()

        adapter.setOnItemClickListener {
            Constants.curProduct = it
            Navigation.findNavController(requireActivity(),R.id.nav_host_fragment_content_main).navigate(R.id.action_shopFragment_to_viewProductFragment)
        }

        viewPageAdapter.setOnItemClickListener {
            Constants.categoryID = it.BannerCategoryID
            Constants.categoryName = it.BannerCategory
            Navigation.findNavController(requireActivity(),R.id.nav_host_fragment_content_main).navigate(R.id.action_shopFragment_to_subCategoryFragment)
        }

        categoryAdapter.setOnItemClickListener {
            Constants.categoryID = it.PTypeID
            Constants.categoryName = it.PType
            Navigation.findNavController(requireActivity(),R.id.nav_host_fragment_content_main).navigate(R.id.action_shopFragment_to_subCategoryFragment)
        }


        setCallbacks()

        setViewPager()

    }

    fun setCallbacks(){

        viewmodel.homeProductsLive.observe(viewLifecycleOwner, Observer {

            binding.progressbar.visibility = View.INVISIBLE
            binding.rvItems.visibility  = View.VISIBLE
            binding.tvBestsellingproducts.visibility  = View.VISIBLE

            adapter.categoryList = it

        })

        viewmodel.errorHomeProductsLive.observe(viewLifecycleOwner, Observer {

            binding.progressbar.visibility = View.INVISIBLE

            myDialog.showErrorAlertDialog(it)

        })

        viewmodel.appMainBannersLive.observe(viewLifecycleOwner, Observer {

            viewPageAdapter.playlist = it
            binding.vpMainBanner.visibility = View.VISIBLE

         //   Toast.makeText(requireContext(), "$it", Toast.LENGTH_SHORT).show()

        })

        viewmodel.errorAppMainBannersLive.observe(viewLifecycleOwner, Observer {


            myDialog.showErrorAlertDialog(it)

        })


        viewmodel.categoriesLive.observe(viewLifecycleOwner, Observer {

            categoryAdapter.categoryList = it

            binding.rvCategories.visibility  = View.VISIBLE
            binding.tvCategories.visibility  = View.VISIBLE

        })

        viewmodel.errorCategoriesLive.observe(viewLifecycleOwner, Observer {

            myDialog.showErrorAlertDialog(it)

        })

    }

    fun setViewPager(){

        val handler = Handler()
        val Update = Runnable {
            if (currentPage === adapter.itemCount - 1) {
                currentPage = 0
            }
            if (binding.vpMainBanner != null){
                binding.vpMainBanner.setCurrentItem(currentPage++, true)
            }
        }

        timer = Timer() // This will create a new Thread

        timer!!.schedule(object : TimerTask() {
            // task to be scheduled
            override fun run() {
                handler.post(Update)
            }
        }, DELAY_MS, PERIOD_MS)

        binding.vpMainBanner.offscreenPageLimit = 1
        val nextItemVisiblePx = resources.getDimension(R.dimen.viewpager_next_item_visible)
        val currentItemHorizontalMarginPx = resources.getDimension(R.dimen.viewpager_current_item_horizontal_margin)
        val pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx
        val pageTransformer = ViewPager2.PageTransformer { page: View, position: Float ->
            page.translationX = -pageTranslationX * position
            page.scaleY = 1 - (0.25f * Math.abs(position))
        }
        if(pageTransformer != null){
            binding.vpMainBanner.setPageTransformer(pageTransformer)
        }
        val itemDecoration = HorizontalMarginItemDecoration(
            requireContext(),
            R.dimen.viewpager_current_item_horizontal_margin
        )
        binding.vpMainBanner.addItemDecoration(itemDecoration)
    }

}

class HorizontalMarginItemDecoration(context: Context, @DimenRes horizontalMarginInDp: Int) :
    RecyclerView.ItemDecoration() {

    private val horizontalMarginInPx: Int =
        context.resources.getDimension(horizontalMarginInDp).toInt()

    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
    ) {
        outRect.right = horizontalMarginInPx
        outRect.left = horizontalMarginInPx
    }

}