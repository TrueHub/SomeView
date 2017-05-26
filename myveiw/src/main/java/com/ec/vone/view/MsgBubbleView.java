package com.ec.vone.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.PointFEvaluator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.ec.vone.R;
import com.ec.vone.utils.CircleUtils;

import static com.ec.vone.view.MsgBubbleView.BubbleState.DEFAULT;
import static com.ec.vone.view.MsgBubbleView.BubbleState.DISMISS;
import static com.ec.vone.view.MsgBubbleView.BubbleState.DRAG;
import static com.ec.vone.view.MsgBubbleView.BubbleState.MOVE;

/**
 * Created by user on 2017/5/23.
 */

public class MsgBubbleView extends View {
    private Paint mTextPaint;
    private Path mBezierPath;
    /**
     * 气泡颜色
     */
    private int mColor;
    /**
     * 中心文字颜色
     */
    private int mTextColor;
    /**
     * 中心文字内容
     */
    private String mText;

    /**
     * 原始气泡大小
     */
    private float mBubbleRadius;
    /**
     * 拖拽时跟随手指的气泡大小
     */
    private float mDragBubbleRadius;

    /**
     * 原始气泡的x、y坐标
     */
    private float mBCx, mBCy;
    /**
     * 拖拽气泡的x、y坐标
     */
    private float mDBCx, mDBCy;
    /**
     * 两圆圆心的距离
     */
    private float defLength;
    /**
     * 两圆圆心距离阀值，超过此值原始气泡消失
     */
    private float defMaxLength;

    /*以下是贝塞尔曲线的相关点*/
    /**
     * 原始气泡圆的起点x y
     */
    private float mStartX, mStartY;
    /**
     * 原始气泡圆的终点 x y
     */
    private float mEndX, mEndY;
    /**
     * 拖拽中的圆的起点 x y
     */
    private float mDStartX, mDStartY;
    /**
     * 拖拽中的圆的终点
     */
    private float mDEndX, mDEndY;
    /**
     * 控制点的x y坐标
     */
    private float mCtrlX, mCtrlY;

    /**
     * 文字的尺寸
     */
    private Rect mRect;

    /**
     * 气泡的状态
     */
    enum BubbleState {
        /**
         * 默认，无法拖拽
         */
        DEFAULT,

        /**
         * 拖拽
         */
        DRAG,

        /**
         * 移动
         */
        MOVE,

        /**
         * 消失
         */
        DISMISS;
    }

    private BubbleState mState;

    private Paint mPaint;

    public MsgBubbleView(Context context) {
        this(context, null);
    }

