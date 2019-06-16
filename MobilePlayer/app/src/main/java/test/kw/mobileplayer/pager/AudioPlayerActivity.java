package test.kw.mobileplayer.pager;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.widget.ImageView;

import test.kw.mobileplayer.IMusicPlayService;
import test.kw.mobileplayer.R;
import test.kw.mobileplayer.service.MusicPlayService;

public class AudioPlayerActivity extends Activity {
    private ImageView imageView ;
    private int position;
    //使用它调用服务的相关方法
    private IMusicPlayService iMusicPlayService;
    private ServiceConnection con = new ServiceConnection() {
        /**
         * 连接成功的时候调用
         * @param name
         * @param service
         */
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iMusicPlayService = IMusicPlayService.Stub.asInterface(service);
            if (iMusicPlayService!= null){
                try {
                    iMusicPlayService.openAudio(position);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

        /**
         * 断开连接的时候调用
         * 退出的是不会回调的
         * @param name
         */
        @Override
        public void onServiceDisconnected(ComponentName name) {
            try {
                if(iMusicPlayService!=null){
                    iMusicPlayService.stop();
                    iMusicPlayService=null;
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);
        imageView =findViewById(R.id.iv_icon);
        //播放动画
        imageView.setBackgroundResource(R.drawable.animation);

        AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getBackground();
        animationDrawable.start();
        //绑定数据
        position = getIntent().getIntExtra("position",0);
        //绑定服务
        Intent intent = new Intent(this,MusicPlayService.class);
        //设置动作
        intent.setAction("kw.test.demo.model_OPEN");
        //回调con
        bindService(intent,con,Context.BIND_AUTO_CREATE);
        //启动之后，就不会重复启动
        startService(intent);
    }
}

