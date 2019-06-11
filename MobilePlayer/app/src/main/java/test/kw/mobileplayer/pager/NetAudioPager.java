package test.kw.mobileplayer.pager;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import test.kw.mobileplayer.base.BasePager;

/*本地网络音乐
 */
public class NetAudioPager extends BasePager {
    public NetAudioPager(Context context){
        super(context);
    }
    private TextView textView ;

    @Override
    public View initView() {
        textView = new TextView(context);
        textView.setTextSize(40);
        return textView;
    }

    @Override
    public void initData() {
        textView.setText("网络音乐");
    }
}
