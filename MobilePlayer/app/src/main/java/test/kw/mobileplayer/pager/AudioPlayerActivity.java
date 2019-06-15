package test.kw.mobileplayer.pager;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;

import test.kw.mobileplayer.R;

public class AudioPlayerActivity extends Activity {
    private ImageView imageView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);
        imageView =findViewById(R.id.iv_icon);
        //播放动画
        imageView.setBackgroundResource(R.drawable.animation);

        AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getBackground();
        animationDrawable.start();
    }
}

