package explame.com.imooctestone.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.LinearLayout;

/*
 *      项目名：    ImoocTestOne
 *      包名：       explame.com.imooctestone.view
 *      时间           2017/5/10.
 *      创建者：    qzhuorui
 *      描述：        时间分发
 */
public class DispatchLinearLayour extends LinearLayout{

    private DispatchKeyEventListener dispatchKeyEventListener;

    public DispatchLinearLayour(Context context) {
        super(context);
    }

    public DispatchLinearLayour(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DispatchLinearLayour(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public DispatchKeyEventListener getDispatchKeyEventListener() {
        return dispatchKeyEventListener;
    }

    public void setDispatchKeyEventListener(DispatchKeyEventListener dispatchKeyEventListener) {
        this.dispatchKeyEventListener = dispatchKeyEventListener;
    }

    //接口
    public static interface  DispatchKeyEventListener{
        boolean dispatchKeyEvent(KeyEvent event);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        //如果不为空 说明调用了
        if (dispatchKeyEventListener    != null){
            return dispatchKeyEventListener.dispatchKeyEvent(event);
        }
        return super.dispatchKeyEvent(event);
    }
}
