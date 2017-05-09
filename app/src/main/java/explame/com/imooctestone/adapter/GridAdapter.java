package explame.com.imooctestone.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.List;

import explame.com.imooctestone.R;
import explame.com.imooctestone.entity.GirdData;
import explame.com.imooctestone.utils.PicassoUtils;

/*
 *      项目名：    ImoocTestOne
 *      包名：       explame.com.imooctestone.adapter
 *      时间           2017/5/8.
 *      创建者：    qzhuorui
 *      描述：        妹子适配器
 */
public class GridAdapter extends BaseAdapter {

    private Context mContext;
    private List<GirdData> mList;
    private LayoutInflater inflater;
    private GirdData data;
    private WindowManager wm;
    //屏幕宽
    private int width;

    public GridAdapter(Context mContext, List<GirdData> mList) {
        this.mContext = mContext;
        this.mList = mList;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        width = wm.getDefaultDisplay().getWidth();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.girl_item, null);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.imagview);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        data = mList.get(position);
        //解析图片
        String url = data.getImgUrl();

        PicassoUtils.loadImgViewSize(mContext, url, width / 2, 500, viewHolder.imageView);

        return convertView;
    }

    class ViewHolder {
        private ImageView imageView;
    }
}
