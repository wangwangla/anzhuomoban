// IMusicPlayService.aidl
package test.kw.mobileplayer;

// Declare any non-default types here with import statements

interface IMusicPlayService {
      /**
         *  常用方法
         */
        /**
         * 打开音频
         * @param position
         */
        void openAudio(int position);

        /**
         * 播放
         */
        void start();
        /**
         * 暂停
         */
        void pause();
        /**
         * 停止
         */
        void stop();
        /**
         * 播放进度
         */
        int getCurrentPosition();
        /**
         * 当前的时长
         */
        int getDuration();

        /**
         * 得到艺术家
         * @return
         */
        String getArtitst();
        /**
         * 得到名字
         * @return
         */
        String getName();
        /**
         * 得到歌曲路径
         * @return
         */
        String getAudioPath();

        void next();

        void pre();

        //播放模式
        void setPlayMode(int playMode);
        //播放模式
        int getPlayMode();
}
