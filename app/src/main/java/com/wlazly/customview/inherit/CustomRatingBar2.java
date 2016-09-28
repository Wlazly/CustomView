package com.wlazly.customview.inherit;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.wlazly.customview.R;

/**
 * Created by Wlazly on 2016/8/20 0020.
 */
public class CustomRatingBar2 extends View{

    private int betweenStar = 5;//星星之间的距离
    private Bitmap emptyBitmap;
    private Bitmap fillBitmap;
    private int starNum;
    private float fillNum;
    private float stepNum;
    private Drawable emptyDrawable;
    private Drawable fillDrawable;
    private boolean isClickable;
    private Bitmap partBitmap;


    public CustomRatingBar2(Context context) {
        super(context);
        init(context,null);
    }

    public CustomRatingBar2(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public CustomRatingBar2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        betweenStar = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,betweenStar,displayMetrics);

        TypedArray typedArray = getResources().obtainAttributes(attrs, R.styleable.CustomRatingBar2);
        starNum = typedArray.getInt(R.styleable.CustomRatingBar2_startCount,5);
        fillNum = typedArray.getFloat(R.styleable.CustomRatingBar2_fillCount,5);
        stepNum = typedArray.getFloat(R.styleable.CustomRatingBar2_stepNum,(float) 0.1);
        emptyDrawable = typedArray.getDrawable(R.styleable.CustomRatingBar2_emptyBitmap);
        fillDrawable = typedArray.getDrawable(R.styleable.CustomRatingBar2_fillBitmap);
        typedArray.recycle();

        //drawable 转mipmap
        if (emptyDrawable == null) {
            emptyBitmap =  BitmapFactory.decodeResource(getResources(),R.mipmap.favourite_nor);
        }else {
            emptyBitmap = drawableToBitmap(emptyDrawable);
        }

        if (fillDrawable == null) {
            fillBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.favourite_pressed);
        }else {
            fillBitmap = drawableToBitmap(fillDrawable);
        }

    }

    private Bitmap drawableToBitmap(Drawable drawable) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap.Config config =
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                        : Bitmap.Config.RGB_565;
        Bitmap bitmap = Bitmap.createBitmap(width,height,config);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0,0,width,height);
        drawable.draw(canvas);
        return bitmap;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int width = starNum*emptyBitmap.getWidth() + betweenStar*(starNum-1);
        int height = emptyBitmap.getHeight();
        setMeasuredDimension(makeMeasure(width,widthMeasureSpec),makeMeasure(height,heightMeasureSpec));
    }


    private int makeMeasure(int size,int measureSpec) {
        int result = size;
        int specSize = MeasureSpec.getSize(measureSpec);
        int specMode = MeasureSpec.getMode(measureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        }else {
            result = Math.min(result,specSize);
        }
        return result;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //2.5画星星，画2个全星星，还有1个半星星，还有2个空星星
        int fillInt = (int)Math.floor(fillNum);
        float fillPart = fillNum - fillInt;
        fillPart = stepNum*(Math.round(fillPart/stepNum));
        for (int i = 0;i<starNum;i++) {
            int width = betweenStar*i+fillBitmap.getWidth()*i;
            if (i<fillInt) {
                canvas.drawBitmap(fillBitmap,width,0,null);
            }else if (fillPart>0) {
                partBitmap = getPartBitmap(((int)(fillBitmap.getWidth()*fillPart)),fillBitmap.getHeight());
                canvas.drawBitmap(partBitmap,width,0,null);
            }else {
                canvas.drawBitmap(emptyBitmap,width,0,null);
            }
        }
    }

    public Bitmap getPartBitmap(int endX,int endY) {
        Matrix matrix = new Matrix();
        matrix.setScale(1f, 1f);
        Bitmap bitmap = Bitmap.createBitmap(fillBitmap.getWidth(),fillBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Bitmap src = Bitmap.createBitmap(fillBitmap,0,0,endX,endY,matrix,true);
        canvas.drawBitmap(src,0,0,null);
        Paint paint = new Paint();
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_ATOP));
        canvas.drawBitmap(emptyBitmap,0,0,paint);

        return bitmap;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        int x = (int)event.getX();
        int y = (int)event.getY();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                getRating(x);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                getRating(x);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:

                break;
        }
        return super.onTouchEvent(event);
    }

    private void getRating(int x) {
        int width = emptyBitmap.getWidth() + betweenStar;
        int num = x/width;
        if (num*width+emptyBitmap.getWidth() > x) {
            fillNum = num+1;
        }

    }
}
