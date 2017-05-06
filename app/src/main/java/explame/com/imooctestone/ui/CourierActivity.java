package explame.com.imooctestone.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import explame.com.imooctestone.R;
import explame.com.imooctestone.adapter.CourierAdapter;
import explame.com.imooctestone.entity.CourierData;
import explame.com.imooctestone.utils.L;
import explame.com.imooctestone.utils.StaticClass;

/*
 *      项目名：    ImoocTestOne
 *      包名：       explame.com.imooctestone.ui
 *      时间           2017/5/6.
 *      创建者：    qzhuorui
 *      描述：        TODO
 */
public class CourierActivity extends BaseActivity implements View.OnClickListener {

    private EditText et_name, et_number;
    private Button btn_get_courier;
    private ListView mListView;

    private List<CourierData> mList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courier);

        initView();
    }

    private void initView() {
        et_name = (EditText) findViewById(R.id.et_name);
        et_number = (EditText) findViewById(R.id.et_number);
        btn_get_courier = (Button) findViewById(R.id.btn_get_courier);
        btn_get_courier.setOnClickListener(this);
        mListView = (ListView) findViewById(R.id.mListView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_get_courier:
                /**
                 * 1.获取输入框内容
                 * 2.判断是否为空
                 * 3.拿到数据去请求数据（Json）
                 * 4.解析Json
                 * 5.Listview适配器
                 * 6.实体类(item)
                 * 7.设置数据、显示效果
                 */

                //1.获取数据
                String name = et_name.getText().toString().trim();
                String number = et_number.getText().toString().trim();

                //拼接Url
                String url = "http://v.juhe.cn/exp/index?key=" + StaticClass.COURIER_KEY +
                        "&com=" + name + "&no=" + number;

                //2.判断是否为空
                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(number)) {
                    //3.拿到数据去请求数据（Json）
                    RxVolley.get(url, new HttpCallback() {
                        @Override
                        public void onSuccess(String t) {
                            Toast.makeText(CourierActivity.this, t, Toast.LENGTH_SHORT).show();
                            L.i("Json:" + t);
                            //4.解析Json
                            parsingJson(t);
                        }
                    });


                } else {
                    Toast.makeText(this, "输入框不能为空", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    //解析数据
    private void parsingJson(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONObject jsonresult = jsonObject.getJSONObject("result");
            JSONArray jsonArray = jsonresult.getJSONArray("list");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject json = (JSONObject) jsonArray.get(i);

                CourierData data = new CourierData();
                data.setRemark(json.getString("remark"));
                data.setZone(json.getString("zone"));
                data.setDatetime(json.getString("datetime"));
                mList.add(data);
            }
            //倒序
            Collections.reverse(mList);
            CourierAdapter adapter = new CourierAdapter(this, mList);
            mListView.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
