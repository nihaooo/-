package explame.com.imooctestone.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.telephony.SmsMessage;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import explame.com.imooctestone.R;
import explame.com.imooctestone.utils.L;
import explame.com.imooctestone.utils.StaticClass;

/*
 *      项目名：    ImoocTestOne
 *      包名：       explame.com.imooctestone.service
 *      时间           2017/5/10.
 *      创建者：    qzhuorui
 *      描述：        短信监听服务
 */
public class SmsService extends Service implements View.OnClickListener {

    private SmsReceiver smsReceiver;
    //发件人号码
    private String smsPhone;
    //短息内容
    private String smsContent;
    //窗口管理器
    private WindowManager wm;
    //布局参数
    private WindowManager.LayoutParams layoutparams;
    //View
    private View mView;

    private TextView tv_phone, tv_content;
    private Button btn_send_sms;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        init();

    }

    private void init() {
        L.i("init service");

        //动态注册
        smsReceiver = new SmsReceiver();
        IntentFilter intent = new IntentFilter();
        //添加Action
        intent.addAction(StaticClass.SMS_ACTION);
        //设置权限
        intent.setPriority(Integer.MAX_VALUE);
        //注册
        registerReceiver(smsReceiver, intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        L.i("stop service");
        //注销
        unregisterReceiver(smsReceiver);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send_sms:
                sendSms();
                //消失窗口
                //进行判断，防止空指针
                if (mView.getParent() != null) {
                    wm.removeView(mView);
                }
                break;
        }
    }

    //回复短信
    private void sendSms() {
        Uri uri = Uri.parse("sms:" + smsPhone);
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        //设置启动模式
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("sms_body", "");
        startActivity(intent);
    }

    //短信广播
    public class SmsReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (StaticClass.SMS_ACTION.equals(action)) {
                L.i("来短信了");

                //获取短信内容，返回一个object数组
                Object[] objs = (Object[]) intent.getExtras().get("pdus");
                //遍历短信数组得到相关数据
                for (Object obj : objs) {
                    //把数组元素，转化成短信对象
                    SmsMessage sms = SmsMessage.createFromPdu((byte[]) obj);
                    //发件人
                    smsPhone = sms.getOriginatingAddress();
                    //内容
                    smsContent = sms.getMessageBody();
                    L.i("短信的内容" + smsPhone + ":" + smsContent);
                    showWindow();
                }
            }
        }
    }

    //窗口提示
    private void showWindow() {
        //获取系统服务
        wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        //获取布局参数
        layoutparams = new WindowManager.LayoutParams();
        //定义宽高
        layoutparams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutparams.height = WindowManager.LayoutParams.MATCH_PARENT;
        //定义标记
        layoutparams.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;
        //定义格式
        layoutparams.format = PixelFormat.TRANSLUCENT;
        //定义类型
        layoutparams.type = WindowManager.LayoutParams.TYPE_PHONE;
        //加载布局
        mView = View.inflate(getApplicationContext(), R.layout.sms_item, null);

        tv_phone = (TextView) mView.findViewById(R.id.tv_phone);
        tv_content = (TextView) mView.findViewById(R.id.tv_content);
        btn_send_sms = (Button) mView.findViewById(R.id.btn_send_sms);
        btn_send_sms.setOnClickListener(this);

        //设置数据
        tv_phone.setText("发件人：" + smsPhone);
        tv_content.setText(smsContent);


        //添加View到窗口
        wm.addView(mView, layoutparams);
    }
}
