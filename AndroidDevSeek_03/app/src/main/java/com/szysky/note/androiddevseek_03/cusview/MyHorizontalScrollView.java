package com.szysky.note.androiddevseek_03.cusview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by suzeyu on 16/8/9.
 *
 * 自定义一个ViewGroup的类, 来进行场景模拟
 */

public class MyHorizontalScrollView extends ViewGroup {
    private static final String TAG = "MyHorizontalScrollView";

    private int mChildrenSize, mChildWidth, mChildIndex;

    /**
     * 记录上一次滑动的坐标
     */
    protected int mLastX, mLastY;

    /**
     * 记录在onIntercepteTouchEvent中的最后一次坐标
     */
    protected int mLastXIntercept, mLastYIntercept;

    protected Scroller mScroller;
    protected VelocityTracker mVerlocityTracker;


    public MyHorizontalScrollView(Context context) {
        super(context);
        init();
    }

    public MyHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public MyHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    /**
     * 初始化辅助类对象
     */
    private void init() {
        if (mScroller == null){
            mScroller = new Scroller(getContext());
            mVerlocityTracker = VelocityTracker.obtain();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredWidth = 0;
        int measuredHeight = 0;
        final int childCount = getChildCount();
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int widthSpaceSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthSpaceMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpaceSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightSpaceMode = MeasureSpec.getMode(heightMeasureSpec);

        //设置两个默认值宽高
        int defaultHeight = 100;
        int defaultWidth = 100;


        // 针对AT_MOST模式进行特殊处理
        if (widthSpaceMode == MeasureSpec.AT_MOST
                && heightSpaceMode == MeasureSpec.AT_MOST){

            setMeasuredDimension(defaultWidth, defaultHeight);

        }else if (widthSpaceMode == MeasureSpec.AT_MOST){

            setMeasuredDimension(defaultWidth, heightSpaceSize);

        }else if (heightSpaceMode == MeasureSpec.AT_MOST){

            setMeasuredDimension(widthMeasureSpec, defaultHeight);

        }




        if (childCount == 0){
            setMeasuredDimension(0,0);
        }else if (widthSpaceMode == MeasureSpec.AT_MOST && heightMeasureSpec == MeasureSpec.AT_MOST){
            final View childView = getChildAt(0);
            measuredWidth = childView.getMeasuredWidth() * childCount;
            measuredHeight = childView.getMeasuredHeight() ;
            setMeasuredDimension(measuredWidth, measuredHeight);

        }else if (heightSpaceMode == MeasureSpec.AT_MOST){
            final View childView = getChildAt(0);
            measuredHeight = childView.getMeasuredHeight() ;
            setMeasuredDimension(widthSpaceSize, childView.getMeasuredHeight());

        }else if (widthSpaceMode == MeasureSpec.AT_MOST){
            final View childView = getChildAt(0);
            measuredWidth = childView.getMeasuredWidth() * childCount;
            setMeasuredDimension(measuredWidth, heightSpaceSize);
        }


    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childLeft = 0;
        final int childCount = getChildCount();
        mChildrenSize = childCount;

        for (int i = 0; i < childCount; i++) {
            final  View childView = getChildAt(i);
            if (childView.getVisibility() != View.GONE){
                final int childWidth = childView.getMeasuredWidth();
                mChildWidth = childWidth;
                childView.layout(childLeft, 0, childLeft+childWidth, childView.getMeasuredHeight());
                childLeft += childWidth;
            }
        }
    }

    private void smoothScrollBy(int dx, int dy){
        mScroller.startScroll(getScrollX(), 0, dx, 0,500);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        mVerlocityTracker.recycle();
        super.onDetachedFromWindow();
    }


    /**
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercepted = false;
        int x = (int) ev.getX();
        int y = (int) ev.getY();

        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                intercepted = false;
                if (!mScroller.isFinished()){
                    mScroller.abortAnimation();
                    intercepted = true;
                }
                break;

            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mLastXIntercept;
                int deltaY = y - mLastYIntercept;

                if (Math.abs(deltaX) > Math.abs(deltaY)){
                    intercepted = true;
                }else {
                    intercepted = false;
                }

                break;

            case MotionEvent.ACTION_UP:
                intercepted = false;
                break;

            default:
                break;


        }

        Log.d(TAG, "onInterceptTouchEvent: intercepted="+intercepted);

        mLastX = x;
        mLastY = y;
        mLastXIntercept = x;
        mLastYIntercept = y;

        return intercepted;
    }
     **/

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mVerlocityTracker.addMovement(event);
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished()){
                    mScroller.abortAnimation();
                }
                Log.e(TAG, "onTouchEvent: ~~~~~~~~~~~~~~~~~~~~~~~~" );
                break;
            case MotionEvent.ACTION_MOVE:
                int deltax = x - mLastX;
                int deltay = y - mLastY;
                scrollBy(-deltax,0);

                break;
            case MotionEvent.ACTION_UP:
                int scrollX = getScrollX();
                mVerlocityTracker.computeCurrentVelocity(1000);
                float xVelocity = mVerlocityTracker.getXVelocity();

                if (Math.abs(xVelocity) >= 50){
                    mChildIndex = xVelocity >= 0? mChildIndex -1 : mChildIndex +1;
                }else{
                    mChildIndex = (scrollX + mChildWidth/2) / mChildWidth;
                }

                mChildIndex = Math.max(0, Math.min(mChildIndex, mChildrenSize -1 ));
                int dx = mChildIndex * mChildWidth - scrollX;
                smoothScrollBy(dx, 0);
                mVerlocityTracker.clear();

                break;
            default:
                break;
        }

        mLastX = x;
        mLastY = y;
        return  true;
    }
}
