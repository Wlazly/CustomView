package com.wlazly.customview.inherit;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.wlazly.customview.R;

/**
 * Created by Wlazly on 2016/8/17 0017.
 */
public class CustomListView extends ListView implements AbsListView.OnScrollListener {


    private View headView;
    private int scrollState;
    private int firstVisibleItem = -1;
    private int status;//刷新状态
    public static final int NONE = 0;//正常状态
    public static final int PULL = 1;//下拉状态
    public static final int RELEASE = 2;//提示上拉刷新状态
    public static final int REFRESHING = 3;//正在刷新状态。

    private boolean isRemark = false;//标记是否在刷新
    private float startY;
    private int headHeight;

    public CustomListView(Context context) {
        super(context);
        initView(context);
    }



    public CustomListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public CustomListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);

    }

    private void initView(Context context) {
        headView = LayoutInflater.from(context).inflate(R.layout.header_layout,null);
        measureView(headView);
        headHeight = headView.getMeasuredHeight();
        topPadding(-headHeight);
        addHeaderView(headView);
        setOnScrollListener(this);
    }

    private void measureView(View headView) {
        ViewGroup.LayoutParams layoutParams = headView.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        int headWidthSpec = getChildMeasureSpec(0,0,layoutParams.width);
        int hight = layoutParams.height;
        int headHeightSpec;

        if (hight > 0) {
            headHeightSpec = MeasureSpec.makeMeasureSpec(hight,MeasureSpec.EXACTLY);
        }else {
            headHeightSpec = MeasureSpec.makeMeasureSpec(0,MeasureSpec.UNSPECIFIED);
        }
        headView.measure(headWidthSpec,headHeightSpec);
    }

    private void topPadding(int topPadding) {
        headView.setPadding(headView.getPaddingLeft(),topPadding,headView.getPaddingRight(),headView.getPaddingBottom());
        headView.invalidate();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        this.firstVisibleItem = firstVisibleItem;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        this.scrollState = scrollState;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        float y = ev.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (firstVisibleItem == 0) {
                    isRemark = true;
                    startY = ev.getY();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                onMove(y-startY);
                break;
            case MotionEvent.ACTION_UP:
                if (status == RELEASE) {
                    status = REFRESHING;
                    reflashViewByState();
                    listener.onRefresh();
                }else if (status == PULL) {
                    status = NONE;
                    isRemark = false;
                    reflashViewByState();
                }
                break;

            default:
                break;
        }
        return super.onTouchEvent(ev);
    }


    private void onMove(float moveDistance) {
        if (!isRemark) {
            return;
        }
        int topPadding = (int)moveDistance-headHeight;
            switch (status) {
                case NONE:
                    if (moveDistance > 0) {
                        status = PULL;
                        topPadding(topPadding);
                        reflashViewByState();
                    }
                    break;
                case PULL:
                    topPadding(topPadding);
                    if (moveDistance > headHeight+30 && scrollState == SCROLL_STATE_TOUCH_SCROLL) {
                        status = RELEASE;
                        reflashViewByState();
                    }
                    break;

                case RELEASE:
                    topPadding(topPadding);
                    if (moveDistance < headHeight + 30) {
                        status = PULL;
                        reflashViewByState();
                    }else if (moveDistance <= 0) {
                        status = NONE;
                        isRemark = false;
                        reflashViewByState();
                    }
                    break;

                default:
                    break;
            }

    }

    /**
     * 更新刷新状态
     */
    public void reflashViewByState(){

        TextView tip = (TextView) headView.findViewById(R.id.tip);
        ImageView arrow = (ImageView) headView.findViewById(R.id.arrow);
        ProgressBar progress = (ProgressBar) headView.findViewById(R.id.progress);
        RotateAnimation anim = new RotateAnimation(0, 180,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(500);
        anim.setFillAfter(true);
        RotateAnimation anim1 = new RotateAnimation(180, 0,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        anim1.setDuration(500);
        anim1.setFillAfter(true);

        switch (status) {
            case NONE:
                arrow.clearAnimation();
                topPadding(-headHeight);
                break;

            case PULL:
                arrow.setVisibility(VISIBLE);
                progress.setVisibility(GONE);
                tip.setText("下拉可以刷新！");
                arrow.clearAnimation();
                arrow.setAnimation(anim1);
                break;
            case RELEASE:
                arrow.setVisibility(View.VISIBLE);
                progress.setVisibility(View.GONE);
                tip.setText("松开可以刷新！");
                arrow.clearAnimation();
                arrow.setAnimation(anim);
                break;

            case REFRESHING:
                topPadding(50);
                arrow.setVisibility(View.GONE);
                progress.setVisibility(View.VISIBLE);
                tip.setText("正在刷新...");
                arrow.clearAnimation();
                break;
        }

    }

    public interface RefreshListener{
        void onRefresh();
    }

    RefreshListener listener;

    public void setOnRefreshListener(RefreshListener listener) {
        this.listener = listener;
    }


    public void compleRefresh(){
        status = NONE;
        isRemark  = false;
        reflashViewByState();
    }
}
