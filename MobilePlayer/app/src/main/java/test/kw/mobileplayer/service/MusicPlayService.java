package test.kw.mobileplayer.service;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.os.RemoteException;
import android.provider.MediaStore;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import test.kw.mobileplayer.IMusicPlayService;
import test.kw.mobileplayer.domain.MediaItem;

public class MusicPlayService extends Service {
    private ArrayList<MediaItem> arrayList;
    private int position;
    private MediaItem mediaItem;
    //用于部分音乐
    private MediaPlayer mediaPlayer;

    @Override
    public void onCreate() {
        super.onCreate();
        //服务创建的时候加载音乐列表
        getDataFromLocal();
    }
    private void getDataFromLocal() {
        arrayList = new ArrayList();
        //一种遍历后缀名
        //一种查询数据库（内容提供者）
        //服务也是在主线程中执行，所以需要new一下。
        new Thread(){
            @Override
            public void run() {
                super.run();
                //内容解析者
                ContentResolver contentResolver = getContentResolver();
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
            }
        }.start();
    }
    private IMusicPlayService.Stub stub = new IMusicPlayService.Stub() {
        private MusicPlayService musicPlayService=MusicPlayService.this;
        @Override
        public void openAudio(int position) throws RemoteException {
            musicPlayService.openAudio(position);
        }

        @Override
        public void start() throws RemoteException {
            musicPlayService.start();
        }

        @Override
        public void pause() throws RemoteException {
            musicPlayService.pause();
        }

        @Override
        public void stop() throws RemoteException {
            musicPlayService.stop();
        }

        @Override
        public int getCurrentPosition() throws RemoteException {
            return musicPlayService.getCurrentPosition();
        }

        @Override
        public int getDuration() throws RemoteException {
            return musicPlayService.getDuration();
        }

        @Override
        public String getArtitst() throws RemoteException {
            return musicPlayService.getArtitst();
        }

        @Override
        public String getName() throws RemoteException {
            return musicPlayService.getName();
        }

        @Override
        public String getAudioPath() throws RemoteException {
            return musicPlayService.getAudioPath();
        }

        @Override
        public void next() throws RemoteException {
            musicPlayService.next();
        }

        @Override
        public void pre() throws RemoteException {
            musicPlayService.pre();
        }

        @Override
        public void setPlayMode(int playMode) throws RemoteException {
            musicPlayService.setPlayMode(playMode);
        }

        @Override
        public int getPlayMode() throws RemoteException {
            return musicPlayService.getPlayMode();
        }
    };
    @Override
    public IBinder onBind(Intent intent) {
        return stub;
    }
    /**
     *  常用方法
     */
    /**
     * 打开音频   并播放
     * @param position
     */
    public void openAudio(int position){
        this.position = position;
        if (arrayList!=null&&arrayList.size()>0){
            mediaItem = arrayList.get(position);

            if (mediaPlayer!=null){
                //将结束掉在数据清除到
                mediaPlayer.release();
                mediaPlayer.reset();
            }
            mediaPlayer = new MediaPlayer();
            try {
                //设置监听，播放出错等
                //设置准备好监听
                mediaPlayer.setOnPreparedListener(new MyOnPreparedListener());
                //播放完成
                mediaPlayer.setOnCompletionListener(new MyOnCompletionListener());
                //播放出错
                mediaPlayer.setOnErrorListener(new MyOnErrorListener());
                mediaPlayer.setDataSource(mediaItem.getData());
                //我们使用异步的
                mediaPlayer.prepareAsync();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            Toast.makeText(MusicPlayService.this,"wushuju",Toast.LENGTH_SHORT).show();
        }
    }
    class MyOnErrorListener implements MediaPlayer.OnErrorListener{

        @Override
        public boolean onError(MediaPlayer mp, int what, int extra) {
            //服务死了
            next();
            return true;
        }
    }
    class MyOnCompletionListener implements MediaPlayer.OnCompletionListener{

        @Override
        public void onCompletion(MediaPlayer mp) {
            next();
        }
    }
    class MyOnPreparedListener implements MediaPlayer.OnPreparedListener{

        @Override
        public void onPrepared(MediaPlayer mp) {
            //准备好之候就开始播放了
            start();
        }
    }
    /**
     * 播放
     */
    public void start(){
        mediaPlayer.start();
    }
    /**
     * 暂停
     */
    public void pause(){
        mediaPlayer.pause();
    }
    /**
     * 停止
     */
    public void stop(){

    }
    /**
     * 播放进度
     */
    public int getCurrentPosition(){
        return 0;
    }
    /**
     * 当前的时长
     */
    public int getDuration(){
        return 0;
    }

    /**
     * 得到艺术家
     * @return
     */
    public String getArtitst(){
        return "";
    }
    /**
     * 得到名字
     * @return
     */
    public String getName(){
        return "";
    }
    /**
     * 得到歌曲路径
     * @return
     */
    public String getAudioPath(){
        return "";
    }

    public void next(){

    }

    public void pre(){

    }

    //播放模式
    public void setPlayMode(int playMode){

    }
    //播放模式
    public int getPlayMode(){
        return 0;
    }
}

