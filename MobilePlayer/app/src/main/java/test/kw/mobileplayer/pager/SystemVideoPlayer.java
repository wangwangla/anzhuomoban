package test.kw.mobileplayer.pager;

import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import test.kw.mobileplayer.R;

public class SystemVideoPlayer extends Activity {
    private VideoView videoView;
    private Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_video_player);
        videoView = findViewById(R.id.videoview);
        //得到部分地址

        //准备好的监听
        videoView.setOnPreparedListener(new MyOnPreparedListener());
        //出错的监听
        videoView.setOnErrorListener(new MyOnErrorListener());
        //播放完成 的监听
        videoView.setOnCompletionListener(new MyOnComplatetionListener());
        uri = getIntent().getData();
        if (uri != null){
            videoView.setVideoURI(uri);
        }
        /*屏蔽自带的播放器，自定义播放器*/
        /*videoView.setMediaController(new MediaController(this));*/
    }
    class MyOnPreparedListener implements MediaPlayer.OnPreparedListener{

        @Override
        public void onPrepared(MediaPlayer mp) {
            videoView.start();
        }
    }
    class MyOnErrorListener implements MediaPlayer.OnErrorListener{

        @Override
        public boolean onError(MediaPlayer mp, int what, int extra) {

            return false;
        }
    }
    class MyOnComplatetionListener implements MediaPlayer.OnCompletionListener{

        @Override
        public void onCompletion(MediaPlayer mp) {

        }
    }
}
