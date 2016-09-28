package com.wlazly.customview.combination;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wlazly.customview.R;

/**
 * Created by Wlazly on 2016/7/24 0024.
 */
public class CustomTitle2 extends RelativeLayout {

    private int backTextColor;
    private int rightTextColor;
    private int titleTextColor;
    private  String backText;
    private  String rightText;
    private  String titleText;
    private  Drawable backBg;
    private  Drawable rightBg;
    private  Drawable titleBg;
    private Button backBtn;
    private Button rightBtn;
    private TextView titleTv;


    public CustomTitle2(Context context) {
        super(context);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public CustomTitle2(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.customTitle2);
        backTextColor = typedArray.getColor(R.styleable.customTitle2_backTextColor, 0);
        backText = typedArray.getString(R.styleable.customTitle2_backText);
        backBg = typedArray.getDrawable(R.styleable.customTitle2_backBg);
        rightText = typedArray.getString(R.styleable.customTitle2_rightText);
        rightTextColor = typedArray.getColor(R.styleable.customTitle2_rightTextColor,0);
        rightBg = typedArray.getDrawable(R.styleable.customTitle2_rightBg);
        titleText = typedArray.getString(R.styleable.customTitle2_titleText);
        titleTextColor = typedArray.getColor(R.styleable.customTitle2_titleTexColor,0);
        titleBg = typedArray.getDrawable(R.styleable.customTitle2_titleBg);
        typedArray.recycle();
        backBtn = new Button(context);
        rightBtn = new Button(context);
        titleTv = new TextView(context);
        backBtn.setText(backText);
        backBtn.setTextColor(backTextColor);
        backBtn.setBackground(backBg);
        rightBtn.setText(rightText);
        rightBtn.setTextColor(rightTextColor);
        rightBtn.setBackground(rightBg);
        titleTv.setTextColor(titleTextColor);
        titleTv.setText(titleText);
        titleTv.setBackground(titleBg);
//        if (!TextUtils.isEmpty(backText)) {
//            backBtn.setText(backText);
//        }else {
//            backBtn.setText("back");
//        }
//        if (backTextColor == 0) {
//            backBtn.setTextColor(getResources().getColor(R.color.colorAccent));
//        }else {
//            backBtn.setTextColor(backTextColor);
//        }
        LayoutParams backParams = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        backBtn.setLayoutParams(backParams);
        rightBtn.setPadding(1,1,1,1);
        addView(backBtn);

        LayoutParams titleParams = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        titleParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        titleTv.setLayoutParams(titleParams);
        addView(titleTv);

        LayoutParams rightParams = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        rightParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        rightBtn.setPadding(1,1,1,1);
        rightBtn.setLayoutParams(rightParams);
        addView(rightBtn);




    }


}
