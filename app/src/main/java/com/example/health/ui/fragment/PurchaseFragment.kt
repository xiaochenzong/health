package com.example.health.ui.fragment


import android.content.Context
import android.os.Bundle
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
import android.widget.LinearLayout
import com.example.health.ui.adapter.PurchaseAdapter
import com.example.health.ui.mode.PurchaseData
import kotlinx.android.synthetic.main.fragment_purchase.*


/*
 *  @项目名：  health
 *  @包名：    com.example.health.ui.fragment
 *  @文件名:   MenuFragment
 *  @创建者:   ${小陈}
 *  @创建时间:  2020/4/28 17:44
 *  @描述：    TODO
 */
class PurchaseFragment : Fragment() {

    var mView: View? = null
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
        adapter.setPurchaseListener {
            buyListener?.buyClick(it)
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
        fun buyClick(data: PurchaseData)
    }
}
