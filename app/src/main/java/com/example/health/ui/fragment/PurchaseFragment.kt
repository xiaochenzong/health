package com.example.health.ui.fragment


import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.os.SystemClock
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.health.R
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.example.health.ui.adapter.ShoppingAdapter
import com.example.health.ui.mode.ShopData
import kotlinx.android.synthetic.main.fragment_shopping.*
import java.util.ArrayList
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.example.health.ui.adapter.PurchaseAdapter
import com.example.health.ui.mode.PurchaseData
import com.example.health.ui.mode.PurchaseInfo
import com.google.gson.Gson
import com.iflytek.speech.util.DatesUtils
import kotlinx.android.synthetic.main.fragment_purchase.*
import kotlinx.android.synthetic.main.loading.view.*
import kotlinx.android.synthetic.main.qrcode_dialog.view.*


/*
 *  @项目名：  health
 *  @包名：    com.example.health.ui.fragment
 *  @文件名:   MenuFragment
 *  @创建者:   ${小陈}
 *  @创建时间:  2020/4/28 17:44
 *  @描述：    TODO
 */
class PurchaseFragment : Fragment() {
    var purchase: PurchaseData? = null
    var qrDialog: Dialog? = null
    var mView: View? = null
    var tvTime: TextView? = null
    var countTime: Int = 10
    private var time1: Long = 0
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.fragment_purchase, container, false)
        initView()
        return mView
    }

    private fun initData() {

        getValidity()

        val purchaseList = ArrayList<PurchaseData>()

        val purchaseData1 = PurchaseData()
        purchaseData1.name = "1小时体验"
        purchaseData1.price = "20"
        purchaseData1.packageTime = "1小时"
        purchaseData1.time = 1 * 60 * 60 * 1000
        purchaseData1.iSselect = true
        purchaseList.add(purchaseData1)

        val purchaseData2 = PurchaseData()
        purchaseData2.name = "24小时体验"
        purchaseData2.packageTime = "24小时"
        purchaseData2.time = 24 * 60 * 60 * 1000
        purchaseData2.price = "60"
        purchaseList.add(purchaseData2)

        val purchaseData3 = PurchaseData()
        purchaseData3.name = "30天体验"
        purchaseData3.packageTime = "30天"
        purchaseData3.time = 30 * 24 * 60 * 60 * 1000
        purchaseData3.price = "600"
        purchaseList.add(purchaseData3)

        val recyclerView = purchaseRecyclerView
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayout.HORIZONTAL
        recyclerView.layoutManager = layoutManager as RecyclerView.LayoutManager?
        val adapter = PurchaseAdapter(purchaseList, context)
        recyclerView.adapter = adapter
        llPurchase.setOnClickListener {
            val purchaseData = adapter.purchaseData
            var isSelect: Boolean = false
            for (purchaseDatum in purchaseData) {
                isSelect = purchaseDatum.iSselect
                if (isSelect) {
                    purchase = purchaseDatum
                    break
                }
            }
            if (isSelect) {
                showQrCodeDialog()
            } else {
                Toast.makeText(context, "请选择体验体验时长", Toast.LENGTH_SHORT).show()
            }

        }

    }

    private fun getValidity() {
        val purchaseSP = context?.getSharedPreferences("purchaseJson", Context.MODE_PRIVATE)
        //第一个参数是键名，第二个是默认值
        val purchaseData = purchaseSP?.getString("purchaseDate", "")
        if (!purchaseData.isNullOrEmpty()) {
            val gson = Gson()
            val purchaseInfo = gson.fromJson(purchaseData, PurchaseInfo::class.java)
            time1 = purchaseInfo.validity
        }
    }

    private fun showQrCodeDialog() {
        countTime = 10
        countDownTimer.start()
        val dialogView = LayoutInflater.from(context).inflate(R.layout.qrcode_dialog, null)
        tvTime = dialogView.tvTime
        qrDialog = Dialog(context, R.style.dialog)
        dialogView.llClose.setOnClickListener {
            qrDialog?.dismiss()
            countDownTimer.cancel()
        }
        qrDialog?.setContentView(dialogView)
        qrDialog?.setCanceledOnTouchOutside(false)
        val p = qrDialog!!.window!!.attributes  //获取对话框当前的参数值// 获取屏幕宽、高用
        p.width = resources.getDimensionPixelOffset(R.dimen.dialog_widthM)
        p.height = resources.getDimensionPixelOffset(R.dimen.dialog_heightM)
        qrDialog!!.window!!.attributes = p
        qrDialog!!.show()

    }

    val countDownTimer = object : CountDownTimer(11 * 1000, 1000) {
        override fun onFinish() {
            val phone1 = etPhone.text.toString().trim()
            val name1 = etName1.text.toString().trim()
            val address = etAddress.text.toString().trim()
            val name2 = etName2.text.toString().trim()
            val phone2 = etPhone2.text.toString().trim()
            val purchaseInfo = PurchaseInfo()
            purchaseInfo.address = address
            purchaseInfo.emergencyName = name2
            purchaseInfo.name = name1
            purchaseInfo.emergencyPhone = phone2
            purchaseInfo.phone = phone1
            purchaseInfo.packageTime = purchase?.packageTime!!
            val currentTimeMillis = System.currentTimeMillis()
            val total = time1 - currentTimeMillis
            if (total <= 0L) {
                time1 = currentTimeMillis
                purchaseInfo.totalTime = purchase?.time!!
            } else {
                purchaseInfo.totalTime = ((total / purchase?.time!!) + 1) * purchase?.time!! + purchase?.time!!
            }
            purchaseInfo.validity = time1 + purchase?.time!!
            purchaseInfo.price = purchase?.price!!
            Log.d("MenuFragment2", "purchaseInfo.validity:${purchaseInfo.validity}")
            Log.d("MenuFragment2", "purchaseInfo.totalTime:${purchaseInfo.totalTime}")
            val nowTime = DatesUtils.getNowTime("yyyy-MM-dd HH:mm:ss")
            purchaseInfo.time = nowTime
            val purchaseSP = context?.getSharedPreferences("purchaseJson", Context.MODE_PRIVATE)
            val gson = Gson()
            val toJson = gson.toJson(purchaseInfo)
            val edit = purchaseSP?.edit()
            edit?.putString("purchaseDate", toJson)
            edit?.commit()

            buyListener?.buyClick(purchase)
            qrDialog?.dismiss()
        }

        override fun onTick(p0: Long) {
            tvTime?.text = "$countTime" + "S"
            countTime--
        }
    }

    private fun initView() {

    }

    override fun onResume() {
        super.onResume()
        Log.d("PurchaseFragment", "onResume")
        initData()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            getValidity()
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        Log.d("PurchaseFragment", "onAttach")

    }

    private var buyListener: BuyListener? = null

    fun setBuyListener(listener: BuyListener) {
        buyListener = listener
    }

    interface BuyListener {
        fun buyClick(data: PurchaseData?)
    }
}
