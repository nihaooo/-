package explame.com.imooctestone.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cn.bmob.v3.listener.ResetPasswordByEmailListener;
import cn.bmob.v3.listener.UpdateListener;
import explame.com.imooctestone.R;
import explame.com.imooctestone.entity.MyUser;

/*
 *      项目名：    ImoocTestOne
 *      包名：       explame.com.imooctestone.ui
 *      时间           2017/5/3.
 *      创建者：    qzhuorui
 *      描述：        忘记、重置密码
 */
public class ForgetPasswordActivity extends BaseActivity implements View.OnClickListener {

    private Button btn_forget_password, btn_update_password;
    private EditText et_email, et_now, et_new, et_new_password;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);

        initView();
    }

    private void initView() {
        btn_forget_password = (Button) findViewById(R.id.btn_forget_password);
        btn_forget_password.setOnClickListener(this);
        et_email = (EditText) findViewById(R.id.et_email);
        et_now = (EditText) findViewById(R.id.et_now);
        et_new = (EditText) findViewById(R.id.et_new);
        et_new_password = (EditText) findViewById(R.id.et_new_password);
        btn_update_password = (Button) findViewById(R.id.btn_update_password);
        btn_update_password.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_forget_password:
                final String email = et_email.getText().toString().trim();
                if (!TextUtils.isEmpty(email)) {

                    //发送邮件
                    MyUser.resetPasswordByEmail(ForgetPasswordActivity.this, email, new ResetPasswordByEmailListener() {
                        @Override
                        public void onSuccess() {
                            Toast.makeText(ForgetPasswordActivity.this, "邮件已发送至" + email, Toast.LENGTH_SHORT).show();
                            finish();
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            Toast.makeText(ForgetPasswordActivity.this, "邮件发送失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(this, "输入不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_update_password:
                String now = et_now.getText().toString().trim();
                String news = et_new.getText().toString().trim();
                String new_password = et_new_password.getText().toString();
                //判断是否为空
                if (!TextUtils.isEmpty(now) & !TextUtils.isEmpty(news) & !TextUtils.isEmpty(new_password)) {
                    //判断两次密码是否一致
                    if (news.equals(new_password)) {
                        //重置
                        MyUser.updateCurrentUserPassword(ForgetPasswordActivity.this, now, news, new UpdateListener() {
                            @Override
                            public void onSuccess() {
                                Toast.makeText(ForgetPasswordActivity.this, "重置密码成功", Toast.LENGTH_SHORT).show();
                                finish();
                            }

                            @Override
                            public void onFailure(int i, String s) {
                                Toast.makeText(ForgetPasswordActivity.this, "重置密码失败", Toast.LENGTH_SHORT).show();
                            }
                        });

                    } else {
                        Toast.makeText(this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "输入不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
