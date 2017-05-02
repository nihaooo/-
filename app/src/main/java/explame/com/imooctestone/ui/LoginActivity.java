package explame.com.imooctestone.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import explame.com.imooctestone.R;

/*
 *      项目名：    ImoocTestOne
 *      包名：       explame.com.imooctestone.ui
 *      时间           2017/5/2.
 *      创建者：    qzhuorui
 *      描述：        登录
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    //注册按钮
    private Button btn_register;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        initView();
    }

    private void initView() {
        btn_register = (Button) findViewById(R.id.btn_register);
        btn_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_register :
                startActivity(new Intent(this,RegisteredActivity.class));
                break;
        }
    }
}
