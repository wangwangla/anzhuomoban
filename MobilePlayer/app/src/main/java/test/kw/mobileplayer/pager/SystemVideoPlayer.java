package test.kw.mobileplayer.pager;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import test.kw.mobileplayer.view.VideoView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import test.kw.mobileplayer.R;
import test.kw.mobileplayer.domain.MediaItem;
import test.kw.mobileplayer.utils.Utils;

public class SystemVideoPlayer extends Activity implements View.OnClickListener {
    //屏幕大小
    private int screenWidth = 0;
    private int screenHight = 0;

    //设置全屏
    private final int FULL_SCREEN = 1;
    //设置默认
    private final int DEFAULT_SCREEN = 2;

    //是否全屏
    private boolean isFullScreen = false;
    private static final int HIDECONTROLLER = 2;
    //状态栏和底部栏
    private RelativeLayout media_controller;
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

    private boolean isShowMediaController = false;

    //监听电量的广播
    private MyReceiver myReceiver;
    /**
     * 手势识别器  双击  单机  长按
     * onTouchEvent()方法将事件传递给手势识别器。
     * 定义手势识别器
     */
    private GestureDetector detector;
    private int videoWidth;
    private int videoHeight;

    /**
     * 调节声音
     */
    private AudioManager am;
    //当前声音
    private int currentVoice;
    //最大的音量  0~15
    private int maxVoice;
    //默认是
    private boolean isMute = false;

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2019-06-13 11:13:34 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        setContentView(R.layout.activity_system_video_player);
        media_controller = findViewById(R.id.media_controller);
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

        //设置参数

    }
    public void setFullAndDefault(){
        if (isFullScreen){
            //设置默认
            setVideoType(DEFAULT_SCREEN);
        }else {
            //设置全屏
            setVideoType(FULL_SCREEN);
        }
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
            // 静音
            isMute=!isMute;
            updataVoice(currentVoice,isMute);
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
            setFullAndDefault();
        }
        //先移除，再发
        handler.removeMessages(HIDECONTROLLER);
        handler.sendEmptyMessageDelayed(HIDECONTROLLER,4000);
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

        //设置声音监听
        seekbarVoice.setOnSeekBarChangeListener(new VoiceSeekBarChangeListener());

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

            /**
             *
             * 默认和全屏的切换
             * @param e
             * @return
             */
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                setFullAndDefault();
                return super.onDoubleTap(e);
            }

            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                if (isShowMediaController){
                    //yincang
                    hideMediaController();
                    //消除消息
                    handler.removeMessages(HIDECONTROLLER);
                }else {
                    //show
                    showMediaController();
                    //show ，we can sengmessage
                    handler.sendEmptyMessageDelayed(HIDECONTROLLER,4000);
                }
                return super.onSingleTapConfirmed(e);
            }
        });

        //初始化长和宽   过时方法
        /*screenWidth = getWindowManager().getDefaultDisplay().getWidth();
        screenHight = getWindowManager().getDefaultDisplay().getHeight();*/

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        screenHight = metrics.heightPixels;
        screenWidth = metrics.widthPixels;
        //得到音量
        am = (AudioManager) getSystemService(AUDIO_SERVICE);
        //最大的
        maxVoice = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        //当前的
        currentVoice = am.getStreamVolume(AudioManager.STREAM_MUSIC);
        seekbarVoice.setMax(maxVoice);
        seekbarVoice.setProgress(currentVoice);
    }
    class VoiceSeekBarChangeListener implements SeekBar.OnSeekBarChangeListener{

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (fromUser){
                //默认不是静音
                if (progress>0){
                    isMute = false;
                }else {
                    isMute = true;
                }
                updataVoice(progress,isMute);
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            handler.removeMessages(HIDECONTROLLER);
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            handler.sendEmptyMessageDelayed(HIDECONTROLLER,4000);
        }
    }

    private void updataVoice(int progress,boolean isMute) {
        //1.将系统的调出来   0不会调用系统的
        if (isMute) {
            am.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 1);
            seekbarVoice.setProgress(progress);
            currentVoice = progress;
        }else {
            am.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0);
            seekbarVoice.setProgress(0);
        }
    }

    private void setVideoType(int videoType) {
        switch (videoType){
            case FULL_SCREEN:
                //1.设置屏幕大小----屏幕大小
                videoView.setVideoSize(screenWidth,screenHight);
                //2.设置按钮状态
                btnVideoSwitchScreen.setBackgroundResource(R.drawable.btn_video_switch_screen_default_selector);
                //3.设置全屏状态
                isFullScreen = true;
                break;
            case DEFAULT_SCREEN:
                int mVideoWidth = videoWidth;
                int mVideoHeight = videoHeight;

                int width = screenWidth;
                int height = screenHight;

                // for compatibility, we adjust size based on aspect ratio
                if ( mVideoWidth * height  < width * mVideoHeight ) {
                    //Log.i("@@@", "image too wide, correcting");
                    width = height * mVideoWidth / mVideoHeight;
                } else if ( mVideoWidth * height  > width * mVideoHeight ) {
                    //Log.i("@@@", "image too tall, correcting");
                    height = width * mVideoHeight / mVideoWidth;
                }
                videoView.setVideoSize(width,height);
                //设置参数
                btnVideoSwitchScreen.setBackgroundResource(R.drawable.btn_video_switch_screen_full_selector);
                isFullScreen = false;
                break;
        }
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
         *手指触碰就移除消息
         * @param seekBar
         */
        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            handler.removeMessages(HIDECONTROLLER);
        }
        //手指移开回调
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            handler.sendEmptyMessageDelayed(HIDECONTROLLER,4000);
        }
    }

    class MyOnPreparedListener implements MediaPlayer.OnPreparedListener{

        @Override
        public void onPrepared(MediaPlayer mp) {
            videoWidth =  mp.getVideoWidth();
            videoHeight = mp.getVideoHeight();
            videoView.start();
            setButtonStatus();
            //视频总时长
            int duration = videoView.getDuration();
            tvDuration.setText(utils.stringForTime(duration));
            seekbarVideo.setMax(duration);
            hideMediaController();//默认隐藏
            //发消息
            handler.sendEmptyMessage(PROGRESS);
            /**
             * 设置宽和高
             */
            //videoView.setVideoSize(100,100);
            /**
             * 获取视频真实的宽和高
             */
            //videoView.setVideoSize(mp.getVideoWidth(),mp.getVideoHeight());
            setVideoType(DEFAULT_SCREEN);
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
                case HIDECONTROLLER:
                    hideMediaController();
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
    private void showMediaController(){
        media_controller.setVisibility(View.VISIBLE);
        isShowMediaController = true;
    }
    private void hideMediaController(){
        media_controller.setVisibility(View.GONE);
        isShowMediaController = false;
    }
}
