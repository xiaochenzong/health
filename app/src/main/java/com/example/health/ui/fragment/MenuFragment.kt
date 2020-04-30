package com.example.health.ui.fragment


import android.app.Activity
import android.app.Dialog
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Environment
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import com.example.health.R
import com.example.health.ui.MyAppliction
import com.iflytek.cloud.*
import com.iflytek.cloud.ui.RecognizerDialog
import com.iflytek.cloud.ui.RecognizerDialogListener
import com.iflytek.cloud.util.ResourceUtil
import com.iflytek.speech.setting.IatSettings
import com.iflytek.speech.util.JsonParser
import kotlinx.android.synthetic.main.fragment_menu.*
import kotlinx.android.synthetic.main.voice_dialog.view.*
import java.util.*

/*
 *  @项目名：  health
 *  @包名：    com.example.health.ui.fragment
 *  @文件名:   MenuFragment
 *  @创建者:   ${小陈}
 *  @创建时间:  2020/4/28 17:44
 *  @描述：    TODO
 */
class MenuFragment : Fragment() {
    private val TAG = "IatDemo"
    private var mToast: Toast? = null
    // 语音听写对象
    private var mIat: SpeechRecognizer? = null
    // 语音听写UI
    private var mIatDialog: RecognizerDialog? = null
    // 用HashMap存储听写结果
    private val mIatResults = LinkedHashMap<String, String>()

    private var mSharedPreferences: SharedPreferences? = null
    private var mEngineType = "cloud"
    internal var ret = 0// 函数调用返回值
    private lateinit var mDialog: Dialog

    private var llVoice: LinearLayout? = null
    private var ivVoice: ImageView? = null
    private var ivIdentify: ImageView? = null

