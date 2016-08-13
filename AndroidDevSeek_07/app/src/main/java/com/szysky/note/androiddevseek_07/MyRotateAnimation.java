package com.szysky.note.androiddevseek_07;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by suzeyu on 16/8/13.
 * 搬照书上代码, 查看效果
 */

public class MyRotateAnimation extends Animation {

    private final float toDegrees;
    private final float mCenterX;
    private final float mCenterY;
    private final float mDepthZ;
    private final boolean mReverse;
    private final float mFromDegrees;
    private Camera mCamera;

    public MyRotateAnimation(float fromDegrees, float toDegrees,
                             float centerX, float centerY,
                             float depthZ, boolean reverse ){
        this.mFromDegrees = fromDegrees;
        this.toDegrees = toDegrees;
        this.mCenterX = centerX;
        this.mCenterY = centerY;
        this.mDepthZ = depthZ;
        this.mReverse = reverse;
    }

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        mCamera = new Camera();
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        float fromDegrees = this.mFromDegrees;
        float degrees = mFromDegrees + ((toDegrees - mFromDegrees) * interpolatedTime);

        final float centerX = mCenterX;
        final float centerY = mCenterY;

        final Camera camera = mCamera;
        final Matrix matrix = t.getMatrix();

        camera.save();

        if (mReverse){
            camera.translate(0.0f, 0.0f, mDepthZ * interpolatedTime);
        }else{
            camera.translate(0.0f, 0.0f, mDepthZ * (1 - interpolatedTime));
        }

        camera.rotateY(degrees);
        camera.getMatrix(matrix);
        camera.restore();

        matrix.preTranslate(-centerX, -centerY);
        matrix.postTranslate(centerX, centerY);


    }
}
