package explame.com.imooctestone.application;

import android.app.Application;

import cn.bmob.v3.Bmob;
import explame.com.imooctestone.utils.StaticClass;

/*
 *      项目名：    ImoocTestOne
 *      包名：       explame.com.imooctestone.application
 *      时间           2017/5/2.
 *      创建者：    qzhuorui
 *      描述：        Application
 */
public class BaseApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();

        //初始化Bmob
        Bmob.initialize(this, StaticClass.BMOB_APP_ID);
    }
}
