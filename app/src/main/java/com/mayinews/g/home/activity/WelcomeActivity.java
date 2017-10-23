package com.mayinews.g.home.activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mayinews.g.MainActivity;
import com.mayinews.g.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;



public class WelcomeActivity extends AppCompatActivity {


    private static final int STARTMAINACTIVITY = 0;
    @BindView(R.id.imageview)
    ImageView imageview;
    @BindView(R.id.start_image)
    ImageView startImage;
    @BindView(R.id.start_tv)
    TextView startTv;
    @BindView(R.id.start_ll)
    LinearLayout startLl;
    private Handler handler = new Handler();


    private int time = 3;
    private boolean isOnClick = false;//标记是否点击过了
    private boolean isStartMain=false;//标记是否启动了MainActivity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);

        startImage.setAlpha(0.6f);
        //请求推荐模特图片
//        Glide.with(this).load("http://fns-photo-public.oss-cn-hangzhou.aliyuncs.com/1505460750868429d25.jpg").into(imageview);
        //imageView进行缩放

        ObjectAnimator scaleX = ObjectAnimator.ofFloat(imageview, "scaleX", 1.0f, 1.5f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(imageview, "scaleY", 1.0f, 1.5f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(6000);
        animatorSet.playTogether(scaleX, scaleY);
        animatorSet.start();
        setTimeTest();


    }

    private void setTimeTest() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                time--;
                if (time == 0) {
                    startTv.setText("");
                    //将中间小圆圈移动到上方
                    beginTranX();
                } else {
                    startTv.setText("点击图标跳过 " + time + "秒");
                    setTimeTest();
                }


            }
        }, 1000);
    }

    private void beginTranX() {
        float translationY = startLl.getTranslationY();
        WindowManager wm1 = this.getWindowManager();

        int height1 = wm1.getDefaultDisplay().getHeight();

        ObjectAnimator trans = ObjectAnimator.ofFloat(startLl, "translationY", translationY, -height1/2);
        trans.setDuration(1000);
        trans.start();
        trans.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                       startImage.setEnabled(false);
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                //执行startImage的旋转动画

                ObjectAnimator rotation = ObjectAnimator.ofFloat(startImage, "rotation", 0, 360);
                rotation.setDuration(1000);
                rotation.start();
                rotation.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {

                        if(!isStartMain){
                            //启动MainActivity
                            startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                            finish();
                            isStartMain=true;
                        }

                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });


            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);

    }

    @OnClick(R.id.start_image)
    public void OnClick(View view) {
        if (view.getId() == R.id.start_image) {
            isOnClick = true;
            if (isOnClick) {
                //直接执行唯一动画

                beginTranX();
                startTv.setText("");
                handler.removeCallbacksAndMessages(null);

            }
        }
    }

}
