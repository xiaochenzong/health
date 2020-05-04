package com.iflytek.speech.util

import android.os.Handler
import android.text.TextUtils
import android.util.Log
import java.util.ArrayList

/*
 *  @项目名：  health 
 *  @包名：    com.iflytek.speech.util
 *  @文件名:   SpeechResultUtils
 *  @创建者:   ${小陈}
 *  @创建时间:  2020/4/30 11:54
 *  @描述：    TODO
 */
class SpeechResultUtils {
    companion object {

        val zuoCeFanArray: Array<String> = arrayOf("左侧翻", "左侧", "卓车费", "做车费", "做的饭", "坐厕笵", "卓侧翻", "坐车方便", "左侧三", "坐车山", "我侧翻", "左侧分", "坐车翻", "欧洲三", "坐上三")

        val youCeFanArray: Array<String> = arrayOf("右侧发", "右侧", "有车费", "有侧翻", "右侧泛", "尤策凡", "有车房", "右侧方", "要侧翻", "要策反", "右侧三", "要错峰", "右侧分", "我吃醋酸", "要周三", "有车三", "要做饭", "要做山", "要车三", "要做三", "要坐山","要做翻")

        val shouDongChongXiArray: Array<String> = arrayOf("手动", "从西", "冲洗", "手动冲", "手动充", "手动从西", "手动冲洗", "手动从小", "骚动装水", "骚动冲水", "操控中心", "操纵雄性", "骚动中心", "我更伤心", "手冻疮听", "我更重庆", "高中中心", "肇东中心", "高中生性", "邵东中心", "劳动中心", "高端中心", "邵东重新", "骚动重庆", "骚动中水", "冲水", "手动充水", "手动冲水", "早当中去", "我刚从", "骚动春水", "骚动重新", "手动装水", "骚动")

        val beiBuAnMoArray: Array<String> = arrayOf("背部", "背部按摩", "贝步安", "背部按", "背部我按摩", "为不按呢", "微博案", "微波啊", "背部案", "微博啊", "给我按摩", "背部我按摩", "微博按摩", "一部按摩", "被波按摩", "微博呢", "内部按摩", "被迫按摩", "被不嗯", "背负按摩")

        val qiBeiArray: Array<String> = arrayOf("齐备", "起背", "气呗", "起被", "起呗", "其呗", "洗被", "七倍", "气呢", "黑斑", "其美", "期待", "七呗")

        val pingBeiMoArray: Array<String> = arrayOf("平背", "平辈", "平北", "屏蔽", "评比", "拼呗", "听呗", "经费", "运费", "应该", "可能被", "平台", "平淡")

        val fuWeiMoArray: Array<String> = arrayOf("复位", "抚慰", "付伟", "腹围", "富伟", "富维", "傅伟", "富威", "符伟", "复胃", "所谓", "座位", "够味", "福会")

        val tuiBuAnMoArray: Array<String> = arrayOf("腿部", "推不按", "腿部按", "腿部按摩", "微博安哦", "腿我按摩", "腿说啊", "腿给我按摩")

        val taiTuiArray: Array<String> = arrayOf("抬腿", "太土", "态图", "肽图", "还退", "排队", "干脆")

        val tingZhiArray: Array<String> = arrayOf("停止", "紧急停止", "停滞", "亭子", "挺直", "听着", "停职", "名字", "听证", "瓶子")

        val pingTuiArray: Array<String> = arrayOf("平退", "而退", "黑腿", "拼音退", "陪退", "陪你退", "林退", "拼音翠", "因退", "黑鸭退", "评测", "黑云吞", "听退", "贫嘴", "拼退", "陪你退", "听证", "应在", "您在", "听听", "应退","惭愧","林腿","明腿","停课")


        val zuoArray: Array<String> = arrayOf("左", "做", "坐", "作", "座", "昨", "佐", "组", "租", "足", "族", "祖", "阻", "卒", "诅", "桌", "捉", "卓", "着", "拙", "啄", "灼", "浊", "镯", "主", "住", "猪", "祝", "朱", "珠", "煮", "竹", "逐")

        val ceArray: Array<String> = arrayOf("测", "册", "侧", "策", "厕", "恻", "栅", "恻", "側", "恻", "车", "撤", "扯", "彻", "澈", "澈", "坼", "则", "泽", "择", "责", "咋")

        val fanArray: Array<String> = arrayOf("饭", "烦", "反", "翻", "犯", "范", "凡", "返", "帆", "泛", "繁", "番", "发", "法", "罚", "伐", "乏", "放", "房", "方", "防", "芳", "仿", "坊", "访", "纺")

        val youArray: Array<String> = arrayOf("有", "又", "游", "由", "友", "油", "右", "优", "邮", "呦", "幼", "忧", "幽", "悠", "佑", "与", "于", "雨", "鱼", "玉", "语", "余", "绕", "饶", "扰", "娆", "要", "药", "咬", "腰", "妖", "摇", "妖", "肉", "揉", "柔", "蹂")

        val shouArray: Array<String> = arrayOf("熟", "收", "手", "受", "瘦", "首", "守", "兽", "搜", "嗖", "售", "寿", "授", "艘", "馊", "嗽", "扫", "骚", "嫂", "搔", "臊", "少", "烧", "稍", "邵", "捎", "韶")

        val dongArray: Array<String> = arrayOf("懂", "动", "东", "洞", "冻", "栋", "冬", "董", "同", "通", "痛", "童", "筒", "铜")

        val chongArray: Array<String> = arrayOf("从", "葱", "聪", "丛", "匆", "琮", "囱", "淙", "重", "冲", "充", "虫", "宠", "崇", "涌", "宗", "总", "踪", "粽", "中", "钟", "种", "忠")

        val xiArray: Array<String> = arrayOf("洗", "西", "系", "戏", "囍", "细", "喜", "吸", "习", "息", "希", "溪", "惜", "夕", "稀", "机", "集", "即", "季", "几", "级", "及", "急", "记", "c", "死", "四", "思", "斯", "似", "丝", "司", "私", "是", "时", "市", "试", "失", "事", "十")

        val beiArray: Array<String> = arrayOf("北", "被", "呗", "背", "倍", "杯", "备", "贝", "悲", "辈", "狈", "狈")

        val buArray: Array<String> = arrayOf("不", "补", "部", "步", "布", "簿", "捕", "卜", "卟")

        val anArray: Array<String> = arrayOf("按", "安", "俺", "暗", "案", "岸", "庵", "涵", "含", "喊", "汗", "汉", "韩")

        val moArray: Array<String> = arrayOf("摸", "莫", "磨", "魔", "末", "膜", "模", "抹", "墨", "木", "母", "目", "亩", "幕", "牧", "墓", "吗", "哦", "噢", "喔")

        val qiArray: Array<String> = arrayOf("器", "起", "气", "七", "其", "期", "齐", "骑", "奇", "琪", "启", "旗", "棋", "漆", "琦", "弃", "妻")

        val pingArray: Array<String> = arrayOf("平", "瓶", "凭", "屏", "评", "萍", "坪", "苹", "品", "拼", "频", "贫", "聘", "晕", "而", "人", "听", "黑")

        val fuArray: Array<String> = arrayOf("夫", "付", "福", "府", "副", "服", "服", "富", "负", "复", "符", "附", "父", "浮", "幅", "傅", "妇", "赴", "伏", "辅", "弗", "赋", "敷", "腹", "肤", "扶", "甫", "芙", "覆", "佛", "坲", "仸")

        val weiArray: Array<String> = arrayOf("为", "位", "喂", "未", "伟", "味", "胃", "维", "卫", "威", "微", "魏", "围", "尾", "委", "唯", "韦", "伪", "炜", "纬")

        val tuiArray: Array<String> = arrayOf("退", "腿", "推", "忒", "褪", "颓", "蜕", "頽")

        val taiArray: Array<String> = arrayOf("太", "台", "泰", "抬", "态", "胎", "钛", "苔", "肽", "邰", "汰", "酞", "带", "呆", "待", "代", "戴", "袋", "贷", "逮", "歹", "安")

        //  val voiceArry2: Array<Array<String>> = arrayOf(zuoCeFanArray, youCeFanArray, shouDongChongXiArray, beiBuAnMoArray, qiBeiArray, pingBeiMoArray, fuWeiMoArray, tuiBuAnMoArray, taiTuiArray, tingZhiArray, pingTuiArray)

        // val voiceArry1: Array<Array<String>> = arrayOf(zuoArray, ceArray, fanArray, youArray, qiBeiArray, shouArray, dongArray, chongArray, xiArray, beiArray, buArray, anArray, moArray, qiArray, pingArray, fuArray, weiArray, tuiArray, taiArray)

        fun getSpeech(speech: String): String {
            Log.d("speech", "" + speech.length)
            val speech2 = getSpeech2(speech)
            if (!TextUtils.isEmpty(speech2)) {
                return speech2
            }
            var speech1 = ""
            if (TextUtils.isEmpty(speech2) && speech.length > 1 && speech.length < 7) {
                val toCharArray = speech.toCharArray()
                toCharArray.forEach {
                    speech1 += getSpeech1(it.toString())
                }
            }
            if (!TextUtils.isEmpty(speech1.trim())) {
                speech1 = getSpeech2(speech1)
            }
            if (!TextUtils.isEmpty(speech1)) {
                return speech1
            }
            return speech
        }

        fun getSpeech1(speech: String): String {
            zuoArray.forEach {
                if (speech.contains(it)) {
                    return "左"
                }
            }
            ceArray.forEach {
                if (speech.contains(it)) {
                    return "侧"
                }
            }
            fanArray.forEach {
                if (speech.contains(it)) {
                    return "翻"
                }
            }
            youArray.forEach {
                if (speech.contains(it)) {
                    return "右"
                }
            }
            shouArray.forEach {
                if (speech.contains(it)) {
                    return "手"
                }
            }
            dongArray.forEach {
                if (speech.contains(it)) {
                    return "动"
                }
            }
            chongArray.forEach {
                if (speech.contains(it)) {
                    return "冲"
                }
            }
            xiArray.forEach {
                if (speech.contains(it)) {
                    return "洗"
                }
            }
            beiArray.forEach {
                if (speech.contains(it)) {
                    return "背"
                }
            }
            buArray.forEach {
                if (speech.contains(it)) {
                    return "部"
                }
            }
            anArray.forEach {
                if (speech.contains(it)) {
                    return "按"
                }
            }
            moArray.forEach {
                if (speech.contains(it)) {
                    return "摩"
                }
            }
            qiArray.forEach {
                if (speech.contains(it)) {
                    return "起"
                }
            }
            fuArray.forEach {
                if (speech.contains(it)) {
                    return "复"
                }
            }
            weiArray.forEach {
                if (speech.contains(it)) {
                    return "位"
                }
            }
            tuiArray.forEach {
                if (speech.contains(it)) {
                    return "腿"
                }
            }
            taiArray.forEach {
                if (speech.contains(it)) {
                    return "抬"
                }
            }
            pingArray.forEach {
                if (speech.contains(it)) {
                    return "平"
                }
            }
            return ""
        }

        fun getSpeech2(speech: String): String {
            zuoCeFanArray.forEach {
                if (speech.contains(it)) {
                    return "左侧翻"
                }
            }
            youCeFanArray.forEach {
                if (speech.contains(it)) {
                    return "右侧翻"
                }
            }
            shouDongChongXiArray.forEach {
                if (speech.contains(it)) {
                    return "手动冲洗"
                }
            }
            beiBuAnMoArray.forEach {
                if (speech.contains(it)) {
                    return "背部按摩"
                }
            }
            qiBeiArray.forEach {
                if (speech.contains(it)) {
                    return "起背"
                }
            }
            pingBeiMoArray.forEach {
                if (speech.contains(it)) {
                    return "平背"
                }
            }
            fuWeiMoArray.forEach {
                if (speech.contains(it)) {
                    return "复位"
                }
            }
            tuiBuAnMoArray.forEach {
                if (speech.contains(it)) {
                    return "腿部按摩"
                }
            }
            taiTuiArray.forEach {
                if (speech.contains(it)) {
                    return "抬腿"
                }
            }
            pingTuiArray.forEach {
                if (speech.contains(it)) {
                    return "平腿"
                }
            }
            return ""
        }
    }
}