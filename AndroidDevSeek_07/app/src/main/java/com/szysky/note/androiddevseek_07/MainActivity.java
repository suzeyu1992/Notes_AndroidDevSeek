package com.szysky.note.androiddevseek_07;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import java.util.Objects;

public class MainActivity extends Activity implements View.OnClickListener {

    private View iv_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iv_main = findViewById(R.id.iv_main);
        View activity_main = findViewById(R.id.activity_main);
        iv_main.setOnClickListener(this);
        findViewById(R.id.btn_rotate3d).setOnClickListener(this);
        findViewById(R.id.btn_start_act).setOnClickListener(this);

        //让背景自动变色
        bgDym(activity_main);

        //给图片添加属性动画 乱七八糟的动画
        //properStartAnim(iv_main);

        Animator set = AnimatorInflater.loadAnimator(this, R.animator.property_anim);
        set.setTarget(iv_main);
        set.start();
    }

    private void properStartAnim(Object object) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(object, "rotationX", 0,360),
                ObjectAnimator.ofFloat(object, "rotationY", 0,360),
                ObjectAnimator.ofFloat(object, "rotation", 0,360),
                ObjectAnimator.ofFloat(object, "translationX", 0,200),
                ObjectAnimator.ofFloat(object, "translationY", 0,200),
                ObjectAnimator.ofFloat(object, "scaleX", 1,1.5f),
                ObjectAnimator.ofFloat(object, "scaleY", 1,1.5f),
                ObjectAnimator.ofFloat(object, "alpha", 1, 0.25f, 1)
        );

        animatorSet.setDuration(5*1000).start();
    }

    private void bgDym(View activity_main) {
        ObjectAnimator colorAnim = ObjectAnimator.ofInt(activity_main, "backgroundColor", 0xffffa000, 0xffffa0ff);
        colorAnim.setDuration(5000);
        colorAnim.setEvaluator(new ArgbEvaluator());
        colorAnim.setRepeatCount(ValueAnimator.INFINITE);
        colorAnim.setRepeatMode(ValueAnimator.REVERSE);
        colorAnim.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_rotate3d:
                final MyRotateAnimation myRotateAnimation = new MyRotateAnimation(0f, 360f, 0f, 0f, 0f, true);
                myRotateAnimation.setDuration(5000);
                iv_main.startAnimation(myRotateAnimation);
                break;

            case R.id.btn_start_act:
                startActivity(new Intent(this, TestOpenActAnimationActivity.class));
                overridePendingTransition(R.anim.enter_anim, R.anim.exit_anim);
                break;
        }
    }
}
