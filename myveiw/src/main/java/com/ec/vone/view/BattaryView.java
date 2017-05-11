package com.ec.vone.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.ec.vone.R;

/**
 * Created by user on 2017/5/11.
 */

public class BattaryView extends View {

    private final Paint mPaint;
    private int battaryPercent;
    private int color;
    private String text;
    private Bitmap img;

    public BattaryView(Context context) {
        this(context, null);
    }

    public BattaryView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BattaryView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
        text = battaryPercent + "%";
        mPaint = new Paint();
        mPaint.setColor(color);
        mPaint.setAntiAlias(true);
        img = BitmapFactory.decodeResource(getResources(), R.mipmap.battery);
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
                width = (int) (mPaint.measureText(text) + getPaddingLeft() + getPaddingRight() + img.getWidth());//先确定mPaint是否已经设置textsize
                break;
        }

        specMode = MeasureSpec.getMode(heightMeasureSpec);
        specSize = MeasureSpec.getSize(heightMeasureSpec);
        switch (specMode) {
            case MeasureSpec.EXACTLY:
                height = specSize + getPaddingTop() + getPaddingBottom();
                break;
            case MeasureSpec.AT_MOST:
//                height = specSize + getPaddingTop() + getPaddingBottom();
                height = img.getHeight() + getPaddingTop() + getPaddingBottom();
//                height = (int) (Math.abs(mPaint.getFontMetrics().bottom - mPaint.getFontMetrics().top) + getPaddingTop() + getPaddingBottom());
                break;
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setStyle(Paint.Style.FILL);
        int imgH = img.getHeight();
        int imgW = img.getWidth();
        RectF rectF = new RectF((float) getPaddingLeft(),
                0,
                (float) getPaddingLeft() + imgW,
                0);
        float spaceTop = (getHeight() - imgH) / 2 + getPaddingTop();
        rectF.top = spaceTop + (1.0f - battaryPercent * 1.0f / 100) * imgH;
        rectF.bottom = getHeight() - spaceTop;

        canvas.drawRect(rectF, mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
        canvas.drawBitmap(img, rectF.left, spaceTop, mPaint);


    }
}
