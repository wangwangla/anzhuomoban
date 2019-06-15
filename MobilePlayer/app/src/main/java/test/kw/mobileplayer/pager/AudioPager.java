package test.kw.mobileplayer.pager;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

import test.kw.mobileplayer.R;
import test.kw.mobileplayer.adapter.VideoPagerAdapter;
import test.kw.mobileplayer.base.BasePager;
import test.kw.mobileplayer.domain.MediaItem;
import test.kw.mobileplayer.utils.Utils;

/*本地视频
 */
public class AudioPager extends BasePager {
    //初始化
    private ListView listView;
    private TextView tv_media;
    private ProgressBar progressBar;
    private ArrayList<MediaItem> arrayList;
    private Utils utils;
    private View view;

    public AudioPager(Context context){
        super(context);
        utils = new Utils();
    }

    @Override
    public View initView() {
        view = View.inflate(context, R.layout.video_pager,null);
        listView = view.findViewById(R.id.listView);
        tv_media = view.findViewById(R.id.tv_nomedia);
        progressBar = view.findViewById(R.id.pb_loading);
        listView.setOnItemClickListener(new MyOnItemClickListener());
        return view;
    }

    class MyOnItemClickListener implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(context,AudioPlayerActivity.class);
            intent.putExtra("position",position);
            context.startActivity(intent);
        }
    }
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (arrayList!=null&&arrayList.size()>0){
                //设置适配器，文本和进度条隐藏
                //文本隐藏
                listView.setAdapter(new VideoPagerAdapter(context,arrayList));
                tv_media.setVisibility(View.GONE);
            }else {
                tv_media.setVisibility(View.VISIBLE);
            }
            progressBar.setVisibility(View.GONE);
        }
    };
    @Override
    public void initData() {
        super.initData();
        //加载本地视频数据
        getLocalVideo();
    }
    private void getLocalVideo() {
        arrayList = new ArrayList();
        //一种遍历后缀名
        //一种查询数据库（内容提供者）
        new Thread(){
            @Override
            public void run() {
                super.run();
                //内容解析者
                ContentResolver contentResolver = context.getContentResolver();
                Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                //关心的部分
                String [] objs = {
                        MediaStore.Audio.Media.DISPLAY_NAME,//名字
                        MediaStore.Audio.Media.DURATION,//时长
                        MediaStore.Audio.Media.SIZE,
                        MediaStore.Audio.Media.DATA, //绝对地址
                        MediaStore.Audio.Media.ARTIST//作者
                };
                Cursor cursor = contentResolver.query(uri,objs,null,null,null);
                if(cursor!=null) {
                    while (cursor.moveToNext()) {
                        MediaItem mediaItem = new MediaItem();
                        String artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.ARTIST));
                        String displayName = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME));
                        String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
                        long duration = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION));
                        long size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE));
                        mediaItem.setName(displayName);
                        mediaItem.setDuration(duration);
                        mediaItem.setSize(size);
                        mediaItem.setData(path);
                        mediaItem.setArtist(artist);
                        arrayList.add(mediaItem);
                    }
                }
                cursor.close();
                handler.sendEmptyMessage(0);
            }
        }.start();
    }


}
