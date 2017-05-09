package explame.com.imooctestone.application;

import android.app.Application;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;

import cn.bmob.v3.Bmob;
import explame.com.imooctestone.utils.StaticClass;

/*
 *      项目名：    ImoocTestOne
 *      包名：       explame.com.imooctestone.application
 *      时间           2017/5/2.
 *      创建者：    qzhuorui
 *      描述：        Application
 */
public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //初始化Bmob
        Bmob.initialize(this, StaticClass.BMOB_APP_ID);
        // 将“12345678”替换成您申请的APPID，申请地址：http://www.xfyun.cn
        // 请勿在“=”与appid之间添加任何空字符或者转义符
        SpeechUtility.createUtility(getApplicationContext(), SpeechConstant.APPID + "=" + StaticClass.VOICE_KEY);
    }
}
