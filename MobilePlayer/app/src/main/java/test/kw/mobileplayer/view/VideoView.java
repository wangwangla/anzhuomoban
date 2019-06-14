package test.kw.mobileplayer.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

/**
 * 自定义播放大小
 */
public class VideoView extends android.widget.VideoView {
    /**
     * 代码中调用实现
     * @param context
     */
    public VideoView(Context context) {
        this(context,null);
    }

    /**
     * 在布局中调用
     * @param context
     * @param attrs
     */
    public VideoView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    /**
     * 设置样式
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public VideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec,heightMeasureSpec);
    }

    /**
     * 设置视频的宽和高
     */
    public void setVideoSize(int videoWidth,int videoHeight){
        ViewGroup.LayoutParams params = getLayoutParams();
        params.width = videoWidth;
        params.height = videoHeight;
        setLayoutParams(params);
    }
}
