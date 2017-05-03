package explame.com.imooctestone.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.UpdateListener;
import explame.com.imooctestone.R;
import explame.com.imooctestone.entity.MyUser;
import explame.com.imooctestone.ui.LoginActivity;

/*
 *      项目名：    ImoocTestOne
 *      包名：       explame.com.imooctestone.fragment
 *      时间           2017/5/2.
 *      创建者：    qzhuorui
 *      描述：        TODO
 */
public class UserFragment extends Fragment implements View.OnClickListener {

    private Button btn_exit_user, btn_update_ok;
    private TextView edit_user;
    private EditText et_username, et_sex, et_age, et_desc;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, null);
        findView(view);
        return view;
    }

    //初始化View
    private void findView(View view) {
        btn_exit_user = (Button) view.findViewById(R.id.btn_exit_user);
        btn_exit_user.setOnClickListener(this);
        edit_user = (TextView) view.findViewById(R.id.edit_user);
        edit_user.setOnClickListener(this);
        et_username = (EditText) view.findViewById(R.id.et_username);
        et_sex = (EditText) view.findViewById(R.id.et_sex);
        et_age = (EditText) view.findViewById(R.id.et_age);
        et_desc = (EditText) view.findViewById(R.id.et_desc);

        //默认是不可点击的，不可输入
        setEnabled(false);


        //设置具体的值
        MyUser userInfo = BmobUser.getCurrentUser(getActivity(), MyUser.class);
        et_username.setText(userInfo.getUsername());
        et_sex.setText(userInfo.isSex() ? "男" : "女");
        et_age.setText(userInfo.getAge() + "");
        et_desc.setText(userInfo.getDesc());

        btn_update_ok = (Button) view.findViewById(R.id.btn_update_ok);
        btn_update_ok.setOnClickListener(this);
    }

    //控制焦点
    private void setEnabled(boolean is) {
        et_username.setEnabled(is);
        et_sex.setEnabled(is);
        et_age.setEnabled(is);
        et_desc.setEnabled(is);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            //退出登录
            case R.id.btn_exit_user:
                //清除缓存对象
                MyUser.logOut(getActivity());
                //现在currenuser is NULL
                BmobUser currentUser = MyUser.getCurrentUser(getActivity(), MyUser.class);
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
                break;

            //编辑资料
            case R.id.edit_user:
                setEnabled(true);
                btn_update_ok.setVisibility(View.VISIBLE);
                break;

            //修改信息
            case R.id.btn_update_ok:
                //1.拿到输入框的值
                String username = et_username.getText().toString();
                String age = et_age.getText().toString();
                String sex = et_sex.getText().toString();
                String desc = et_desc.getText().toString();

                //判断是否为空
                if (!TextUtils.isEmpty(username) &
                        !TextUtils.isEmpty(age) &
                        !TextUtils.isEmpty(sex)) {
                    //3.更新属性
                    MyUser user = new MyUser();
                    user.setUsername(username);
                    user.setAge(Integer.parseInt(age));
                    //性别
                    if (sex.equals("男")) {
                        user.setSex(true);
                    } else {
                        user.setSex(false);
                    }
                    //简介
                    if (!TextUtils.isEmpty(desc)) {
                        user.setDesc(desc);
                    } else {
                        user.setDesc("这个人很懒，什么都没有留下");
                    }

                    BmobUser bmobUser = BmobUser.getCurrentUser(getActivity(), MyUser.class);
                    user.update(getActivity(), bmobUser.getObjectId(), new UpdateListener() {
                        @Override
                        public void onSuccess() {
                            //修改成功
                            setEnabled(false);
                            btn_update_ok.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), "修改成功", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            Toast.makeText(getActivity(), "修改失败", Toast.LENGTH_SHORT).show();
                        }
                    });


                } else {
                    Toast.makeText(getActivity(), "输入框不能为空", Toast.LENGTH_SHORT).show();
                }


                break;

        }
    }
}
