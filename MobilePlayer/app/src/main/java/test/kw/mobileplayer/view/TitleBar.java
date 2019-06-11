package test.kw.mobileplayer.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import test.kw.mobileplayer.R;

/**
 * 反射实例化
 *
 * 自定义标题栏
 */
public class TitleBar extends LinearLayout implements View.OnClickListener{
    /**
     * 代码中实例化类
     * @param context
     */
    //View的好处就是，以后怎样改都兼容
    private View tv_search;
    private View rl_game;
    private View iv_hosity;
    private Context context;
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
        this.context = context;
    }
    //有一个回调方法  布局文件完成时候
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        tv_search  = getChildAt(1);
        rl_game = getChildAt(2);
        iv_hosity = getChildAt(3);

        //设置点击事件
        tv_search.setOnClickListener(this);
        rl_game.setOnClickListener(this);
        iv_hosity.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_search:
                Toast.makeText(context,"search",Toast.LENGTH_SHORT).show();
                break;
            case R.id.rl_game:
                Toast.makeText(context,"game",Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_hosity:
                Toast.makeText(context,"hosity",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
