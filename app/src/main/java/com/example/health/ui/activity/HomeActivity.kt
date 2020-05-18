package com.example.health.ui.activity

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.example.health.R
import com.example.health.mode.PurchaseData
import com.example.health.mode.PurchaseInfo
import com.example.health.mode.ShopData
import com.example.health.ui.MyAppliction
import com.example.health.ui.fragment.*
import com.google.gson.Gson
import com.ucloudrtclib.sdkengine.UCloudRtcSdkEnv
import com.ucloudrtclib.sdkengine.define.UCloudRtcSdkMode
import com.urtcdemo.utils.CommonUtils
import com.urtcdemo.utils.PermissionUtils
import com.urtcdemo.utils.ToastUtils
import kotlinx.android.synthetic.main.activity_home.*
import java.io.*
import java.lang.ref.WeakReference
import java.util.*

class HomeActivity : AppCompatActivity() {
    var mMenuFragment: MenuFragment? = MenuFragment()
    var mShoppingFragment: ShoppingFragment? = ShoppingFragment()
    var mShoppingInfoFragment: ShoppingInfoFragment? = ShoppingInfoFragment()
    var mShoppingResultFragment: ShoppingResultFragment? = ShoppingResultFragment()
    var mContextFragment: ContextFragment? = null
    var mVideoCallFragment: VideoCallFragment? = VideoCallFragment()
    var mPurchaseFragment: PurchaseFragment? = PurchaseFragment()
    var mFragmentManager: FragmentManager? = null
    private var mUserId = ""
    private var mRoomid = "13821686815"
    private var mRoomToken = "testoken"
    private var mAppid: String? = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        initUCloudRtcSdk()
        requestPermissions()
        initView()
        initListener()
    }

    private fun initUCloudRtcSdk() {
        val preferences = getSharedPreferences(getString(R.string.app_name),
                Context.MODE_PRIVATE)
        mAppid = preferences.getString(CommonUtils.APPID_KEY, CommonUtils.APP_ID)
        UCloudRtcSdkEnv.setMixSupport(preferences.getBoolean(CommonUtils.SDK_SUPPORT_MIX, false))
        UCloudRtcSdkEnv.setLoop(preferences.getBoolean(CommonUtils.SDK_IS_LOOP, true))
        UCloudRtcSdkEnv.setMixFilePath(preferences.getString(CommonUtils.SDK_MIX_FILE_PATH, resources.getString(R.string.mix_file_path)))
        UCloudRtcSdkEnv.setLogReport(true)
        val permissions = arrayOf(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        PermissionUtils.needsPermissions(this, permissions)
        val thread = Thread(CopyMixFileTask(this))
        thread.start()
    }

    internal class CopyMixFileTask(context: AppCompatActivity) : Runnable {

        var context: WeakReference<AppCompatActivity>? = null

        init {
            this.context = WeakReference(context)
        }

        override fun run() {
            if (context != null && context!!.get() != null) {
                val storageFileDir = context!!.get()?.getResources()?.getString(R.string.mix_file_dir)
                val storageFilePath = storageFileDir + File.separator + "dy.mp3"
                val fileStorage = File(storageFilePath)
                var needCopy = false
                if (!fileStorage.exists()) {
                    needCopy = true
                }
                val handler = Handler(Looper.getMainLooper())
                if (needCopy) {
                    val file = File(storageFileDir)
                    if (!file.exists()) {//如果文件夹不存在，则创建新的文件夹
                        file.mkdirs()
                    }
                    readInputStream(storageFilePath, context!!.get()?.getResources()?.openRawResource(R.raw.dy)!!)
                    handler.post {
                        if (UCloudRtcSdkEnv.getApplication() != null) {
                            ToastUtils.shortShow(UCloudRtcSdkEnv.getApplication(), "default mix file copy success")
                        }
                    }
                } else {
                    //                    handler.post(new Runnable() {
                    //                        @Override
                    //                        public void run() {
                    //                            ToastUtils.shortShow(UCloudRtcSdkEnv.getApplication(),"mix file already exist");
                    //                        }
                    //                    });
                }
                context!!.clear()
                context = null
            }
        }

        /**
         * 读取输入流中的数据写入输出流
         *
         * @param storagePath 目标文件路径
         * @param inputStream 输入流
         */
        fun readInputStream(storagePath: String, inputStream: InputStream) {
            val file = File(storagePath)
            try {
                if (!file.exists()) {
                    // 1.建立通道对象
                    val fos = FileOutputStream(file)
                    // 2.定义存储空间
                    val buffer = ByteArray(1024)
                    // 3.开始读文件
                    var length = 0
                    // 循环从输入流读取buffer字节
                    // 将Buffer中的数据写到outputStream对象中
                    while ((inputStream.read(buffer)) != -1) {
                        length = inputStream.read(buffer)
                        fos.write(buffer, 0, length)
                    }
                    fos.flush()// 刷新缓冲区
                    // 4.关闭流
                    fos.close()
                    inputStream.close()
                }
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
    }

    private fun initView() {
        if (mFragmentManager == null) {
            mFragmentManager = supportFragmentManager
        }
        showMenuFragment()
//        if (mMenuFragment == null) {
//            mMenuFragment = MenuFragment()
//        }
//        showFragment(mMenuFragment!!)
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

        ivVoiceCallOff.setOnClickListener {
            val purchaseSP = getSharedPreferences("purchaseJson", Context.MODE_PRIVATE)
            //第一个参数是键名，第二个是默认值
            val purchaseData = purchaseSP?.getString("purchaseDate", "")
            Log.d("MenuFragment", "purchaseDate:$purchaseData")
            if (!purchaseData.isNullOrEmpty()) {
                val gson = Gson()
                val purchaseInfo = gson.fromJson(purchaseData, PurchaseInfo::class.java)
                val time1 = purchaseInfo.validity

                val currentThreadTimeMillis = System.currentTimeMillis()
                Log.d("MenuFragment", "time1:$time1...currentThreadTimeMillis:$currentThreadTimeMillis")
                if (time1 - currentThreadTimeMillis > 60000) {
                    if (mVideoCallFragment == null) {
                        mVideoCallFragment = VideoCallFragment()
                    }
                    if (UCloudRtcSdkEnv.getSdkMode() == UCloudRtcSdkMode.UCLOUD_RTC_SDK_MODE_TRIVAL) {
                        mRoomToken = "testoken"
                    }
                    val autoGenUserId = "android_" + UUID.randomUUID().toString().replace("-", "")
                    mUserId = (if (MyAppliction.getUserId() != null) MyAppliction.getUserId() else autoGenUserId)!!
                    mVideoCallFragment?.setData(mUserId, mRoomid, mRoomToken, mAppid);
                    showVideoCallFragment(mVideoCallFragment!!)
                } else {
                    if (mPurchaseFragment == null) {
                        mPurchaseFragment = PurchaseFragment()
                    }
                    showFragment(mPurchaseFragment!!)
                }
            } else {
                if (mPurchaseFragment == null) {
                    mPurchaseFragment = PurchaseFragment()
                }
                showFragment(mPurchaseFragment!!)
            }
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

        if (mPurchaseFragment != null) {
            mPurchaseFragment?.setBuyListener(object : PurchaseFragment.BuyListener {
                override fun buyClick(data: PurchaseData?) {
//                    if (mMenuFragment == null) {
//                        mMenuFragment = MenuFragment()
//                    }
//                    showFragment(mMenuFragment!!)
                    showMenuFragment()
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

    private fun showVideoCallFragment(mVideoCallFragment: VideoCallFragment) {
        ivContext2.visibility = View.GONE
        ivMenu2.visibility = View.GONE
        ivShopping2.visibility = View.GONE
        showFragment(mVideoCallFragment!!)
    }

    private fun replaceFragment(mPurchaseFragment: PurchaseFragment) {

    }

    public fun showMenuFragment() {
        val purchaseSP = getSharedPreferences("purchaseJson", Context.MODE_PRIVATE)
        //第一个参数是键名，第二个是默认值
        val purchaseData = purchaseSP?.getString("purchaseDate", "")
        Log.d("MenuFragment", "purchaseDate:$purchaseData")
        if (!purchaseData.isNullOrEmpty()) {
            val gson = Gson()
            val purchaseInfo = gson.fromJson(purchaseData, PurchaseInfo::class.java)
            val time1 = purchaseInfo.validity
            val currentThreadTimeMillis = System.currentTimeMillis()
            Log.d("MenuFragment", "time1:$time1...currentThreadTimeMillis:$currentThreadTimeMillis")
            if (time1 - currentThreadTimeMillis > 60000) {
                ivVoiceCallOff.setImageResource(R.drawable.videocall)
                tvVoiceCall.visibility = View.GONE
            } else {
                ivVoiceCallOff.setImageResource(R.drawable.videocalloff)
                tvVoiceCall.visibility = View.VISIBLE
            }
        } else {
            ivVoiceCallOff.setImageResource(R.drawable.videocalloff)
            tvVoiceCall.visibility = View.VISIBLE
        }
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
