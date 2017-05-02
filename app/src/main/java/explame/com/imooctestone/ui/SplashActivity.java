package explame.com.imooctestone.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import explame.com.imooctestone.R;
import explame.com.imooctestone.utils.ShareUtils;
import explame.com.imooctestone.utils.StaticClass;

/*
 *      项目名：    ImoocTestOne
 *      包名：       explame.com.imooctestone.ui
 *      时间           2017/5/2.
 *      创建者：    qzhuorui
 *      描述：        闪屏页
 */
public class SplashActivity extends AppCompatActivity{

    /**
     * 1.延时2000ms
     * 2.判断是否第一次运行
     * 3.定义字体
     * 3.activity全屏
     * @param savedInstanceState
     */

    private TextView tv_splash;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case StaticClass.HANDLER_SPLASH :
                    //判断是否第一次运行
                    if (isFirst()){
                        startActivity(new Intent(SplashActivity.this,GuideActivity.class));
                    }else {
                        startActivity(new Intent(SplashActivity.this, GuideActivity.class));
                    }
                    finish();
                    break;
            }
        }
    };

    //判断第一次运行
    private boolean isFirst() {
        boolean isFirst = ShareUtils.getBoolean(this,StaticClass.SHARE_IS_FIRST,true);
        if (isFirst){
            ShareUtils.putBoolean(this,StaticClass.SHARE_IS_FIRST,false);
            //is first
            return true;
        }else {
            return false;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initView();
    }

    private void initView() {
        //延时2000ms
        handler.sendEmptyMessageDelayed(StaticClass.HANDLER_SPLASH,2000);

        tv_splash = (TextView) findViewById(R.id.tv_splash);
        //设置字体
//        UtilTools.setFont(this,tv_splash);
    }

    //禁止返回键

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }
}
