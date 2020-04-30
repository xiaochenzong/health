package com.example.health.ui

import android.app.Application
import android.os.Handler
import com.example.health.R
import com.iflytek.cloud.SpeechConstant
import com.iflytek.cloud.SpeechUtility

/*
 *  @项目名：  health 
 *  @包名：    com.example.health.ui
 *  @文件名:   MyAppliction
 *  @创建者:   ${小陈}
 *  @创建时间:  2020/4/30 10:28
 *  @描述：    TODO
 */
class MyAppliction : Application() {

    override fun onCreate() {
        val param = StringBuffer()
        param.append("appid=" + getString(R.string.app_id))
        param.append(",")
        // 设置使用v5+
        param.append(SpeechConstant.ENGINE_MODE + "=" + SpeechConstant.MODE_MSC)
        SpeechUtility.createUtility(this@MyAppliction, param.toString())
        super.onCreate()
    }

    companion object {
        private val mHandler: Handler = Handler()

        fun getHandler(): Handler {
            return mHandler
        }
    }
}