package com.example.health.ui.activity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import com.example.health.R
import com.example.health.ui.fragment.ContextFragment
import com.example.health.ui.fragment.MenuFragment
import com.example.health.ui.fragment.ShoppingFragment
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    var mMenuFragment: MenuFragment? = null
    var mShoppingFragment: ShoppingFragment? = null
    var mContextFragment: ContextFragment? = null
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
            if (mContextFragment != null) {
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
        ivMenu.setOnClickListener {
            if (mMenuFragment == null) {
                mMenuFragment = MenuFragment()
            }
            showFragment(mMenuFragment!!)
        }
        ivContact.setOnClickListener {
            if (mContextFragment == null) {
                mContextFragment = ContextFragment()
            }
            showFragment(mContextFragment!!)
        }
        ivShopping.setOnClickListener {
            if (mShoppingFragment == null) {
                mShoppingFragment = ShoppingFragment()
            }
            showFragment(mShoppingFragment!!)
        }
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
