package com.wlazly.customview.combination;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.wlazly.customview.R;

/**
 * Created by Wlazly on 2016/7/24 0024.
 */
public class CustomTitle1 extends FrameLayout{

    private Button backBtn;
    private Button rightBtn;
    private TextView titleBtn;

    public CustomTitle1(Context context) {
        super(context,null);
    }

    public CustomTitle1(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view=LayoutInflater.from(context).inflate(R.layout.custom_title,this);
        backBtn = (Button)findViewById(R.id.backBtn);
        rightBtn = (Button)findViewById(R.id.rightBtn);
        titleBtn = (TextView)findViewById(R.id.titleBtn);
        //addView(view);
    }




}