    var mView: View? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.fragment_menu, container, false)
        mEngineType = SpeechConstant.TYPE_LOCAL
        mIat = SpeechRecognizer.createRecognizer(context, mInitListener)
        // 初始化听写Dialog，如果只使用有UI听写功能，无需创建SpeechRecognizer
        // 使用UI听写功能，请根据sdk文件目录下的notice.txt,放置布局文件和图片资源
        mIatDialog = RecognizerDialog(context, mInitListener)
        mSharedPreferences = context?.getSharedPreferences(IatSettings.PREFER_NAME, Activity.MODE_PRIVATE)
        mToast = Toast.makeText(context, "", Toast.LENGTH_SHORT)
        initView()
        initListener()
        return mView
    }

    private fun initListener() {
        llLeftBack.setOnClickListener {}
        llRightBack.setOnClickListener {}
        llWash.setOnClickListener { }
        llBackMassage.setOnClickListener { }
        llBackup.setOnClickListener { }
        llBackDown.setOnClickListener { }
        llReset.setOnClickListener { }
        llLegMassage.setOnClickListener { }
        llLegup.setOnClickListener { }
        llLegDown.setOnClickListener { }
        llStop.setOnClickListener { }
        //正视图
        tvPositive.setOnClickListener { }
        //侧视图
        tvSide.setOnClickListener { }
        //智能语音
        llVoiceHealth.setOnClickListener {
            mIatResults.clear()
            // 设置参数
            setParam()
            // 不显示听写对话框
            ret = mIat?.startListening(mRecognizerListener)!!
            showDialog()
            if (ret != ErrorCode.SUCCESS) {
                showTip("听写失败,错误码：$ret,请点击网址https://www.xfyun.cn/document/error-code查询解决方案")
            } else {
                showTip(getString(R.string.text_begin))
            }
        }
    }

    private fun initView() {

    }

    /**
     * 参数设置
     *
     * @return
     */
    fun setParam() {
        // 清空参数
        mIat?.setParameter(SpeechConstant.PARAMS, null)
        val lag = mSharedPreferences?.getString("iat_language_preference", "mandarin")
        // 设置引擎
        mIat?.setParameter(SpeechConstant.ENGINE_TYPE, mEngineType)
        // 设置返回结果格式
        mIat?.setParameter(SpeechConstant.RESULT_TYPE, "json")

        //mIat.setParameter(MscKeys.REQUEST_AUDIO_URL,"true");

        //	this.mTranslateEnable = mSharedPreferences.getBoolean( this.getString(R.string.pref_key_translate), false );
        if (mEngineType == SpeechConstant.TYPE_LOCAL) {
            // 设置本地识别资源
            mIat?.setParameter(ResourceUtil.ASR_RES_PATH, getResourcePath())
        }
        // 在线听写支持多种小语种，若想了解请下载在线听写能力，参看其speechDemo
        if (lag == "en_us") {
            // 设置语言
            mIat?.setParameter(SpeechConstant.LANGUAGE, "en_us")
            mIat?.setParameter(SpeechConstant.ACCENT, null)

            // 设置语言
            mIat?.setParameter(SpeechConstant.LANGUAGE, "zh_cn")
            // 设置语言区域
            mIat?.setParameter(SpeechConstant.ACCENT, lag)

        }


        // 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理
        mIat?.setParameter(SpeechConstant.VAD_BOS, mSharedPreferences?.getString("iat_vadbos_preference", "4000"))

        // 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
        mIat?.setParameter(SpeechConstant.VAD_EOS, mSharedPreferences?.getString("iat_vadeos_preference", "1000"))

        // 设置标点符号,设置为"0"返回结果无标点,设置为"1"返回结果有标点
        mIat?.setParameter(SpeechConstant.ASR_PTT, mSharedPreferences?.getString("iat_punc_preference", "1"))

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        mIat?.setParameter(SpeechConstant.AUDIO_FORMAT, "wav")
        mIat?.setParameter(SpeechConstant.ASR_AUDIO_PATH, Environment.getExternalStorageDirectory().toString() + "/msc/iat.wav")
    }

    private fun showDialog() {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.voice_dialog, null)
        llVoice = dialogView.llVoice
        ivVoice = dialogView.ivVoice
        ivIdentify = dialogView.ivIdentify
        llVoice?.setOnClickListener {
            llVoice?.visibility = View.GONE
            ivIdentify?.visibility = View.VISIBLE
            var rotate = AnimationUtils.loadAnimation(context, R.anim.rotate_anim)
            if (rotate != null) {
                ivIdentify?.startAnimation(rotate);
            } else {
                ivIdentify?.setAnimation(rotate);
                ivIdentify?.startAnimation(rotate);
            }
            mIat?.stopListening()
        }
        mDialog = Dialog(context, R.style.dialog)
        mDialog.setContentView(dialogView)
        val p = mDialog!!.window!!.attributes  //获取对话框当前的参数值// 获取屏幕宽、高用
        p.width = resources.getDimensionPixelOffset(R.dimen.dialog_widthM)
        p.height = resources.getDimensionPixelOffset(R.dimen.dialog_heightM)
        mDialog!!.window!!.attributes = p
        mDialog!!.show()
    }

    /**
     * 听写监听器。
     */
    private val mRecognizerListener = object : RecognizerListener {

        override fun onBeginOfSpeech() {
            // 此回调表示：sdk内部录音机已经准备好了，用户可以开始语音输入
            showTip("开始说话")
            Log.d(TAG, "开始说话")
        }

        override fun onError(error: SpeechError?) {
            Log.d(TAG, "onError")
            ivIdentify?.clearAnimation()
            mDialog.dismiss()
            // Tips：
            // 错误码：10118(您没有说话)，可能是录音机权限被禁，需要提示用户打开应用的录音权限。
            showTip(error?.getPlainDescription(true)!!)
        }

        override fun onEndOfSpeech() {
            // 此回调表示：检测到了语音的尾端点，已经进入识别过程，不再接受语音输入
            showTip("结束说话")
            llVoice?.visibility = View.GONE
            ivIdentify?.visibility = View.VISIBLE
            var rotate = AnimationUtils.loadAnimation(context, R.anim.rotate_anim)
            if (rotate != null) {
                ivIdentify?.startAnimation(rotate)
            } else {
                ivIdentify?.setAnimation(rotate)
                ivIdentify?.startAnimation(rotate)
            }
            Log.d(TAG, "结束说话")
        }

        override fun onResult(results: RecognizerResult?, isLast: Boolean) {
            mDialog.dismiss()
            ivIdentify?.clearAnimation()
            Log.d(TAG, "说话结果:${JsonParser.parseIatResult(results?.resultString)}")
            val text = JsonParser.parseIatResult(results?.resultString)
            showTip(text)
            if (isLast) {
                //TODO 最后的结果
            }
        }

        override fun onVolumeChanged(volume: Int, data: ByteArray) {
            if (mDialog != null && ivVoice != null) {
                ivVoice?.setImageResource(R.drawable.voice_full)
            }
            showTip("当前正在说话，音量大小：$volume")
            Log.d(TAG, "正在说话：" + data.size)
        }

        override fun onEvent(eventType: Int, arg1: Int, arg2: Int, obj: Bundle?) {
            // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
            // 若使用本地能力，会话id为null
            Log.d(TAG, "onEvent")
            if (SpeechEvent.EVENT_SESSION_ID == eventType) {
                val sid = obj?.getString(SpeechEvent.KEY_EVENT_AUDIO_URL)
                if (sid != null) {
                    Log.d(TAG, "session id =" + sid!!)
                }
            }
        }
    }

    /**
     * 听写UI监听器
     */
    private val mRecognizerDialogListener = object : RecognizerDialogListener {
        override fun onResult(results: RecognizerResult, isLast: Boolean) {
            Log.d(TAG, "recognizer result：" + results.resultString)
            val text = JsonParser.parseIatResult(results.resultString)
            showTip(text)
        }

        /**
         * 识别回调错误.
         */
        override fun onError(error: SpeechError) {
            Log.d(TAG, error.getPlainDescription(true))
            showTip(error.getPlainDescription(true))
            showTip(error.getPlainDescription(true))
        }
    }

    /**
     * 初始化监听器。
     */
    private val mInitListener = InitListener { code ->
        Log.d(TAG, "SpeechRecognizer init() code = $code")
        if (code != ErrorCode.SUCCESS) {
            showTip("初始化失败，错误码：$code,请点击网址https://www.xfyun.cn/document/error-code查询解决方案")
        }
    }

    private fun showTip(str: String) {
        MyAppliction.getHandler().post {
            mToast?.setText(str)
            mToast?.show()
        }
    }

    private fun getResourcePath(): String {
        val tempBuffer = StringBuffer()
        //识别通用资源
        tempBuffer.append(ResourceUtil.generateResourcePath(context, ResourceUtil.RESOURCE_TYPE.assets, "iat/common.jet"))
        tempBuffer.append(";")
        tempBuffer.append(ResourceUtil.generateResourcePath(context, ResourceUtil.RESOURCE_TYPE.assets, "iat/sms_16k.jet"))
        //识别8k资源-使用8k的时候请解开注释
        return tempBuffer.toString()
    }
}
