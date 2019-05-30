package test.kw.my_vedio;

import android.content.Intent;
import android.webkit.RenderProcessGoneDetail;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class BlogFragment extends BaseFragment {
    private WebView mWebView;
    private ProgressBar mProgressBar;
    private static final int MAX_VALUE = 100;
    //URL
    private static final String BLOB_URL = "https://blog.csdn.net/fayfayfaydyt";
    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        //绑定web
        mWebView = bindViewId(R.id.webview);
        //绑定progress
        mProgressBar = bindViewId(R.id.pb_progress);

        WebSettings webSettings = mWebView.getSettings();//设置webView的属性
        //支持js  放大缩小
        webSettings.setBuiltInZoomControls(true);
        webSettings.setJavaScriptEnabled(true);

        mProgressBar.setMax(MAX_VALUE);
        mWebView.loadUrl(BLOB_URL);

        /*监听*/
        mWebView.setWebChromeClient(mWebChromeClient);


    }
    private WebViewClient webViewClient = new WebViewClient(){
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String request) {
            mWebView.loadUrl(request);
            return true;
        }
    };
    private WebChromeClient mWebChromeClient = new WebChromeClient(){
      /*目的是使用里面的一个方法*/

      public boolean shouldOverrideUrlLoading(WebView view, String request) {
          mWebView.loadUrl(request);
          return true;
      }
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            //更新
            mProgressBar.setProgress(newProgress);
            //如果大小为100的时候就将其隐藏掉
            if(newProgress==MAX_VALUE){
                mProgressBar.setVisibility(WebView.GONE);
            }
            super.onProgressChanged(view, newProgress);

        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_blog;
    }
}
