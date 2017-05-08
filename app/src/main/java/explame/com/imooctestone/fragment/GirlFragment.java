package explame.com.imooctestone.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import explame.com.imooctestone.R;
import explame.com.imooctestone.adapter.GridAdapter;
import explame.com.imooctestone.entity.GirdData;
import explame.com.imooctestone.utils.L;
import explame.com.imooctestone.utils.StaticClass;

/*
 *      项目名：    ImoocTestOne
 *      包名：       explame.com.imooctestone.fragment
 *      时间           2017/5/2.
 *      创建者：    qzhuorui
 *      描述：        TODO
 */
public class GirlFragment extends Fragment {

    private GridView mGridVIew;
    private List<GirdData> mList = new ArrayList<>();
    private GridAdapter mAdapter;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_girl, null);
        findView(view);
        return view;
    }

    private void findView(View view) {
        mGridVIew = (GridView) view.findViewById(R.id.mGridView);

        //解析
        RxVolley.get(StaticClass.GIRL_URL, new HttpCallback() {
            @Override
            public void onSuccess(String t) {

                L.i("json:" + t);
                parsing(t);
            }
        });
    }

    private void parsing(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONArray jsonresult = jsonObject.getJSONArray("results");
            for (int i = 0; i < jsonresult.length(); i++) {
                JSONObject json = (JSONObject) jsonresult.get(i);
                String url = json.getString("url");
                GirdData data = new GirdData();
                data.setImgUrl(url);
                mList.add(data);
            }
            mAdapter = new GridAdapter(getActivity(), mList);
            mGridVIew.setAdapter(mAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
