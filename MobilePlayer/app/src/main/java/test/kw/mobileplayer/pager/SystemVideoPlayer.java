package test.kw.mobileplayer.pager;

import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import test.kw.mobileplayer.R;
import test.kw.mobileplayer.utils.Utils;

public class SystemVideoPlayer extends Activity implements View.OnClickListener {
    private Utils utils;
    private VideoView videoView;
    private Uri uri;
    private LinearLayout llTop;
    private TextView tvName;
    private ImageView ivBattery;
    private TextView tvSystemTime;
    private Button btnVoice;
    private SeekBar seekbarVoice;
    private Button btnSwitchPlayer;
    private LinearLayout llBottom;
    private TextView tvCurrentTime;
    private SeekBar seekbarVideo;
    private TextView tvDuration;
    private Button btnExit;
    private Button btnVideoPre;
    private Button btnVideoStartPause;
    private Button btnVideoNext;
    private Button btnVideoSwitchScreen;
    private static final int PROGRESS = 1;
    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2019-06-13 11:13:34 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        setContentView(R.layout.activity_system_video_player);
        videoView = findViewById(R.id.videoview);
        llTop = (LinearLayout)findViewById( R.id.ll_top );
        tvName = (TextView)findViewById( R.id.tv_name );
        ivBattery = (ImageView)findViewById( R.id.iv_battery );
        tvSystemTime = (TextView)findViewById( R.id.tv_system_time );
        btnVoice = (Button)findViewById( R.id.btn_voice );
        seekbarVoice = (SeekBar)findViewById( R.id.seekbar_voice );
        btnSwitchPlayer = (Button)findViewById( R.id.btn_switch_player );
        llBottom = (LinearLayout)findViewById( R.id.ll_bottom );
        tvCurrentTime = (TextView)findViewById( R.id.tv_current_time );
        seekbarVideo = (SeekBar)findViewById( R.id.seekbar_video );
        tvDuration = (TextView)findViewById( R.id.tv_duration );
        btnExit = (Button)findViewById( R.id.btn_exit );
        btnVideoPre = (Button)findViewById( R.id.btn_video_pre );
        btnVideoStartPause = (Button)findViewById( R.id.btn_video_start_pause );
        btnVideoNext = (Button)findViewById( R.id.btn_video_next );
        btnVideoSwitchScreen = (Button)findViewById( R.id.btn_video_switch_screen );

        btnVoice.setOnClickListener( this );
        btnSwitchPlayer.setOnClickListener( this );
        btnExit.setOnClickListener( this );
        btnVideoPre.setOnClickListener( this );
        btnVideoStartPause.setOnClickListener( this );
        btnVideoNext.setOnClickListener( this );
        btnVideoSwitchScreen.setOnClickListener( this );
    }

    /**
     * Handle button click events<br />
     * <br />
     * Auto-created on 2019-06-13 11:13:34 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    @Override
    public void onClick(View v) {
        if ( v == btnVoice ) {
            // Handle clicks for btnVoice
        } else if ( v == btnSwitchPlayer ) {
            // Handle clicks for btnSwitchPlayer
        } else if ( v == btnExit ) {
            // Handle clicks for btnExit
        } else if ( v == btnVideoPre ) {
            // Handle clicks for btnVideoPre
        } else if ( v == btnVideoStartPause ) {
            // Handle clicks for btnVideoStartPause
            if (videoView.isPlaying()){
                videoView.pause();
                btnVideoStartPause.setBackgroundResource(R.drawable.btn_video_start_selector);
            }else {
                videoView.start();
                btnVideoStartPause.setBackgroundResource(R.drawable.btn_video_pause_selector);
            }
        } else if ( v == btnVideoNext ) {
            // Handle clicks for btnVideoNext
        } else if ( v == btnVideoSwitchScreen ) {
            // Handle clicks for btnVideoSwitchScreen
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViews();
        //得到部分地址
        utils = new Utils();
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
            //视频总时长
            int duration = videoView.getDuration();
            tvDuration.setText(utils.stringForTime(duration));
            seekbarVideo.setMax(duration);
            //发消息
            handler.sendEmptyMessage(PROGRESS);
        }
    }
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case PROGRESS:
                    //当前播放进度
                    int currentPosition = videoView.getCurrentPosition();
                    //设置
                    seekbarVideo.setProgress(currentPosition);
                    //每秒一次
                    tvCurrentTime.setText(utils.stringForTime(currentPosition));
                    removeMessages(PROGRESS);
                    sendEmptyMessageDelayed(PROGRESS,1000);
                    break;
            }
        }
    };
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
