package test.kw.videodemo;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

/**
 * 播放器集成
 *uri = Uri.parse(path);
 *         mVideoView.setVideoURI(uri);//设置视频播放地址
 *         mCustomMediaController.show(5000);
 *
 * private String path = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4";
 * private Uri uri;
 * @author TianJun
 *
 */
public class MainActivity extends Activity implements MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnInfoListener {
    //设置url
    private String path = "http://183.207.248.71:80/cntv/live1/CCTV-15/cctv-15";
    private Uri uri;
    //进度条
    private ProgressBar pb;

    private TextView downloadRateView, loadRateView;
    private VideoView mVideoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //检查Vitamio的库是否正常
        if (!LibsChecker.checkVitamioLibs(this)) {
            return;
        }
        //初始化框架
        Vitamio.isInitialized(getApplicationContext());
        setContentView(R.layout.activity_main);
        //初始化视频
        uri = Uri.parse(path);
        //初始化绑定相应控件
        pb = (ProgressBar) findViewById(R.id.probar);
        mVideoView = (VideoView) findViewById(R.id.buffer);
        downloadRateView = (TextView) findViewById(R.id.download_rate);
        loadRateView = (TextView) findViewById(R.id.load_rate);
        //判断UR是否为空
        if (path.equals("")) {
            //告诉用户需要设置媒体文件的URL地址路径
            Toast.makeText(MainActivity.this, "请输入媒体文件URL地址", Toast.LENGTH_LONG).show();
            return;
        } else {
            //设置播放地址
            mVideoView.setVideoURI(uri);
            //设置视频控制器，当然这个可以自定义控制器，我这里使用自带想控制器
            mVideoView.setMediaController(new MediaController(this));
            //获取焦点
            mVideoView.requestFocus();
            //设置状态信息监听
            mVideoView.setOnInfoListener(this);
            //设置数据更新监听
            mVideoView.setOnBufferingUpdateListener(this);
            //进行异步的准备
            mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    //播放速率的调节，需要Vitamio 4.0 以上
                    mediaPlayer.setPlaybackSpeed(1.0f);
                }
            });
        }
    }

    /**
     * 数据更新监听的回调函数
     * @param mp      the MediaPlayer the update pertains to
     * @param percent the percentage (0-100) of the buffer that has been filled thus
     */
    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
//        Log.i("onBufferingUpdate",percent+"");
        loadRateView.setText(percent + "%");
    }


    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        switch (what) {
            case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                //缓存开始，判断是否在播放，如果在，那就暂停播放，出现等待的响应提示
                if (mVideoView.isPlaying()) {
                    mVideoView.pause();
                    pb.setVisibility(View.VISIBLE);
                    downloadRateView.setText("");
                    loadRateView.setText("");
                    downloadRateView.setVisibility(View.VISIBLE);
                    loadRateView.setVisibility(View.VISIBLE);
                }
                break;
            case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                //缓存结束，视频开始播放，等待的响应相关控件消失
                mVideoView.start();
                pb.setVisibility(View.GONE);
                downloadRateView.setVisibility(View.GONE);
                loadRateView.setVisibility(View.GONE);
                break;
            case MediaPlayer.MEDIA_INFO_DOWNLOAD_RATE_CHANGED:
                //下载速率的显示
                downloadRateView.setText("" + extra + "kb/s" + "  ");
                break;
        }
        return false;
    }

    /**
     * Activity 失去焦点
     */
    @Override
    protected void onPause() {
        super.onPause();
        mVideoView.pause();//暂停
    }

    /**
     * Activity 获得焦点
     */
    @Override
    protected void onResume() {
        super.onResume();
        mVideoView.start();//开始
    }

    /**
     * Activity 停止
     * 停止摧毁后一定要判断是否有初始化，如果初始化后需要关闭释放Vitamio播放器
     * 不然会出现应用崩溃
     */
    @Override
    protected void onStop() {
        super.onStop();
        //判断是否初始化
        if (LibsChecker.checkVitamioLibs(this)) {
            //VideoView停止关闭
            mVideoView.stopPlayback();
        }
    }
}