package com.ec.vone.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

import com.ec.vone.R;

/**
 * Created by user on 2017/5/11.
 */

public class BattaryView extends View {

    private final Paint mPaint;
    private int battaryPercent;//电量百分比
    private int color;//前景色
    private float round;//圆角大小，这里写死，省去使用时计算

    public void setBattaryPercent(int battaryPercent) {
        this.battaryPercent = battaryPercent;
        postInvalidate();
    }

    public BattaryView(Context context) {
        this(context, null);
    }

    public BattaryView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BattaryView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.BattaryView, 0, defStyleAttr);
        for (int i = 0; i < typedArray.length(); i++) {
            int attr = typedArray.getIndex(i);
            switch (attr) {
                case R.styleable.BattaryView_battaryPercnet:
                    battaryPercent = typedArray.getInteger(attr, 0);
                    break;
                case R.styleable.BattaryView_color:
                    color = typedArray.getColor(attr, Color.BLACK);
                    break;
            }
        }
        mPaint = new Paint();
        mPaint.setColor(color);
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);

        int width = 0, height = 0;
        switch (specMode) {
            case MeasureSpec.EXACTLY:
                width = specSize + getPaddingRight() + getPaddingLeft();
                break;
            case MeasureSpec.AT_MOST:
                break;
        }
        specMode = MeasureSpec.getMode(heightMeasureSpec);
        specSize = MeasureSpec.getSize(heightMeasureSpec);
        switch (specMode) {
            case MeasureSpec.EXACTLY:
                height = specSize + getPaddingTop() + getPaddingBottom();
                break;
            case MeasureSpec.AT_MOST:
                break;
        }
        setMeasuredDimension(width, height);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        round = getHeight() / 40f;//把圆角写死，省去使用时计算
        mPaint.setStyle(Paint.Style.FILL);//填充模式
        //电池区域的高，宽
        int imgH = getHeight() - getPaddingTop() - getPaddingBottom();
        int imgW = (getWidth() < getHeight() / 2) ? (getWidth() - getPaddingLeft() - getPaddingRight()) : getHeight() / 2;
        //根据电量百分比得到的矩形,top和bottom先占位，等一下再重新赋值，也可以new RectF()里面空参，四个属性挨个设置
        RectF rectF = new RectF(
                (float) getPaddingLeft(),
                0,
                (float) getPaddingLeft() + imgW,
                0);
        float spaceTop = (getHeight() - imgH) / 2 + getPaddingTop();
        rectF.top = spaceTop + (1.0f - battaryPercent * 1.0f / 100) * imgH;
        rectF.bottom = getHeight() - spaceTop;
        //上面这三行其实有电多余，由于刚开始是想设置at most时的状况的，后来发现太麻烦，急着用就先这样了。

        int layerId = canvas.saveLayer(0, 0, canvas.getWidth(), canvas.getHeight(), null, Canvas.ALL_SAVE_FLAG);
        int h1 = imgH / 8;//电池凸点的高度
        int w1 = imgW / 3;//电池凸点的宽度
        //绘制背景
        mPaint.setColor(Color.argb(77, 0, 0, 0));
        canvas.drawRoundRect(rectF.left,h1 + getPaddingTop(),rectF.right,getHeight() - getPaddingBottom(),round,round,mPaint);
        canvas.drawRect(getPaddingLeft() + w1,getPaddingTop(),rectF.right - w1,h1 + getPaddingTop(),mPaint);
        //绘制有电部分
        canvas.save();
        canvas.clipRect(rectF);
        mPaint.setColor(color);
        canvas.drawRoundRect(rectF.left, getPaddingTop() + h1, rectF.right, rectF.bottom, round, round, mPaint);
        canvas.drawRect(rectF.left + w1, rectF.top, rectF.right - w1, getPaddingTop() + h1 + h1 / 10, mPaint);
        canvas.restore();
        canvas.restoreToCount(layerId);

    }

}
