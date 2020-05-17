package com.example.health.ui.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.health.R
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_shopping_information.*


/*
 *  @项目名：  health
 *  @包名：    com.example.health.ui.fragment
 *  @文件名:   MenuFragment
 *  @创建者:   ${小陈}
 *  @创建时间:  2020/4/28 17:44
 *  @描述：    TODO
 */
class ShoppingInfoFragment : Fragment() {

    var mView: View? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.fragment_shopping_information, container, false)
        initView()
        return mView
    }

    private fun initData() {
        llSubmit.setOnClickListener {
            val name1 = etName1.text.toString().trim()
            val phone1 = etPhone.text.toString().trim()
            val name2 = etName2.text.toString().trim()
            val phone2 = etPhone2.text.toString().trim()
            if ((!TextUtils.isEmpty(name1) && !TextUtils.isEmpty(phone1)) || (!TextUtils.isEmpty(name2) && !TextUtils.isEmpty(phone2))) {
                mSubmitListenerListener?.submitClick()
            } else {
                Toast.makeText(context, "至少填写一个联系人信息", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initView() {

    }

    override fun onResume() {
        super.onResume()
        Log.d("ShoppingFragment", "onResume")
        initData()
    }

    private var mSubmitListenerListener: SubmitListener? = null

    fun setSubmitListener(listener: SubmitListener) {
        mSubmitListenerListener = listener
    }

    interface SubmitListener {
        fun submitClick()
    }
}
