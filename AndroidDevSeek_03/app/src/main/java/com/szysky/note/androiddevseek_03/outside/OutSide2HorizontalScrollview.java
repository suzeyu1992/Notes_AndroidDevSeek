package com.szysky.note.androiddevseek_03.outside;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.szysky.note.androiddevseek_03.cusview.MyHorizontalScrollView;

/**
 * Created by suzeyu on 16/8/9.
 *
 * 利用外部拦截法  解决滑动冲突
 */

public class OutSide2HorizontalScrollview extends MyHorizontalScrollView {
    public OutSide2HorizontalScrollview(Context context) {
        super(context);
    }

    public OutSide2HorizontalScrollview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public OutSide2HorizontalScrollview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OutSide2HorizontalScrollview(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = false;
        int x = (int) ev.getX();
        int y = (int) ev.getY();

        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished()){
                    mScroller.abortAnimation();
                    intercept = true;
                }
                break;

            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mLastYIntercept;
                int deltaY = y - mLastYIntercept;
                //根据绝对值判断是否需要拦截
                if (Math.abs(deltaX) < Math.abs(deltaY)){
                    intercept = false;
                }else{
                    intercept = true;
                }
                break;

            case MotionEvent.ACTION_UP:
                intercept = false;
                break;

            default:
                break;
        }
        //赋值给mLast是防止在onTouchEvent第一次move移动时候跳屏
        mLastX = mLastXIntercept = x;
        mLastY = mLastYIntercept = y;
        return intercept;
    }
}
