package com.example.health.ui.activity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.health.R
import com.example.health.ui.fragment.*
import com.example.health.ui.mode.ShopData
import kotlinx.android.synthetic.main.activity_home.*
import java.util.*

class HomeActivity : AppCompatActivity() {
    var mMenuFragment: MenuFragment? = MenuFragment()
    var mShoppingFragment: ShoppingFragment? = ShoppingFragment()
    var mShoppingInfoFragment: ShoppingInfoFragment? = ShoppingInfoFragment()
    var mShoppingResultFragment: ShoppingResultFragment? = ShoppingResultFragment()
    var mContextFragment: ContextFragment? = null
    var mPurchaseFragment: PurchaseFragment? = null
    var mFragmentManager: FragmentManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        requestPermissions()
        initView()
        initListener()
    }

    private fun initView() {
        if (mFragmentManager == null) {
            mFragmentManager = supportFragmentManager
        }
        if (mMenuFragment == null) {
            mMenuFragment = MenuFragment()
        }
        showFragment(mMenuFragment!!)
    }

    private var currentFragment: Fragment? = null

    /**
     * 展示Fragment
     */
    private fun showFragment(fragment: Fragment) {

        if (currentFragment !== fragment) {
            val transaction = mFragmentManager?.beginTransaction()
            if (currentFragment != null) {
                transaction?.hide(currentFragment)
            }
            currentFragment = fragment
            if (!fragment.isAdded) {
                transaction?.add(R.id.frameLayout, fragment)?.show(fragment)?.commit()
            } else {
                transaction?.show(fragment)?.commit()
            }
        }
    }

    private fun initListener() {
        llMenu2.setOnClickListener {
            showMenuFragment()
        }
        llContact2.setOnClickListener {
            ivContext2.visibility = View.VISIBLE
            ivMenu2.visibility = View.GONE
            ivShopping2.visibility = View.GONE
            if (mContextFragment == null) {
                mContextFragment = ContextFragment()
            }
            showFragment(mContextFragment!!)
        }
        llShopping2.setOnClickListener {
            ivContext2.visibility = View.GONE
            ivMenu2.visibility = View.GONE
            ivShopping2.visibility = View.VISIBLE
            if (mShoppingFragment == null) {
                mShoppingFragment = ShoppingFragment()
            }
            showFragment(mShoppingFragment!!)
        }


        if (mMenuFragment != null) {
            mMenuFragment?.setRegisteredListener(object : MenuFragment.RegisteredListener {
                override fun registered() {
                    if (mPurchaseFragment == null) {
                        mPurchaseFragment = PurchaseFragment()
                    }
                    showFragment(mPurchaseFragment!!)
                }
            })
        }

        if (mShoppingFragment != null) {
            mShoppingFragment?.setBuyListener(object : ShoppingFragment.BuyListener {
                override fun buyClick(data: ShopData) {
                    if (mShoppingInfoFragment == null) {
                        mShoppingInfoFragment = ShoppingInfoFragment()
                    }
                    showFragment(mShoppingInfoFragment!!)
                }
            })
        }
        if (mShoppingInfoFragment != null) {
            mShoppingInfoFragment?.setSubmitListener(object : ShoppingInfoFragment.SubmitListener {
                override fun submitClick() {
                    if (mShoppingResultFragment == null) {
                        mShoppingResultFragment = ShoppingResultFragment()
                    }
                    showFragment(mShoppingResultFragment!!)
                }
            })
        }

        if (mShoppingResultFragment != null) {
            mShoppingResultFragment?.setBackHomeListener(object : ShoppingResultFragment.BackHomeListener {
                override fun backClick() {
                    showMenuFragment()
                }
            })
        }
    }

    private fun showMenuFragment() {
        ivContext2.visibility = View.GONE
        ivMenu2.visibility = View.VISIBLE
        ivShopping2.visibility = View.GONE
        if (mMenuFragment == null) {
            mMenuFragment = MenuFragment()
        }
        showFragment(mMenuFragment!!)
    }

    private fun requestPermissions() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val permission = ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                if (permission != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.LOCATION_HARDWARE, Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_SETTINGS, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_CONTACTS), 0x0010)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
