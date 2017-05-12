package explame.com.imooctestone.ui;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.ProgressListener;
import com.kymjs.rxvolley.http.VolleyError;
import com.kymjs.rxvolley.toolbox.FileUtils;

import explame.com.imooctestone.R;
import explame.com.imooctestone.utils.L;

/*
 *      项目名：    ImoocTestOne
 *      包名：       explame.com.imooctestone.ui
 *      时间           2017/5/12.
 *      创建者：    qzhuorui
 *      描述：        下载
 */
public class UpdateActivity extends BaseActivity {

    private TextView tv_size;
    private String url;
    private String path;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_update);
        initView();
    }

    private void initView() {
        tv_size = (TextView) findViewById(R.id.tv_size);
        path = FileUtils.getSDCardPath()+"/"+System.currentTimeMillis() + ".apk";
        //下载
        url = getIntent().getStringExtra("url");
        if(!TextUtils.isEmpty(url)){
            //下载
            RxVolley.download(path, url, new ProgressListener() {
                @Override
                public void onProgress(long transferredBytes, long totalSize) {
                    L.i("transferredBytes:" + transferredBytes + "totalSize:" + totalSize);
                }
            }, new HttpCallback() {
                @Override
                public void onSuccess(String t) {
                    L.e("成功");
                }

                @Override
                public void onFailure(VolleyError error) {
                    L.e("失败");
                }
            });
        }
    }
}
