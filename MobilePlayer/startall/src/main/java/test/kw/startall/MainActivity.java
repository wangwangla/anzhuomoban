package test.kw.startall;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startPlayer(View view) {
        /*String url = "http://vjs/zencdn.net/v/oceans.mp4";
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse(url),MimeTypeMap.getSingleton().getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(url)));
        startActivity(intent);*/
        /*Intent intent = new Intent();
        intent.setDataAndType(Uri.parse("http://vjs/zencdn.net/v/oceans.mp4"),"video/*");
        startActivity(intent);

        */
        Intent intent = new Intent();
        String type = "video/* ";
        Uri uri = Uri.parse("http://vjs/zencdn.net/v/oceans.mp4");
        intent.setDataAndType(uri, type);
        startActivity(intent);
    }
}
