package com.example.health.ui

import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.os.Handler
import android.text.TextUtils
import android.util.DisplayMetrics
import android.view.WindowManager
import com.example.health.R
import com.iflytek.cloud.SpeechConstant
import com.iflytek.cloud.SpeechUtility
import com.tencent.bugly.crashreport.CrashReport
import com.ucloudrtclib.sdkengine.UCloudRtcSdkEngine
import com.ucloudrtclib.sdkengine.UCloudRtcSdkEnv
import com.ucloudrtclib.sdkengine.define.UCloudRtcSdkLogLevel
import com.ucloudrtclib.sdkengine.define.UCloudRtcSdkMode
import com.urtcdemo.utils.CommonUtils
import com.urtcdemo.utils.UiHelper

/*
 *  @项目名：  health 
 *  @包名：    com.example.health.ui
 *  @文件名:   MyAppliction
 *  @创建者:   ${小陈}
 *  @创建时间:  2020/4/30 10:28
 *  @描述：    TODO
 */
class MyAppliction : Application() {
    private var sContext: Context? = null
    private var sUserId: String? = null
    private var rtcSdkEngine: UCloudRtcSdkEngine? = null
    override fun onCreate() {
        val param = StringBuffer()
        param.append("appid=" + getString(R.string.app_id))
        param.append(",")
        // 设置使用v5+
        param.append(SpeechConstant.ENGINE_MODE + "=" + SpeechConstant.MODE_MSC)
        SpeechUtility.createUtility(this@MyAppliction, param.toString())
        super.onCreate()
        if (TextUtils.equals(getCurrentProcessName(this), packageName)) {
            //            Log.d(TAG, "init: ");
            init()//判断成功后才执行初始化代码
        }
    }

    private fun init() {
        sContext = this
        UCloudRtcSdkEnv.initEnv(applicationContext)
        UCloudRtcSdkEnv.setWriteToLogCat(true)
        UCloudRtcSdkEnv.setLogReport(true)
        //        UCloudRtcSdkEnv.setEncodeMode(UCloudRtcSdkPushEncode.UCLOUD_RTC_PUSH_ENCODE_MODE_H264);
        UCloudRtcSdkEnv.setLogLevel(UCloudRtcSdkLogLevel.UCLOUD_RTC_SDK_LogLevelInfo)
        UCloudRtcSdkEnv.setSdkMode(UCloudRtcSdkMode.UCLOUD_RTC_SDK_MODE_TRIVAL)
        UCloudRtcSdkEnv.setReConnectTimes(60)
        UCloudRtcSdkEnv.setTokenSeckey(CommonUtils.SEC_KEY)
        //        UCloudRtcSdkEnv.setPushOrientation(UCloudRtcSdkPushOrentation.UCLOUD_RTC_PUSH_LANDSCAPE_MODE);
        //私有化部署
        //        UCloudRtcSdkEnv.setPrivateDeploy(true);
        //        UCloudRtcSdkEnv.setPrivateDeployRoomURL("ws://mediapoc1.pingan.com.cn:5005/ws");
        //无限重连
        //        UCloudRtcSdkEnv.setReConnectTimes(-1);
        //默认vp8编码，可以改成h264
        //        UCloudRtcSdkEnv.setEncodeMode(UcloudRtcSdkPushEncode.UCLOUD_RTC_PUSH_ENCODE_MODE_H264);
        val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val outMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(outMetrics)
        CommonUtils.mItemWidth = (outMetrics.widthPixels - UiHelper.dipToPx(this, 15F)) / 3
        CommonUtils.mItemHeight = CommonUtils.mItemWidth
        CrashReport.initCrashReport(applicationContext, "9a51ae062a", true)
        //        BlockCanary.install(this, new AppContext()).start();
    }

    private fun getCurrentProcessName(context: Context): String? {
        val pid = android.os.Process.myPid()
        val mActivityManager = context
                .getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (appProcess in mActivityManager
                .runningAppProcesses) {
            if (appProcess.pid == pid) {
                return appProcess.processName
            }
        }
        return null
    }

    companion object {
        private val mHandler: Handler = Handler()
        private var sUserId: String? = "test001"
        fun getUserId(): String? {
            return sUserId
        }

        fun setUserId(userId: String?) {
            sUserId = userId
        }

        fun getHandler(): Handler {
            return mHandler
        }
    }
}