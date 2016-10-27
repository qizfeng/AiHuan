package com.zipingfang.aihuan;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.zipingfang.aihuan.constants.Constants;

/**
 * Created by 峰 on 2016/9/13.
 */
public class WebViewActivity extends BaseActivity implements View.OnClickListener {
    private String url;
    private WebView webView;
    private TextView tv_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zpf_activity_webview);
        initActionBar();
        url = getIntent().getStringExtra(Constants.WEBVIEW_URL);
        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        WebSettings webSettings = webView.getSettings();
        webSettings.setSupportMultipleWindows(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setBuiltInZoomControls(true);
        Log.e("qizfeng","web:"+url);
        webView.loadUrl(url);
        // 加载数据
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
//                if (newProgress == 100) {
//                    tv_title.setText("注册协议");
//                } else {
//                    tv_title.setText("loading...");
//                }
            }
        });

        // 这个是当网页上的连接被点击的时候
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    private void initActionBar() {
//        View view = findViewById(R.id.il_topbar);
//        tv_title = (TextView) view.findViewById(R.id.tv_title);
//        tv_title.setText("注册协议");
//        ImageButton btn_back = (ImageButton) view.findViewById(R.id.btn_back);
//        btn_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
        }
    }

    @Override
    // 设置回退
    // 覆盖Activity类的onKeyDown(int keyCoder,KeyEvent event)方法
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack(); // goBack()表示返回WebView的上一页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