    public MsgBubbleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public MsgBubbleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MsgBubbleView, 0, defStyleAttr);

        for (int i = 0; i < typedArray.length(); i++) {
            int attr = typedArray.getIndex(i);

            switch (attr) {
                case R.styleable.MsgBubbleView_centerText:
                    mText = typedArray.getString(attr);
                    break;
                case R.styleable.MsgBubbleView_textColor:
                    mTextColor = typedArray.getColor(attr, Color.WHITE);
                    break;
                case R.styleable.MsgBubbleView_color:
                    mColor = typedArray.getColor(attr, Color.RED);
                    break;
                case R.styleable.MsgBubbleView_bubbleRadius:
                    mBubbleRadius = typedArray.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics()));
                    break;
            }
        }

        mDragBubbleRadius = mBubbleRadius;
        defMaxLength = mBubbleRadius * 8;

        typedArray.recycle();

        mState = DEFAULT;

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(mColor);
        mPaint.setTextSize(2);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextSize(16);

        mBezierPath = new Path();
        mRect = new Rect();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measuredDimension(widthMeasureSpec), measuredDimension(heightMeasureSpec));
    }

    private int measuredDimension(int measureSpec) {
        int result = 0;

        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);

        switch (mode) {
            case MeasureSpec.EXACTLY:
                result = size;
                break;
            case MeasureSpec.AT_MOST:
                result = Math.max((int) (mBubbleRadius * 2), size);
                break;
            case MeasureSpec.UNSPECIFIED:
                result = (int) (mBubbleRadius * 2);
                break;
        }
        return result;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        initData(w, h);
    }

    private void initData(int w, int h) {
        mBCx = mDBCx = w / 2;
        mBCy = mDBCy = h / 2;
        mState = DEFAULT;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画拖拽中的气泡
        if (mState != DISMISS) {
            canvas.drawCircle(mDBCx, mDBCy, mDragBubbleRadius, mPaint);
        }
        if (mState == DRAG && defLength < defMaxLength - defMaxLength / 4) {
            //画小圆
            canvas.drawCircle(mBCx, mBCy, mBubbleRadius, mPaint);

            //计算bezier曲线的控制点坐标
            mCtrlX = (mBCx + mDBCx) / 2;
            mCtrlY = (mBCy + mDBCy) / 2;

            /*float sin = (mDBCy - mBCy) / defLength;
            float cos = (mDBCx - mBCx) / defLength;

            mStartX = mBCx - mBubbleRadius * sin;
            mStartY = mBCy + mBubbleRadius * cos;

            mDEndX = mDBCx - mDragBubbleRadius * sin;
            mDEndY = mDBCy + mDragBubbleRadius * cos;

            mDStartX = mDBCx + mDragBubbleRadius * sin;
            mDStartY = mDBCy - mDragBubbleRadius * cos;

            mEndX = mBCx + mBubbleRadius * sin;
            mEndY = mBCy - mBubbleRadius * cos;*/


            PointF[] bubblePoints = CircleUtils.getPointTangency(mBubbleRadius, mBCx, mBCy, mCtrlX, mCtrlY);
            PointF[] dragBubbllePoints = CircleUtils.getPointTangency(mDragBubbleRadius, mDBCx, mDBCy, mCtrlX, mCtrlY);
            PointF A = bubblePoints[0];
            PointF B = bubblePoints[1];
            PointF C = dragBubbllePoints[0];
            PointF D = dragBubbllePoints[1];
            if (A.x > B.x) {
                mStartX = A.x;
                mStartY = A.y;
                mEndX = B.x;
                mEndY = B.y;
                if (C.x > D.x) {
                    mDEndX = C.x;
                    mDEndY = C.y;
                    mDStartX = D.x;
                    mDStartY = D.y;
                } else {
                    mDEndX = D.x;
                    mDEndY = D.y;
                    mDStartX = C.x;
                    mDStartY = C.y;
                }
            } else if (A.x < B.x) {
                mStartX = A.x;
                mStartY = A.y;
                mEndX = B.x;
                mEndY = B.y;
                if (C.x < D.x) {
                    mDEndX = C.x;
                    mDEndY = C.y;
                    mDStartX = D.x;
                    mDStartY = D.y;
                } else {
                    mDEndX = D.x;
                    mDEndY = D.y;
                    mDStartX = C.x;
                    mDStartY = C.y;
                }
            } else if (A.x == B.x) {
                if (A.y > B.y) {
                    mStartX = A.x;
                    mStartY = A.y;
                    mEndX = B.x;
                    mEndY = B.y;
                    if (C.y > D.y) {
                        mDEndX = C.x;
                        mDEndY = C.y;
                        mDStartX = D.x;
                        mDStartY = D.y;
                    } else {
                        mDEndX = D.x;
                        mDEndY = D.y;
                        mDStartX = C.x;
                        mDStartY = C.y;
                    }
                } else {
                    mStartX = B.x;
                    mStartY = B.y;
                    mEndX = A.x;
                    mEndY = A.y;
                    if (C.y > D.y) {
                        mDEndX = D.x;
                        mDEndY = D.y;
                        mDStartX = C.x;
                        mDStartY = C.y;
                    } else {
                        mDEndX = C.x;
                        mDEndY = C.y;
                        mDStartX = D.x;
                        mDStartY = D.y;
                    }
                }
            }

            Log.e("MSL", "onDraw: " + mStartX + " ," + mStartY +"\n" + mDEndX +","+ mDEndY +
                    " \n" + mDStartX + "," + mStartY + "\n" + mEndX + "," + mEndY);
            Log.e("MSL", "onDraw: " + A.x + " ,"+ A.y + " ,"+ B.x + " ,"+ B.y + " ,"+ C.x + " ,"+ C.y + " ,"+ D.x + " ,"+ D.y );


            //画贝塞尔曲线
            mBezierPath.reset();
            mBezierPath.moveTo(mStartX, mStartY);
            mBezierPath.quadTo(mCtrlX, mCtrlY, mDEndX, mDEndY);
            mBezierPath.lineTo(mDStartX, mDStartY);
            mBezierPath.quadTo(mCtrlX, mCtrlY, mEndX, mEndY);
            mBezierPath.close();
            canvas.drawPath(mBezierPath, mPaint);
        }
        if (mState != DISMISS && !TextUtils.isEmpty(mText))

        {
            mTextPaint.getTextBounds(mText, 0, mText.length(), mRect);
            canvas.drawText(mText, mDBCx - mTextPaint.measureText(mText), mDBCy + mText.length() / 2, mTextPaint);
        }

    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mState == DISMISS) break;
                //request父控件不拦截点击事件
                getParent().requestDisallowInterceptTouchEvent(true);

                defLength = (float) Math.hypot(event.getX() - mDBCx, event.getY() - mDBCy);
                if (defLength < mDragBubbleRadius + defMaxLength / 4) {
                    mState = DRAG;
                } else {
                    mState = DEFAULT;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (mState == DEFAULT) break;
                getParent().requestDisallowInterceptTouchEvent(true);
                mDBCx = event.getX();
                mDBCy = event.getY();
                defLength = (float) Math.hypot(mDBCx - mBCx, mDBCy - mBCy);
                if (mState == DRAG) {
                    if (defLength < defMaxLength - defMaxLength / 4) {//
                        mBubbleRadius = mDragBubbleRadius - defLength / 8;//小球逐渐变小，直至逐渐消失
                        if (mBubbleStateListener != null) {
                            mBubbleStateListener.onDrag();
                        }
                    } else {//间距大于最大拖拽距离之后
                        mState = MOVE;//开始拖动
                        if (mBubbleStateListener != null) {
                            mBubbleStateListener.onMove();
                        }
                    }
                }
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                getParent().requestDisallowInterceptTouchEvent(false);

                //拖拽过程中未移动之前(小气泡没有消失之前)松手，气泡回到原来位置，并颤动一下
                if (mState == DRAG) {
                    setBubbleResetAnimation();
                } else if (mState == MOVE) {
                    if (defLength < 2 * mDragBubbleRadius) {
                        //松手的位置距离在气泡原始位置周围，说明user不想取消这个提示，让气泡回到原来位置并颤动
                        setBubbleResetAnimation();
                    } else {
                        //取消气泡显示，并显示dismiss的动画
                    }
                }
                break;
        }
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setBubbleResetAnimation() {
        ValueAnimator valueAnimator = ValueAnimator.ofObject(new PointFEvaluator(),
                new PointF(mDBCx, mDBCy), new PointF(mBCx, mBCy));
        valueAnimator.setDuration(500);

        valueAnimator.setInterpolator(new TimeInterpolator() {
            @Override
            public float getInterpolation(float input) {

                float f = 0.4234219f;
                return (float) (Math.pow(2, -4 * input) * Math.sin((input - f / 4) * (2 * Math.PI) / f) + 1);
            }
        });
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF c = (PointF) animation.getAnimatedValue();
                mDBCx = c.x;
                mDBCy = c.y;
                invalidate();
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mState = DEFAULT;
                if (mBubbleStateListener == null) return;
                mBubbleStateListener.onReset();
            }
        });
        valueAnimator.start();
    }

    public interface BubbleStateListener {

        void onDrag();

        void onMove();

        void onReset();

        void onDismiss();

    }

    private BubbleStateListener mBubbleStateListener;

    public void setmBubbleStateListener(BubbleStateListener mBubbleStateListener) {
        this.mBubbleStateListener = mBubbleStateListener;
        invalidate();
    }

}
