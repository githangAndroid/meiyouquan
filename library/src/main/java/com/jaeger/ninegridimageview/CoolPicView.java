package com.jaeger.ninegridimageview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;


public class CoolPicView extends ImageView {

    private Matrix mBaseMatrix = new Matrix();
    private Matrix mAnimaMatrix = new Matrix();
    private Matrix mSynthesisMatrix = new Matrix();

    private ImageView.ScaleType mScaleType;

    private boolean imgLargeWidth;
    private boolean imgLargeHeight;
    private boolean isInit;
    private boolean hasDrawable;


    private float mScale = 1.0f;
    private int mTranslateX;
    private int mTranslateY;

    private float mHalfBaseRectWidth;
    private float mHalfBaseRectHeight;

    private RectF mWidgetRect = new RectF();
    private RectF mBaseRect = new RectF();
    private RectF mImgRect = new RectF();
    private PointF mScreenCenter = new PointF();


    public CoolPicView(Context context) {
        super(context);
    }

    public CoolPicView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CoolPicView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



    @Override
    public void setScaleType(ScaleType scaleType) {
        if (scaleType != mScaleType) {
            mScaleType = scaleType;
        }

        if (isInit) {
            initBase();
        }
    }

    private void initFitCenter() {
        if (mImgRect.width() < mWidgetRect.width()) {
            mScale = mWidgetRect.width() / mImgRect.width();

            mAnimaMatrix.postScale(mScale, mScale, mScreenCenter.x, mScreenCenter.y);

            executeTranslate();
            resetBase();
        }
    }

    private void initFitStart() {
        initFitCenter();

        float ty = -mImgRect.top;
        mAnimaMatrix.postTranslate(0, ty);
        executeTranslate();
        resetBase();
        mTranslateY += ty;
    }

    private void resetBase() {
        Drawable img = getDrawable();
        int imgw = getDrawableWidth(img);
        int imgh = getDrawableHeight(img);
        mBaseRect.set(0, 0, imgw, imgh);
        mBaseMatrix.set(mSynthesisMatrix);
        mBaseMatrix.mapRect(mBaseRect);
        mHalfBaseRectWidth = mBaseRect.width() / 2;
        mHalfBaseRectHeight = mBaseRect.height() / 2;
        mScale = 1;
        mTranslateX = 0;
        mTranslateY = 0;
        mAnimaMatrix.reset();
    }

    private void executeTranslate() {
        mSynthesisMatrix.set(mBaseMatrix);
        mSynthesisMatrix.postConcat(mAnimaMatrix);
        setImageMatrix(mSynthesisMatrix);
        mAnimaMatrix.mapRect(mImgRect, mBaseRect);
        imgLargeWidth = mImgRect.width() > mWidgetRect.width();
        imgLargeHeight = mImgRect.height() > mWidgetRect.height();
    }

    @Override
    public void setImageResource(int resId) {
        Drawable drawable = null;
        try {
            drawable = getResources().getDrawable(resId);
        } catch (Exception ignored) {
        }

        setImageDrawable(drawable);
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);

        if (drawable == null) {
            hasDrawable = false;
            return;
        }

        if (!hasSize(drawable))
            return;

        if (!hasDrawable) {
            hasDrawable = true;
        }

        initBase();
    }

    private boolean hasSize(Drawable d) {
        if ((d.getIntrinsicHeight() <= 0 || d.getIntrinsicWidth() <= 0)
                && (d.getMinimumWidth() <= 0 || d.getMinimumHeight() <= 0)
                && (d.getBounds().width() <= 0 || d.getBounds().height() <= 0)) {
            return false;
        }
        return true;
    }

    private void initBase() {
        if (!hasDrawable) return;
        mBaseMatrix.reset();
        mAnimaMatrix.reset();

        Drawable img = getDrawable();

        int w = getWidth();
        int h = getHeight();
        int imgw = getDrawableWidth(img);
        int imgh = getDrawableHeight(img);

        mBaseRect.set(0, 0, imgw, imgh);

        // 以图片中心点居中位移
        int tx = (w - imgw) / 2;
        int ty = (h - imgh) / 2;

        float sx = 1;
        float sy = 1;

        // 缩放，默认不超过屏幕大小
        if (imgw > w) {
            sx = (float) w / imgw;
        }

        if (imgh > h) {
            sy = (float) h / imgh;
        }

        float scale = sx < sy ? sx : sy;

        mBaseMatrix.reset();
        mBaseMatrix.postTranslate(tx, ty);
        mBaseMatrix.postScale(scale, scale, mScreenCenter.x, mScreenCenter.y);
        mBaseMatrix.mapRect(mBaseRect);

        mHalfBaseRectWidth = mBaseRect.width() / 2;
        mHalfBaseRectHeight = mBaseRect.height() / 2;


        executeTranslate();

        switch (mScaleType) {
            case FIT_CENTER:
                initFitCenter();
                break;
            case FIT_START:
                initFitStart();
                break;
            case CENTER_CROP:


            default:
                super.setScaleType(mScaleType);
        }

        isInit = true;
    }

    private static int getDrawableWidth(Drawable d) {
        int width = d.getIntrinsicWidth();
        if (width <= 0) width = d.getMinimumWidth();
        if (width <= 0) width = d.getBounds().width();
        return width;
    }

    private static int getDrawableHeight(Drawable d) {
        int height = d.getIntrinsicHeight();
        if (height <= 0) height = d.getMinimumHeight();
        if (height <= 0) height = d.getBounds().height();
        return height;
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Drawable drawable = getDrawable();
                if (drawable != null) {
                    drawable.mutate().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                Drawable drawableUp = getDrawable();
                if (drawableUp != null) {
                    drawableUp.mutate().clearColorFilter();
                }
                break;
        }

        return super.onTouchEvent(event);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        setImageDrawable(null);
    }
}