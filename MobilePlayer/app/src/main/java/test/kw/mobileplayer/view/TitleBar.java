package test.kw.mobileplayer.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * 反射实例化
 *
 * 自定义标题栏
 */
public class TitleBar extends LinearLayout {
    /**
     * 代码中实例化类
     * @param context
     */
    public TitleBar(Context context) {
        //super(context);
        this(context,null);
    }

    /**
     * 布局文件中实例化
     * @param context
     * @param attrs
     */
    public TitleBar(Context context, AttributeSet attrs) {
        //super(context, attrs);
        this(context,attrs,0);
    }

    /**
     * 当需要设置样式的时候调用
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    //有一个回调方法  布局文件完成时候
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }
}
