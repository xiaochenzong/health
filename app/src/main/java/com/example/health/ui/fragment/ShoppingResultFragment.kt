package com.example.health.ui.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.health.R
import android.util.Log
import kotlinx.android.synthetic.main.fragment_shopping_ersult.*


/*
 *  @项目名：  health
 *  @包名：    com.example.health.ui.fragment
 *  @文件名:   MenuFragment
 *  @创建者:   ${小陈}
 *  @创建时间:  2020/4/28 17:44
 *  @描述：    TODO
 */
class ShoppingResultFragment : Fragment() {

    var mView: View? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.fragment_shopping_ersult, container, false)
        initView()
        return mView
    }

    private fun initData() {
        llBackHome.setOnClickListener {
            mBackHomeListener?.backClick()
        }

    }

    private fun initView() {

    }

    override fun onResume() {
        super.onResume()
        Log.d("ShoppingFragment", "onResume")
        initData()
    }

    private var mBackHomeListener: BackHomeListener? = null

    fun setBackHomeListener(listener: BackHomeListener) {
        mBackHomeListener = listener
    }

    interface BackHomeListener {
        fun backClick()
    }
}
