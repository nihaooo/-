package explame.com.imooctestone.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

/*
 *      项目名：    ImoocTestOne
 *      包名：       explame.com.imooctestone.utils
 *      时间           2017/5/8.
 *      创建者：    qzhuorui
 *      描述：        PicassoUtils封装
 */
public class PicassoUtils {

    //默认加载图片
    public static void loadImgView(Context mContext, String url, ImageView imageView) {
        Picasso.with(mContext).load(url).into(imageView);
    }

    //默认加载图片（指定大小）
    public static void loadImgViewSize(Context mContext, String url, int width, int height,
                                       ImageView imageView) {
        Picasso.with(mContext)
                .load(url)
                .resize(width, height)
                .centerCrop()
                .into(imageView);

    }

    //加载图片有默认图片
    public static void loadImgViewHolder(Context mContext, String url,
                                         int loadImg, int errorImg, ImageView imageView) {
        Picasso.with(mContext)
                .load(url)
                .placeholder(loadImg)//默认图片
                .error(errorImg)//加载错误的图片
                .into(imageView);
    }

    //裁剪图片
    public static void loadImgViewCrop(Context mContext, String url, ImageView imageView){
        Picasso.with(mContext).load(url).transform(new CropSquareTransformation()).into(imageView);


    }

    //按比例裁剪 矩形
    public static class CropSquareTransformation implements Transformation {
        @Override
        public Bitmap transform(Bitmap source) {
            int size = Math.min(source.getWidth(), source.getHeight());
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;
            Bitmap result = Bitmap.createBitmap(source, x, y, size, size);
            if (result != source) {
                //回收
                source.recycle();
            }
            return result;
        }

        @Override
        public String key() {
            return "lgl";
        }
    }
}
