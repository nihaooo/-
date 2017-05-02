package explame.com.imooctestone.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

/*
 *      项目名：    ImoocTestOne
 *      包名：       explame.com.imooctestone.utils
 *      时间           2017/5/2.
 *      创建者：    qzhuorui
 *      描述：        工具统一类
 */
public class UtilTools {

    //设置字体
    public static void setFont(Context mContext, TextView textview) {
        Typeface fontType = Typeface.createFromAsset(mContext.getAssets(), "fonts/FONT.TTF");
        textview.setTypeface(fontType);
    }
}
