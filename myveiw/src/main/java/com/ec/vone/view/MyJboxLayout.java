package com.ec.vone.view;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class MyJboxLayout extends FrameLayout {

    private MyJboxView mMobike;

    public MyJboxLayout(@NonNull Context context) {
        this(context,null);
    }

    public MyJboxLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
        mMobike = new MyJboxView(this);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mMobike.onSizeChanged(w,h);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mMobike.onLayout(changed);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mMobike.onDraw(canvas);
    }

    public MyJboxView getmMobike(){
        return this.mMobike;
    }
}