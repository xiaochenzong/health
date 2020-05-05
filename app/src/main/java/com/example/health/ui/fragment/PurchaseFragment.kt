package com.example.health.ui.fragment


import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
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
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.fragment_purchase, container, false)
        initView()
        return mView
    }

    private fun initData() {
        val purchaseList = ArrayList<PurchaseData>()

        val purchaseData1 = PurchaseData()
        purchaseData1.name = "1小时体验"
        purchaseData1.price = "20"
        purchaseData1.iSselect = true
        purchaseList.add(purchaseData1)

        val purchaseData2 = PurchaseData()
        purchaseData2.name = "24小时体验"
        purchaseData2.price = "60"
        purchaseList.add(purchaseData2)

        val purchaseData3 = PurchaseData()
        purchaseData3.name = "30天体验"
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
        Log.d("ShoppingFragment", "onResume")
        initData()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        Log.d("ShoppingFragment", "onAttach")

    }

    private var buyListener: BuyListener? = null

    fun setBuyListener(listener: BuyListener) {
        buyListener = listener
    }

    interface BuyListener {
        fun buyClick(data: PurchaseData?)
    }
}
