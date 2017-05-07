package explame.com.imooctestone.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import explame.com.imooctestone.R;
import explame.com.imooctestone.adapter.ChatListAdapter;
import explame.com.imooctestone.entity.ChatListData;

/*
 *      项目名：    ImoocTestOne
 *      包名：       explame.com.imooctestone.fragment
 *      时间           2017/5/2.
 *      创建者：    qzhuorui
 *      描述：        TODO
 */
public class ButlerFragment extends Fragment implements View.OnClickListener {

    private ListView mChatListView;
    //    private Button btn_left, btn_right;
    private List<ChatListData> mList = new ArrayList<>();
    private ChatListAdapter adapter;

    //输入框
    private EditText et_text;
    //发送按钮
    private Button btn_send;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_butler, null);
        findview(view);
        return view;
    }

    private void findview(View view) {
        mChatListView = (ListView) view.findViewById(R.id.mChatListView);
        //        btn_left = (Button) view.findViewById(btn_left);
        //        btn_left.setOnClickListener(this);
        //        btn_right = (Button) view.findViewById(btn_right);
        //        btn_right.setOnClickListener(this);

        et_text = (EditText) view.findViewById(R.id.et_text);
        btn_send = (Button) view.findViewById(R.id.btn_send);
        btn_send.setOnClickListener(this);

        //设置适配器
        ChatListAdapter adapter = new ChatListAdapter(getActivity(), mList);
        mChatListView.setAdapter(adapter);

        addLeftItem("你好，我是小管家");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //            case btn_left:
            //                addLeftItem("左边");
            //                break;
            //            case btn_right:
            //                addRightItem("右边");
            //                break;

            case R.id.btn_send:
                /**
                 * 1.获取输入框的内容
                 * 2.判断是否为空
                 * 3.判断长度不能大于30
                 * 4.清空当前输入框
                 * 5.添加输入的内容到right item
                 * 6.发送给机器人，请求返回内容
                 * 7.拿到机器人返回值后添加到left item
                 */

                //1.获取输入框的内容
                String text = et_text.getText().toString();
                //2.判断是否为空
                if (!TextUtils.isEmpty(text)) {
                    //3.判断长度不能大于30
                    if (text.length() > 30) {
                        Toast.makeText(getActivity(), "输入长度超出限制", Toast.LENGTH_SHORT).show();
                    } else {
                        //4.清空当前输入框
                        et_text.setText("");
                        //5.添加输入的内容到right item
                        addRightItem(text);
                        //6.发送给机器人，请求返回内容
                        String url = ;
                        RxVolley.get(url, new HttpCallback() {
                            @Override
                            public void onSuccess(String t) {
                                Toast.makeText(getActivity(), "Json:" + t, Toast.LENGTH_SHORT).show();
                                parsingJson(t);
                            }
                        });

                    }
                } else {
                    Toast.makeText(getActivity(), "输入框不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    //解析Json
    private void parsingJson(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONObject jsonresult = jsonObject.getJSONObject("result");
            //拿到返回值
            String text = jsonresult.getString("text");
            //7.拿到机器人返回值后添加到left item
            addLeftItem(text);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //添加左边文本
    private void addLeftItem(String text) {
        ChatListData date = new ChatListData();
        date.setType(ChatListAdapter.VALUE_LEFT_TEXT);
        date.setText(text);
        mList.add(date);
        //通知adapter刷新
        adapter.notifyDataSetChanged();
        //滚动到底部
        mChatListView.setSelection(mChatListView.getBottom());
    }

    //添加左边文本
    private void addRightItem(String text) {
        ChatListData date = new ChatListData();
        date.setType(ChatListAdapter.VALUE_RIGHT_TEXT);
        date.setText(text);
        mList.add(date);
        //通知adapter刷新
        adapter.notifyDataSetChanged();
        //滚动到底部
        mChatListView.setSelection(mChatListView.getBottom());
    }
}
