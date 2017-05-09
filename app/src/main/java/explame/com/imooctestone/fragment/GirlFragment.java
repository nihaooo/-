package explame.com.imooctestone.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import explame.com.imooctestone.R;
import explame.com.imooctestone.adapter.GridAdapter;
import explame.com.imooctestone.entity.GirdData;
import explame.com.imooctestone.utils.L;
import explame.com.imooctestone.utils.PicassoUtils;
import explame.com.imooctestone.view.CustomDialog;
import uk.co.senab.photoview.PhotoViewAttacher;

/*
 *      项目名：    ImoocTestOne
 *      包名：       explame.com.imooctestone.fragment
 *      时间           2017/5/2.
 *      创建者：    qzhuorui
 *      描述：        TODO
 */
public class GirlFragment extends Fragment {

    //列表
    private GridView mGridVIew;
    //数据
    private List<GirdData> mList = new ArrayList<>();
    //适配器
    private GridAdapter mAdapter;
    //提示框
    private CustomDialog dialog;
    //预览图片
    private ImageView iv_img;
    //图片地址的数据
    private List<String> mListUrl = new ArrayList<>();
    //photoView
    private PhotoViewAttacher mAttacher;

    /**
     * 1.监听点击事件
     * 2.提示框
     * 3.加载图片
     * 4.缩放
     */

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_girl, null);
        findView(view);
        return view;
    }

    private void findView(View view) {
        L.i("请求到的Girl数据：" );
        mGridVIew = (GridView) view.findViewById(R.id.mGridView);

        //初始化提示框
        dialog = new CustomDialog(getActivity(), LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT, R.layout.dialog_girl,
                R.style.Theme_dialog, Gravity.CENTER, R.style.pop_anim_style);

        iv_img = (ImageView) dialog.findViewById(R.id.iv_img);

        //接口不能出现中文字，所有我们要对福利这两个字进行转码
        String welfare = null;
        try {
            //Gank升级 需要转码
            welfare = URLEncoder.encode(getString(R.string.text_welfare), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String url = "http://gank.io/api/search/query/listview/category/" + welfare + "/count/50/page/1";
        //解析
        RxVolley.get(url, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                L.i("请求到的Girl数据：" + t);
                parsingJson(t);
            }
        });

        //监听点击事件
        mGridVIew.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //解析图片
                PicassoUtils.loadImgView(getActivity(), mListUrl.get(position), iv_img);
                //缩放
                mAttacher = new PhotoViewAttacher(iv_img);
                //刷新
                mAttacher.update();
                dialog.show();
            }
        });
    }

    private void parsingJson(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONArray jsonresult = jsonObject.getJSONArray("results");
            for (int i = 0; i < jsonresult.length(); i++) {
                JSONObject json = (JSONObject) jsonresult.get(i);
                String url = json.getString("url");
                mListUrl.add(url);
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
