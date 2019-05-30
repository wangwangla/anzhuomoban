package test.kw.my_vedio;

import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.widget.TextView;

public class AboutFragment extends BaseFragment {
    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        TextView textView = bindViewId(R.id.tv_app_des);
        /*文字链接可以点击*/
        textView.setAutoLinkMask(Linkify.ALL);
        //滚动
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_about;
    }
}
