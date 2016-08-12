package com.szysky.note.androiddevseek_03.inside;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * Created by suzeyu on 16/8/9.
 * 对于内部拦截法  子View也需要进行复写操作
 */

public class InSideListView extends ListView {
    public InSideListView(Context context) {
        super(context);
    }

    public InSideListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public InSideListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public InSideListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    public InSide2HorizontalScrollview mInSide2HorizontalScrollview;

    private int mLastX, mLastY;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int x = (int) ev.getX();
        int y = (int) ev.getY();

        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                //相当于进行初始化每次发生一个事件序列时, 都对不容器进行重置不允许拦截
                mInSide2HorizontalScrollview.requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mLastX;
                int deltaY = y - mLastY;

                if (Math.abs(deltaX) > Math.abs(deltaY)){
                    //当水平距离大的时候 允许父容器拦截
                    mInSide2HorizontalScrollview.requestDisallowInterceptTouchEvent(false);
                }

                break;

            default:
                break;

        }
        mLastX = x;
        mLastY = y;
        return super.dispatchTouchEvent(ev);
    }
}
