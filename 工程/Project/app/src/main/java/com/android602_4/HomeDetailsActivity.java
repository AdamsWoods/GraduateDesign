package com.android602_4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class HomeDetailsActivity extends AppCompatActivity {

    private TextView titleText;
    private ImageView imageView,imageViewBack;
    private TextView contentText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_details);

        titleText = (TextView) findViewById(R.id.home_list_details_title);
        imageView = (ImageView) findViewById(R.id.home_list_details_img);
        contentText = (TextView) findViewById(R.id.home_list_details_content);
        imageViewBack = (ImageView) findViewById(R.id.home_details_title_back);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String title = bundle.getString("title");
        int imgId = bundle.getInt("image");
        String content = bundle.getString("content");

        titleText.setText(title);
        imageView.setBackgroundResource(imgId);
        contentText.setText(content);

        imageViewBack.setOnClickListener(mOnClickListener);
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(HomeDetailsActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
