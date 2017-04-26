package com.ec.vone.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.ec.vone.R;

/**
 * Created by user on 2017/4/25.
 */

public class MyVoiceControlView extends View {

    public MyVoiceControlView(Context context) {
        this(context, null);
    }

    public MyVoiceControlView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    private int mFirstColor;//默认颜色
    private int mSecondColor;//选中颜色
    private int mCircleWidth;//圆尺寸
    private int mCurrentCount;//当前进度
    private int mDotCount;//点的数量
    private int mSplitSize;//点之间间隙的大小
    private Bitmap mImage;//中间的图片
    private Paint mPaint;
    private Rect mRect;


    public MyVoiceControlView(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MyVoiceControlView, 0, defStyleAttr);

        for (int i = 0; i < typedArray.getIndexCount(); i++) {
            int attr = typedArray.getIndex(i);

            switch (attr) {
                case R.styleable.MyVoiceControlView_firstColor:
                    mFirstColor = typedArray.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.MyVoiceControlView_secondColor:

                    mSecondColor = typedArray.getColor(attr, Color.WHITE);
                    break;
                case R.styleable.MyVoiceControlView_background:
                    mImage = BitmapFactory.decodeResource(getResources(), typedArray.getResourceId(attr, 0));
                    break;
                case R.styleable.MyVoiceControlView_dotCount:
                    mDotCount = typedArray.getInteger(attr, 10);
                    break;
                case R.styleable.MyVoiceControlView_circleWidth:
                    mCircleWidth = typedArray.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_PX, 16, getResources().getDisplayMetrics()
                    ));
                    break;
                case R.styleable.MyVoiceControlView_splitSize:
                    mSplitSize = typedArray.getInteger(attr, 20);
                    break;
            }
        }
        typedArray.recycle();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mRect = new Rect();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setStrokeWidth(mCircleWidth);
        mPaint.setStrokeCap(Paint.Cap.ROUND);//线段的断点形状为round
        mPaint.setStyle(Paint.Style.STROKE);
        int center = getWidth() / 2;
        int radius = center - mCircleWidth / 2;


        /**
         * 根据需要画的个数以及间隙计算每个块块所占的比例*360
         */
        float itemSize = (240 * 1.0f - (mDotCount - 1) * mSplitSize) / mDotCount;

        RectF oval = new RectF(mCircleWidth / 2, mCircleWidth / 2,
                getWidth() - mCircleWidth / 2, getWidth() - mCircleWidth / 2); // 用于定义的圆弧的形状和大小的界限

        mPaint.setColor(mFirstColor); // 设置圆环的颜色
        for (int i = 0; i < mDotCount; i++) {
            canvas.drawArc(oval, i * (itemSize + mSplitSize) + 150, itemSize, false, mPaint); // 根据进度画圆弧
        }

        mPaint.setColor(mSecondColor); // 设置圆环的颜色

        for (int i = 0; i < mCurrentCount; i++) {
            canvas.drawArc(oval, i * (itemSize + mSplitSize) + 150, itemSize, false, mPaint); // 根据进度画圆弧
        }


        int r = center - mCircleWidth;

        mRect.left = (int) ((1 - Math.sqrt(2) / 2) * r + mCircleWidth);
        mRect.top = (int) ((1 - Math.sqrt(2) / 2) * r + mCircleWidth);
        mRect.right = (int) ((1 + Math.sqrt(2) / 2) * r + mCircleWidth);
        mRect.bottom = (int) ((1 + Math.sqrt(2) / 2) * r + mCircleWidth);

        if (mImage.getWidth() < Math.sqrt(2) * r) {//如果图片的尺寸过小，无法与外圆相交，就使用它本来的尺寸
            mRect.left = r + mCircleWidth - mImage.getWidth() / 2;
            mRect.right = mRect.left + mImage.getWidth();
            mRect.top = r + mCircleWidth - mImage.getWidth() / 2;
            mRect.bottom = mRect.top + mImage.getWidth();
        }

        canvas.drawBitmap(mImage, null, mRect, mPaint);


    }

    public void up() {
        if (mCurrentCount < mDotCount)
            mCurrentCount++;
        postInvalidate();
    }

    public void down() {
        if (mCurrentCount >= 1)
            mCurrentCount--;
        postInvalidate();
    }

    private int xLast;
    private int yLast;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float moveLength = getHeight() / (mDotCount * 3 );//用来判断滑动距离是这个距离的多少倍
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xLast = (int) event.getX();
                yLast = (int) event.getY();
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_MOVE:

                //x轴方向move事件
                int xCurrent = (int) event.getX() - xLast;
                int moveCountX = (int) (xCurrent / moveLength);
                if (xCurrent > 0) {//——→滑
                    if (moveCountX > 1) {
                        up();
                        xLast = (int) event.getX();
                    }
                } else if (xCurrent < 0) {//下滑
                    if (moveCountX < -1) {
                        down();
                        xLast = (int) event.getX();
                    }
                }

                //y轴方向move事件
                int yCurrent = (int) (event.getY() - yLast);
                int moveCountY = (int) (yCurrent / moveLength);
                if (yCurrent > 0) {//↓滑
                    if (moveCountY > 1) {
                        down();
                        yLast = (int) event.getY();
                    }
                } else if (yCurrent < -1) {
                    if (yCurrent < -1) {
                        up();
                        yLast = (int) event.getY();
                    }
                }

                break;
        }
        return true;
    }
}
