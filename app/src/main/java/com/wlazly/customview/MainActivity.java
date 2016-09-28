package com.wlazly.customview;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.RatingBar;

import com.tencent.bugly.crashreport.CrashReport;
import com.wlazly.customview.inherit.CustomRatingBar;
import com.wlazly.customview.inherit.CustomTextView;

public class MainActivity extends Activity {

    private CustomTextView customTextView;
    private CustomRatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        customTextView = (CustomTextView)findViewById(R.id.customTv);
//        ViewTreeObserver vto = customTextView.getViewTreeObserver();
//        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                customTextView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
//                customTextView.getHeight();
//                customTextView.getWidth();
//                Log.e("TAG:",customTextView.getHeight()+","+customTextView.getWidth());
//            }
//        });
        customTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CrashReport.testJavaCrash();
                Intent intent = new Intent(MainActivity.this,ListViewActivity.class);
                startActivity(intent);
            }
        });

        customTextView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });

        //星星评分
        ratingBar = (CustomRatingBar) findViewById(R.id.customRatingBar);
        ratingBar.setOnRatingBarListener(new CustomRatingBar.RatingBarListener() {
            @Override
            public void onRatingBarListener(int grade) {
                Log.e("TAG","ratingBarGrade= "+grade);
                ratingBar.setFillCount(0);
            }
        });


        Log.e("TAG","androidVersion"+ Build.VERSION.SDK+","+Build.VERSION.SDK_INT+","+Build.VERSION.RELEASE);

    }


}
