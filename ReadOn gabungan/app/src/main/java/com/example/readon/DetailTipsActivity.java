package com.example.readon;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.readon.constant.IntentDataConstant;
import com.example.readon.model.TipsModel;

public class DetailTipsActivity extends AppCompatActivity {

    private TipsModel data;
    private TextView tipsTitle;
    private TextView tipsSubtitle;
    private TextView tipsDescription;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail_tips);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#90EE90"));

        getSupportActionBar().setBackgroundDrawable(colorDrawable);


        initData();
        initViews();

    }

    @Override
    public boolean onNavigateUp() {
        finish();
        return true;
    }

    void initData(){
        data = (TipsModel) getIntent().getSerializableExtra(IntentDataConstant.TIPS_DATA);
    }

    void initViews(){
        tipsTitle = (TextView) findViewById(R.id.tipsTitleTextView);
        tipsSubtitle = (TextView) findViewById(R.id.tipsSubtitleTextView);
        tipsDescription = (TextView) findViewById(R.id.tipsDescriptionTextView);

        tipsTitle.setText(data.getTitle());
        tipsSubtitle.setText(data.getSubtitle());
        tipsDescription.setText(data.getDescription());
    }
}
