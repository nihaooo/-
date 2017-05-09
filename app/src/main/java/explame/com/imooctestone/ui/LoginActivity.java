package explame.com.imooctestone.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cn.bmob.v3.listener.SaveListener;
import explame.com.imooctestone.MainActivity;
import explame.com.imooctestone.R;
import explame.com.imooctestone.entity.MyUser;
import explame.com.imooctestone.utils.ShareUtils;
import explame.com.imooctestone.view.CustomDialog;

/*
 *      项目名：    ImoocTestOne
 *      包名：       explame.com.imooctestone.ui
 *      时间           2017/5/2.
 *      创建者：    qzhuorui
 *      描述：        登录
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    //注册按钮
    private Button btn_register, btn_login;
    private EditText et_name, et_password;
    private CheckBox keep_password;
    private TextView tv_forget;

    //登录进度
    private CustomDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_register = (Button) findViewById(R.id.btn_register);
        btn_register.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        et_name = (EditText) findViewById(R.id.et_name);
        et_password = (EditText) findViewById(R.id.et_password);
        keep_password = (CheckBox) findViewById(R.id.keep_password);

        //设置选中的状态
        boolean isCheck = ShareUtils.getBoolean(this, "keeppass", false);
        keep_password.setChecked(isCheck);
        if (isCheck) {
            //设置密码
            et_name.setText(ShareUtils.getString(this, "name", ""));
            et_password.setText(ShareUtils.getString(this, "password", ""));
        }

        tv_forget = (TextView) findViewById(R.id.tv_forget);
        tv_forget.setOnClickListener(this);

        dialog = new CustomDialog(this, 500, 500,
                R.layout.dialog_loding, R.style.Theme_dialog, Gravity.CENTER, R.style.pop_anim_style);
        //屏幕外点击无效
        dialog.setCancelable(false);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
                startActivity(new Intent(this, RegisteredActivity.class));
                break;
            case R.id.btn_login:
                String name = et_name.getText().toString().trim();
                String password = et_password.getText().toString().trim();
                //判断是否为空
                if (!TextUtils.isEmpty(name) & !TextUtils.isEmpty(password)) {
                    //准备登录时，show dialog
                    dialog.show();
                    //登录
                    final MyUser user = new MyUser();
                    user.setUsername(name);
                    user.setPassword(password);

                    user.login(LoginActivity.this, new SaveListener() {
                        @Override
                        public void onSuccess() {
                            //判断邮箱是否验证
                            if (user.getEmailVerified()) {
                                //跳转
                                Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                finish();
                            } else {
                                Toast.makeText(LoginActivity.this, "请前往邮箱验证", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    });
                } else {
                    Toast.makeText(this, "输入框不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv_forget:
                startActivity(new Intent(this, ForgetPasswordActivity.class));
                break;
        }
    }

    //假设输入用户名，但是不点击登录，而是退出

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dialog.dismiss();
        //保存状态
        ShareUtils.putBoolean(this, "keeppass", keep_password.isChecked());
        //是否记住密码
        if (keep_password.isChecked()) {
            //记住
            ShareUtils.putString(this, "name", et_name.getText().toString().trim());
            ShareUtils.putString(this, "password", et_password.getText().toString().trim());
        } else {
            ShareUtils.deleShare(this, "name");
            ShareUtils.deleShare(this, "password");
        }
    }
}
