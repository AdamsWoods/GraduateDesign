package com.example;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;


public class HomeDetailsActivity extends AppCompatActivity {

    private TextView titleText;
//    private ImageView imageViewBack;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_details);

        titleText = findViewById(R.id.home_details_title);
//        imageViewBack = findViewById(R.id.home_details_title_back);
        webView = findViewById(R.id.wv_detail);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
//        String title = bundle.getString("title");
        String contentUrl = bundle.getString("contentUrl");
//        titleText.setText(title);
        webView.loadUrl(contentUrl);
//        imageViewBack.setOnClickListener(mOnClickListener);
    }
//
//    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            Intent intent = new Intent(HomeDetailsActivity.this,MainActivity.class);
//            startActivity(intent);
//            finish();
//        }
//    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
