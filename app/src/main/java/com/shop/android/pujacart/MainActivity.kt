package com.shop.android.pujacart

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.navigation.NavigationView
import com.shop.android.pujacart.api.MainViewmodel
import com.shop.android.pujacart.data.cart.GCartTransferBody
import com.shop.android.pujacart.data.item.GetHomeProductsBody
import com.shop.android.pujacart.databinding.ActivityMainBinding
import com.shop.android.pujacart.other.Constants
import com.shop.android.pujacart.other.MyDialog
import com.shop.android.pujacart.other.SharedPref

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var sharedPref : SharedPref
    lateinit var viewmodel : MainViewmodel
    lateinit var myDialog :MyDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUI()

    }

    private fun setUI() {

        setSupportActionBar(binding.appBarMain.toolbar)

        sharedPref = SharedPref(this)

        viewmodel = ViewModelProvider(this).get(MainViewmodel::class.java)

        myDialog = MyDialog(this)


        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.shopFragment, R.id.searchFragment,  R.id.categoryFragment, R.id.myOrdersFragment, R.id.myProfileFragment, R.id.cartFragment
            ), drawerLayout
        )

        navView.setupWithNavController(navController)

        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.appBarMain.layoutMain.bottomView.setupWithNavController(navController)

        hideMenuItems(navView)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.loginFragment, R.id.registerFragment -> {
                    hideBottomNavigation()
                    hideToolbar()
                }
                R.id.subCategoryFragment,R.id.viewProductFragment,R.id.itemsBySubCatFragment,R.id.addAddressFragment,R.id.selectAddressFragment -> {
                    showToolbar()
                    hideBottomNavigation()
                }
                else -> {
                    showToolbar()
                    showBottomNavigation()
                }
            }
        }





        setName(navView)

        setCartBadge()

        getGuestID()

        checkForCartTransfer()

        binding.navView.menu.findItem(R.id.logout).setOnMenuItemClickListener {
            showAlertDialog()
            true
        }

        binding.navView.menu.findItem(R.id.login).setOnMenuItemClickListener {
            Navigation.findNavController(this,R.id.nav_host_fragment_content_main).navigate(R.id.loginFragment)
            true
        }

    }

    fun setToolbarCategoryName(){
        binding.appBarMain.toolbar.setTitle(Constants.categoryName)
    }
    fun setToolbarSubCategoryName(){
        binding.appBarMain.toolbar.setTitle(Constants.subCategory.PSubCategoryName)
    }

    private fun setName(navView: NavigationView) {

        val headerView = navView.getHeaderView(0)
        val name = headerView.findViewById<TextView>(R.id.tv_name_home)

        if(sharedPref.getUserAuthStatus()){
            val userData = sharedPref.getUserData()
            if (userData.UserName != "New"){
                name.text = userData.UserName
            }
        }
    }

    private fun hideMenuItems(navView: NavigationView) {

        if(sharedPref.getUserAuthStatus()){
            val menu = navView.menu
            menu.findItem(R.id.login).isVisible = false
        }else{
            val menu = navView.menu
            menu.findItem(R.id.logout).isVisible = false
        }

    }

    private fun checkForCartTransfer() {

        if(sharedPref.getUserAuthStatus() && !sharedPref.getCartTransferStatus()){

            viewmodel.cartTransfer(GCartTransferBody(sharedPref.getGuestID(),sharedPref.getUserData().UserID))


            viewmodel.cartTransferLive.observe(this, Observer {

             //   Toast.makeText(this, it, Toast.LENGTH_SHORT).show()

                sharedPref.setCartTransferStatus(true)

            })


        }

    }

    fun showAlertDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle("Hold on!")
            .setMessage("Are you sure you want to Logout ?")
            .setPositiveButton("Yes"){  _,_ ->
                sharedPref.setUserAuthStatus(false)
                startActivity(Intent(this,MainActivity::class.java))
                finish()
            }
            .setNegativeButton("No",null)
            .show()
    }

    fun setCartBadge() {

        val cartItems = sharedPref.getItems()

        if (cartItems == 0) {
            binding.appBarMain.layoutMain.bottomView.removeBadge(R.id.cartFragment)
        } else {
            binding.appBarMain.layoutMain.bottomView.getOrCreateBadge(R.id.cartFragment).number =
                cartItems
        }


    }

    @SuppressLint("HardwareIds")
    fun getGuestID(){

        val guestId = sharedPref.getGuestID()

        if(guestId == "null"){

            val android_id = Settings.Secure.getString(
                contentResolver,
                Settings.Secure.ANDROID_ID
            )

            viewmodel.guestRegister(GetHomeProductsBody(1, "",android_id))

        }

        viewmodel.guestRegisterLive.observe(this, Observer {

            if(it.isNotEmpty()){
                sharedPref.saveGuestID(it[0].GCartID)
            }

        })

        viewmodel.errorGuestRegisterLive.observe(this, Observer {


            myDialog.showErrorAlertDialog(it)

        })


    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }



    fun hideBottomNavigation(){
        binding.appBarMain.layoutMain.bottomView.visibility = View.GONE
    }

    fun showBottomNavigation(){
        binding.appBarMain.layoutMain.bottomView.visibility = View.VISIBLE
    }


    fun hideToolbar(){
        binding.appBarMain.toolbar.visibility = View.GONE
    }

    fun showToolbar(){
        binding.appBarMain.toolbar.visibility = View.VISIBLE
    }




}