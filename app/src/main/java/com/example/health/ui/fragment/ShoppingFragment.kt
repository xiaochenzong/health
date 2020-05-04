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


/*
 *  @项目名：  health
 *  @包名：    com.example.health.ui.fragment
 *  @文件名:   MenuFragment
 *  @创建者:   ${小陈}
 *  @创建时间:  2020/4/28 17:44
 *  @描述：    TODO
 */
class ShoppingFragment : Fragment() {

    var mView: View? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.fragment_shopping, container, false)
        initView()
        return mView
    }

    private fun initData() {
        val shopList = ArrayList<ShopData>()

        val shopData1 = ShopData()
        shopData1.name = "高级护理床"
        shopData1.price = "1998"
        val list1 = ArrayList<Int>()
        list1.add(R.drawable.image3)
        list1.add(R.drawable.image3)
        list1.add(R.drawable.image3)
        shopData1.ivList = list1
        shopList.add(shopData1)

        val shopData2 = ShopData()
        shopData2.name = "护理床配件小桌板"
        shopData2.price = "98"
        val list2 = ArrayList<Int>()
        list2.add(R.drawable.item2)
        list2.add(R.drawable.item2)
        list2.add(R.drawable.item2)
        shopData2.ivList = list2
        shopList.add(shopData2)

        shopList.add(shopData2)


        val shopData3 = ShopData()
        shopData3.name = "护理床配件护理床垫"
        shopData3.price = "298"
        val list3 = ArrayList<Int>()
        list3.add(R.drawable.item1)
        list3.add(R.drawable.item1)
        list3.add(R.drawable.item1)
        shopData3.ivList = list3
        shopList.add(shopData3)

        shopList.add(shopData2)
        shopList.add(shopData2)
        val recyclerView = reclclerView
        val layoutManager = GridLayoutManager(context, 3)
        recyclerView.layoutManager = layoutManager as RecyclerView.LayoutManager?
        val adapter = ShoppingAdapter(shopList, context)
        recyclerView.adapter = adapter
        adapter.setShoppingListener {
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
        fun buyClick(data: ShopData)
    }
}
