package test.kw.mobileplayer.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class MusicPlayService extends Service {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    /**
     *  常用方法
     */
    /**
     * 打开音频
     * @param position
     */
    public void openBind(int position){

    }

    /**
     * 播放
     */
    public void start(){

    }
    /**
     * 暂停
     */
    public void pause(){

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

