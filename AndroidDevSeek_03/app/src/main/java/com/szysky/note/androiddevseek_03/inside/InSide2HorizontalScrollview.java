package com.szysky.note.androiddevseek_03.inside;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.szysky.note.androiddevseek_03.cusview.MyHorizontalScrollView;

/**
 * Created by suzeyu on 16/8/9.
 *
 * 利用内部拦截法  解决滑动冲突
 */

public class InSide2HorizontalScrollview extends MyHorizontalScrollView {
    public InSide2HorizontalScrollview(Context context) {
        super(context);
    }

    public InSide2HorizontalScrollview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public InSide2HorizontalScrollview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public InSide2HorizontalScrollview(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int x = (int) ev.getX();
        int y = (int) ev.getY();

        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished()){
                    mScroller.abortAnimation();
                    return true;
                }
                mLastX = x;
                mLastY = y;
                return false;

            default:
               return true;
        }

    }
}
