package com.wlazly.customview.inherit;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;

import com.wlazly.customview.R;

/**
 * Created by Wlazly on 2016/8/17 0017.
 */
public class PasswordInputView extends EditText {

    private static final int defaultContMargin = 5;//内容间距
    private static final int defaultSplitLineWidth = 3;//缺省的分割线宽度

    private int borderColor = 0xffcccccc;//边框颜色
    private float borderWidth = 5;//边框宽度
    private float borderRadius = 3;//边框圆角半径

    private int passwordLength = 6;//密码长度
    private float passwrodWidth = 8;//密码宽度
    private int passwordColr = 0xffcccccc;//密码颜色
    private float passwrodRadius = 3;//密码圆角半径

    private int textLength;//输入的字符个数
    private Paint borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);//边框画笔
    private Paint passwordPaint  = new Paint(Paint.ANTI_ALIAS_FLAG);//密码画笔

    public PasswordInputView(Context context) {
        super(context,null);
    }

    public PasswordInputView(Context context, AttributeSet attrs) {
        super(context, attrs);


        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        //borderColor =(int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,borderColor,displayMetrics);
        borderRadius = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,borderRadius,displayMetrics);
        borderWidth = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,borderWidth,displayMetrics);
        //passwordColr = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,passwordColr,displayMetrics);
        passwrodRadius = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,passwrodRadius,displayMetrics);
        passwrodWidth = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,passwrodWidth,displayMetrics);

        TypedArray typedArray = getResources().obtainAttributes(attrs, R.styleable.passwordView);
        borderColor = typedArray.getColor(R.styleable.passwordView_borderColor,borderColor);
        borderRadius = typedArray.getDimension(R.styleable.passwordView_borderRadius,borderRadius);
        borderWidth = typedArray.getDimension(R.styleable.passwordView_borderWidth,borderWidth);
        passwordLength = typedArray.getInt(R.styleable.passwordView_passwordLength,6);
        passwordColr = typedArray.getColor(R.styleable.passwordView_passwordColor,passwordColr);
        passwrodRadius = typedArray.getDimension(R.styleable.passwordView_passwordRadius,passwrodRadius);
        passwrodWidth = typedArray.getDimension(R.styleable.passwordView_passwordRadius,passwrodWidth);
        typedArray.recycle();

        borderPaint.setStyle(Paint.Style.FILL);
        borderPaint.setColor(borderColor);
        borderPaint.setStrokeWidth(borderWidth);
        passwordPaint.setStyle(Paint.Style.FILL);
        passwordPaint.setColor(passwordColr);
        passwordPaint.setStrokeWidth(passwrodWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();

        //外边框
        RectF outRect = new RectF(0,0,width,height);
        borderPaint.setColor(0xffcccccc);
        canvas.drawRoundRect(outRect,borderRadius,borderRadius,borderPaint);

        RectF rectIn = new RectF(outRect.left + defaultContMargin, outRect.top + defaultContMargin,
                outRect.right - defaultContMargin, outRect.bottom - defaultContMargin);
        borderPaint.setColor(Color.WHITE);
        canvas.drawRoundRect(rectIn, borderRadius, borderRadius, borderPaint);


        //分割线
        borderPaint.setColor(0xffcccccc);
        for (int i=1;i<=passwordLength;i++) {
            borderPaint.setStrokeWidth(defaultSplitLineWidth);
            canvas.drawLine(width*i/passwordLength,0,width*i/passwordLength,height-1,borderPaint);
        }

        //密码
        float cy = height/2;
        float half = width/passwordLength/2;
        for (int i=1;i<=textLength;i++) {
            float cx = width*i/passwordLength-half;
            canvas.drawCircle(cx,cy,passwrodWidth,passwordPaint);
        }
    }


    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        this.textLength = text.toString().length();
        invalidate();
    }

}
