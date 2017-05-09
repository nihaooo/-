package explame.com.imooctestone.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Switch;

import explame.com.imooctestone.R;
import explame.com.imooctestone.utils.ShareUtils;

/*
 *      项目名：    ImoocTestOne
 *      包名：       explame.com.imooctestone.ui
 *      时间           2017/5/2.
 *      创建者：    qzhuorui
 *      描述：        设置
 */
public class SettingActivity extends BaseActivity implements View.OnClickListener {

    //语音播报
    private Switch sw_speak;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initView();
    }

    private void initView() {
        sw_speak = (Switch) findViewById(R.id.sw_speak);
        sw_speak.setOnClickListener(this);
        boolean isSpeak = ShareUtils.getBoolean(this,"isSpeak",false);
        sw_speak.setChecked(isSpeak);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sw_speak:
                //切换相反状态
                sw_speak.setSelected(!sw_speak.isSelected());
                ShareUtils.putBoolean(this, "isSpeak", sw_speak.isChecked());
                break;
        }
    }
}
