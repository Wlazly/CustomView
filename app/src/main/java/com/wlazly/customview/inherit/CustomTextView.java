package com.wlazly.customview.inherit;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

import com.wlazly.customview.R;

/**
 * Created by Wlazly on 2016/8/3 0003.
 */
public class CustomTextView extends TextView {

    private Paint mOutPaint;
    private Paint mInPaint;

    public CustomTextView(Context context) {
        super(context);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mOutPaint = new Paint();
        mOutPaint.setStyle(Paint.Style.FILL);
        mOutPaint.setColor(getResources().getColor(R.color.red));

        mInPaint = new Paint();
        mInPaint.setColor(getResources().getColor(android.R.color.white));
        mInPaint.setStyle(Paint.Style.FILL);



    }

    @Override
    protected void onDraw(Canvas canvas) {
        //第一种画边框画法
        Rect outRect = new Rect(0,0,getWidth(),getHeight());//画笔为黄色的，整个填充
        canvas.drawRect(outRect,mOutPaint);
        Rect inRect = new Rect(1,1,getWidth()-1,getHeight()-1);//画笔是白色的，整个填充
        canvas.drawRect(inRect,mInPaint);
        super.onDraw(canvas);
        Log.e("TAG",outRect.toString());
        Log.e("TAG",inRect.toString());

        //第二种边框画法：画四条直线
        canvas.drawLine(0,0,getWidth()-1,0,mOutPaint);
        canvas.drawLine(0,0,0,getHeight()-1,mOutPaint);
        canvas.drawLine(0,getHeight()-1,getWidth()-1,getHeight()-1,mOutPaint);
        canvas.drawLine(getWidth()-1,0,getWidth()-1,getHeight()-1,mOutPaint);
        super.onDraw(canvas);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e("TAG","getRawX="+event.getRawX()+",getRawY="+event.getRawY()
                        +"getX="+event.getX()+"getY="+event.getY());
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onTouchEvent(event);
    }

    
}
