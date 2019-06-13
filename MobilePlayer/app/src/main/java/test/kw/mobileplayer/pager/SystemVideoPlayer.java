package test.kw.mobileplayer.pager;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import test.kw.mobileplayer.R;
import test.kw.mobileplayer.domain.MediaItem;
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
    private ArrayList<MediaItem> arrayList;
    private int position = 0;
    //监听电量的广播
    private MyReceiver myReceiver;
    /**
     * 手势识别器  双击  单机  长按
     * onTouchEvent()方法将事件传递给手势识别器。
     * 定义手势识别器
     */
    private GestureDetector detector;
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
            finish();
        } else if ( v == btnVideoPre ) {
            // Handle clicks for btnVideoPre
            startPre();
        } else if ( v == btnVideoStartPause ) {
            // Handle clicks for btnVideoStartPause
            startAndPause();
        } else if ( v == btnVideoNext ) {
            startNext();
        } else if ( v == btnVideoSwitchScreen ) {
            // Handle clicks for btnVideoSwitchScreen
        }
    }

    private void startAndPause(){
        if (videoView.isPlaying()){
            videoView.pause();
            btnVideoStartPause.setBackgroundResource(R.drawable.btn_video_start_selector);
        }else {
            videoView.start();
            btnVideoStartPause.setBackgroundResource(R.drawable.btn_video_pause_selector);
        }
    }

    private void startPre() {
        if (arrayList!=null&&arrayList.size()>0){
            position--;
            if (position>=0){
                MediaItem mediaItem = arrayList.get(position);
                tvName.setText(mediaItem.getName());
                videoView.setVideoPath(mediaItem.getData());
                setButtonStatus();
            }
        }
    }

    private void startNext(){
        // Handle clicks for btnVideoNext
        if (arrayList!=null&&arrayList.size()>0){
            position++;
            if (position<arrayList.size()){
                MediaItem mediaItem = arrayList.get(position);
                tvName.setText(mediaItem.getName());
                videoView.setVideoPath(mediaItem.getData());
                //设置按状态
                setButtonStatus();
            }
        }else if (uri!=null){
            setButtonStatus();
        }
    }
    private void setButtonStatus(){

        if (position==0){
            btnVideoPre.setBackgroundResource(R.drawable.btn_pre_gray);
            //设置不可点
            btnVideoPre.setEnabled(false);
        }else {
            btnVideoPre.setBackgroundResource(R.drawable.btn_video_pre_selector);
            //设置不可点
            btnVideoPre.setEnabled(true);
        }
        if (arrayList.size()==position+1) {
            btnVideoNext.setBackgroundResource(R.drawable.btn_next_gray);
            btnVideoNext.setEnabled(false);
        }else {
            btnVideoNext.setBackgroundResource(R.drawable.btn_video_next_selector);
            btnVideoNext.setEnabled(true);
        }
    }
   /* private void setButtonStatus() {
        if (arrayList!=null&&arrayList.size()>0){
            if (arrayList.size()==1){
                //两个按钮设置灰色
                btnVideoPre.setBackgroundResource(R.drawable.btn_pre_gray);
                //设置不可点
                btnVideoPre.setEnabled(false);
                btnVideoNext.setBackgroundResource(R.drawable.btn_next_gray);
                btnVideoNext.setEnabled(false);
            }else
        }else if (uri!=null){
            //两个按钮设置灰色
            btnVideoPre.setBackgroundResource(R.drawable.btn_pre_gray);
            //设置不可点
            btnVideoPre.setEnabled(false);
            btnVideoNext.setBackgroundResource(R.drawable.btn_next_gray);
            btnVideoNext.setEnabled(false);
        }
    }
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViews();
        //得到部分地址
        utils = new Utils();
        //注册广播
        myReceiver = new MyReceiver();
        IntentFilter intentFiler = new IntentFilter();
        //对某个动作感兴趣
        intentFiler.addAction(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(myReceiver,intentFiler);
        //准备好的监听
        videoView.setOnPreparedListener(new MyOnPreparedListener());
        //出错的监听
        videoView.setOnErrorListener(new MyOnErrorListener());
        //播放完成 的监听
        videoView.setOnCompletionListener(new MyOnComplatetionListener());
        //seekBar状态监听
        seekbarVideo.setOnSeekBarChangeListener(new MyOnSeekBarChangeListener());
        uri = getIntent().getData();
        //得到bundle中数据
        arrayList = (ArrayList<MediaItem>) getIntent().getSerializableExtra("videolist");
        position = getIntent().getIntExtra("position",0);
        if (arrayList!=null&&arrayList.size()>0){
            MediaItem mediaItem = arrayList.get(position);
            tvName.setText(mediaItem.getName());
            videoView.setVideoPath(mediaItem.getData());
        }else if (uri != null){
            tvName.setText(uri.toString());
            videoView.setVideoURI(uri);
        }else {
            Toast.makeText(SystemVideoPlayer.this,"无参数",Toast.LENGTH_SHORT).show();
        }
        /*屏蔽自带的播放器，自定义播放器*/
        /*videoView.setMediaController(new MediaController(this));*/
        //初始化手势识别器  实现方法
        detector = new GestureDetector(this,new GestureDetector.SimpleOnGestureListener(){
            //长按暂停 播放
            @Override
            public void onLongPress(MotionEvent e) {
                super.onLongPress(e);
                Toast.makeText(SystemVideoPlayer.this,"长按",Toast.LENGTH_SHORT).show();
                startAndPause();
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                Toast.makeText(SystemVideoPlayer.this,"双击",Toast.LENGTH_SHORT).show();
                return super.onDoubleTap(e);
            }

            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                Toast.makeText(SystemVideoPlayer.this,"单机",Toast.LENGTH_SHORT).show();
                return super.onSingleTapConfirmed(e);
            }
        });
    }
    class MyReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            //监听电量变化
            int level = intent.getIntExtra("level",0);//0~100
            setBattery(level);
        }
    }

    private void setBattery(int level) {
        if (level<=0){
            ivBattery.setImageResource(R.drawable.ic_battery_0);
        }else if (level<=10){
            ivBattery.setImageResource(R.drawable.ic_battery_10);
        }else if (level<=30){
            ivBattery.setImageResource(R.drawable.ic_battery_20);
        }else if (level<=40){
            ivBattery.setImageResource(R.drawable.ic_battery_40);
        }else if (level<=60){
            ivBattery.setImageResource(R.drawable.ic_battery_60);
        }else if (level<=80){
            ivBattery.setImageResource(R.drawable.ic_battery_80);
        }else if (level<=100){
            ivBattery.setImageResource(R.drawable.ic_battery_100);
        }else {
            ivBattery.setImageResource(R.drawable.ic_battery_100);
        }
    }

    class MyOnSeekBarChangeListener implements SeekBar.OnSeekBarChangeListener{
        //手指滑动
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
           if (fromUser){
               videoView.seekTo(progress);
           }
        }
        //手指触碰

        /**
         *
         * @param seekBar
         */
        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }
        //手指移开回调
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }

    class MyOnPreparedListener implements MediaPlayer.OnPreparedListener{

        @Override
        public void onPrepared(MediaPlayer mp) {
            videoView.start();
            setButtonStatus();
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
                    //设置系统时间
                    tvSystemTime.setText(getSystemTime());
                    //每秒一次
                    tvCurrentTime.setText(utils.stringForTime(currentPosition));
                    removeMessages(PROGRESS);
                    sendEmptyMessageDelayed(PROGRESS,1000);
                    break;
            }
        }
    };

    /**
     * 系统时间
     * @return
     */
    private String getSystemTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        return simpleDateFormat.format(new Date());
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
            startNext();
        }
    }

    /**
     * 释放资源，先释放子类，在释放父类
     *
     * 初始化先初始化父类，在初始化子类
     */
    @Override
    protected void onDestroy() {
        if(myReceiver!=null) {
            unregisterReceiver(myReceiver);
            myReceiver = null;
        }
        super.onDestroy();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //必须有的一步  将按下传输给手势识别器
        detector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
}
