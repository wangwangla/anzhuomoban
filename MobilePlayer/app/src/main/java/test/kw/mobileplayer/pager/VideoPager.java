package test.kw.mobileplayer.pager;

import android.content.Context;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import test.kw.mobileplayer.R;
import test.kw.mobileplayer.base.BasePager;

/*本地视频
 */
public class VideoPager extends BasePager {
    //初始化
    private ListView listView;
    private TextView tv_media;
    private ProgressBar progressBar;
    public VideoPager(Context context){
        super(context);
    }
    private View view;
    @Override
    public View initView() {
        view = View.inflate(context, R.layout.video_pager,null);
        listView = view.findViewById(R.id.listView);
        tv_media = view.findViewById(R.id.tv_nomedia);
        progressBar = view.findViewById(R.id.pb_loading);
        return view;
    }

    @Override
    public void initData() {

    }
}
