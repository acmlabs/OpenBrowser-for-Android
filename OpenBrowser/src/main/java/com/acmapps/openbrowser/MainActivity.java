package com.acmapps.openbrowser;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.*;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.*;
import android.os.*;
import android.webkit.*;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    int version = Build.VERSION.SDK_INT ;

    private WebView mWebView;
    String URL = "http://google.com/";

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);

        final ProgressBar loadingProgressBar;

        mWebView = (WebView) findViewById(R.id.webView);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl("file:///android_asset/index.html");
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mWebView.getSettings().setSupportZoom(true);

        //webView Settings
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().supportZoom();

        mWebView.setWebViewClient(new MyWebViewClient());

        loadingProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        mWebView.setWebChromeClient(new WebChromeClient() {

            // this will be called on page loading progress

            @Override

            public void onProgressChanged(WebView view, int newProgress) {

                TextView mTextView = (TextView) findViewById(R.id.urlTitleText);
                String title = mWebView.getTitle();

                super.onProgressChanged(view, newProgress);


                loadingProgressBar.setProgress(newProgress);

                if (newProgress == 100) {
                    loadingProgressBar.setVisibility(View.GONE);
                    mTextView.setVisibility(View.VISIBLE);
                    if (title == null){
                        mTextView.setText("OpenBrowser");
                    }else if (!title.equals(null)) {
                        mTextView.setText(title + " - OpenBrowser");
                    }

                } else {
                    loadingProgressBar.setVisibility(View.VISIBLE);

                }
            }
        });



        hideActionBar();

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(keyCode == KeyEvent.KEYCODE_BACK){
            mWebView.goBack();
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    private class MyWebViewClient extends WebViewClient {


        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            view.loadUrl(url);
            return true;
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void hideActionBar(){
        if (version >= Build.VERSION_CODES.HONEYCOMB){
            ActionBar actionBar = getActionBar();
            if (actionBar != null) {
                actionBar.hide();
            }
        }
    }

    @SuppressWarnings("ConstantConditions")
    public void goClick(View view){
        //Get URL from EditText
        mWebView = (WebView) findViewById(R.id.webView);
        EditText mEditText = (EditText) findViewById(R.id.addressText);

        String url = mEditText.getText().toString();

        mWebView.loadUrl("http://" + url);
    }

    public void backButton(View view){
        mWebView.goBack();
    }

    public void fwdButton(View view){
        mWebView.goForward();
    }

    public void stopButton(View view){
        mWebView.stopLoading();

        Context context = getApplicationContext();
        CharSequence text = "Stopping Page Load";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public void refreshButton(View view){
        mWebView.reload();
    }

    public void homeButton(View view){
        mWebView.loadUrl(URL);
    }


    @SuppressWarnings("deprecation")
    public void onLowMemory(){
        WebView mWebView = (WebView) findViewById(R.id.webView);

        if(version <= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1 ){
            //Attemts to free memory from the webView
            mWebView.freeMemory();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}