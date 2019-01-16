package com.android602_4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class DiscoveryDetailsActivity extends AppCompatActivity {

    private ImageView imageViewBack;
    private TextView titleText;
    private ImageView imageView;
    private TextView contentText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discovery_details);


        titleText = (TextView) findViewById(R.id.discovery_list_details_title);
        imageView = (ImageView) findViewById(R.id.discovery_list_details_img);
        contentText = (TextView) findViewById(R.id.discovery_list_details_content);
        imageViewBack = (ImageView) findViewById(R.id.discovery_details_title_back);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String title = bundle.getString("title");
        int imgId = bundle.getInt("image");
        String content = bundle.getString("content");

        titleText.setText(title);
        imageView.setBackgroundResource(imgId);
        contentText.setText(content);

        imageViewBack = (ImageView) findViewById(R.id.discovery_details_title_back);

        imageViewBack.setOnClickListener(mOnClickListener);
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {DiscoveryDetailsActivity.this.setResult(2);
            DiscoveryDetailsActivity.this.finish();
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount()==0){
            DiscoveryDetailsActivity.this.setResult(12);
            DiscoveryDetailsActivity.this.finish();
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
