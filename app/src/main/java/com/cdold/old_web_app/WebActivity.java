package com.cdold.old_web_app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.util.Locale;

public class WebActivity extends Activity implements TextToSpeech.OnInitListener {


    private WebView webview;
    private TextToSpeech textToSpeech;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        webview = new WebView(this);

        // 支持 JavaScript
        webview.getSettings().setJavaScriptEnabled(true);
        // JavaScript 调用 android 别名
        webview.addJavascriptInterface(WebActivity.this, "old_app");

        // 页面加载失败回调
        webview.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            /**
             * 网页加载失败的回调
             * @param view view WebView
             * @param request web资源请求请求实例
             * @param error web资源加载错误信息
             */
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                LogUtil.writeLog("网页加载失败！重新加载");
                // 重新加载网页
                view.reload();
            }
        });

        String url = ConfUtil.loadConfig();
        LogUtil.writeLog("当前访问 url：" + url);

        /**
         * 设置 webview 视图
         */
        webview.loadUrl(url);
        setContentView(webview);

        if (textToSpeech == null) {
            textToSpeech = new TextToSpeech(this, this);
        }
    }

    /**
     * 初始化语音引擎
     *
     * @param status 状态值，若初始化成功，值为 0
     */
    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            //初始化 tts 引擎
            int result = textToSpeech.setLanguage(Locale.CHINA);
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(this, "语音包丢失或语音不支持", Toast.LENGTH_SHORT).show();
                return;
            }
            LogUtil.writeLog("tts 语音初始化成功");
        }
        // 使用系统设置，防止冲突
//        textToSpeech.setPitch(1.0f);// 设置音调，,1.0是常规
//        textToSpeech.setSpeechRate(1.0f);//设定语速，1.0正常语速
    }

    /**
     * js 调用 android 的接口，执行播报指定文本的功能
     *
     * @param text 播报的文本内容
     */
    @JavascriptInterface
    public void talk(String text) {
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webview.canGoBack()) {
            webview.goBack();
            return true;
        }
        return false;
    }
}
