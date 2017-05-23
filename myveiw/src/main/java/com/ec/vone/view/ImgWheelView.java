package com.ec.vone.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

import com.ec.vone.R;

/**
 * Created by user on 2017/5/16.
 * 人物状态判断UI 类似简单的轮盘菜单
 */

public class ImgWheelView extends View {

    private int checkedColor;
    private int unCheckedColor;
    private Bitmap[] imgs;
    private int checked;
    private Bitmap centerImg;
    private Paint mPaint;

    public void setChecked(int checked) {
        this.checked = checked;
        postInvalidate();
    }

    public void setImgs(Bitmap[] imgs) {
        this.imgs = imgs;
        postInvalidate();
    }

    public void setCenterImg(Bitmap centerImg) {
        this.centerImg = centerImg;
        postInvalidate();
    }

    public ImgWheelView(Context context) {
        this(context, null);
    }

    public ImgWheelView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImgWheelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ImgWheelView, 0, defStyleAttr);

        for (int i = 0; i < typedArray.length(); i++) {
            int attr = typedArray.getIndex(i);

            switch (attr) {
                case R.styleable.ImgWheelView_checkedColor:
                    checkedColor = typedArray.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.ImgWheelView_unCheckedColor:
                    unCheckedColor = typedArray.getColor(attr, Color.BLACK);
                    break;
            }
        }
        mPaint = new Paint();
        mPaint.setAntiAlias(true);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width, height;

        int aMode = MeasureSpec.getMode(widthMeasureSpec);
        int aSize = MeasureSpec.getSize(widthMeasureSpec);

        if (aMode == MeasureSpec.EXACTLY) {
            width = aSize;
        } else {
            width = (int) (getOutCircleDiameter() + getPaddingLeft() + getPaddingRight());
        }

        aMode = MeasureSpec.getMode(heightMeasureSpec);
        aSize = MeasureSpec.getSize(heightMeasureSpec);

        if (aMode == MeasureSpec.EXACTLY) {
            height = aSize;
        } else {
            height = (int) getOutCircleDiameter() + getPaddingTop() + getPaddingBottom();
        }
        setMeasuredDimension(width, height);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float outRadiu = getWidth() < getHeight() ? (getWidth() - getPaddingRight() - getPaddingLeft()) / 2
                : (getHeight() - getPaddingTop() - getPaddingBottom()) / 2;
        float insideRadiu = outRadiu / 3;
        float Ox = getWidth() / 2;
        float Oy = getHeight() / 2;

        mPaint.setColor(checkedColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(3);
        //画出两个同心圆
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, outRadiu, mPaint);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, insideRadiu, mPaint);

        //渲染bitmap
        int layerId = canvas.saveLayer(0, 0, canvas.getWidth(), canvas.getHeight(), null, Canvas.ALL_SAVE_FLAG);

        //画出中心图片
        canvas.drawBitmap(tintBitmap(centerImg , checkedColor), Ox - centerImg.getWidth() / 2, Oy - centerImg.getHeight() / 2, mPaint);

        //画出传入的bitmap数组
        //放大1.2倍
        Matrix matrix = new Matrix();
        matrix.postScale(1.3f, 1.3f);
        Bitmap currentBmp = Bitmap.createBitmap(imgs[checked], 0, 0, imgs[checked].getWidth(),
                imgs[checked].getHeight(), matrix, true);
        currentBmp = tintBitmap(currentBmp,checkedColor);
        canvas.drawBitmap(currentBmp, Ox + insideRadiu, Oy - currentBmp.getHeight() / 2, mPaint);

        //画未被选中的img
        Bitmap lastBmp = tintBitmap(imgs[(checked + imgs.length * 2 - 1 ) % imgs.length] , unCheckedColor);
        Bitmap nextBmp = tintBitmap(imgs[(checked + imgs.length + 1) % imgs.length] , unCheckedColor);

        canvas.drawBitmap(lastBmp, Ox - lastBmp.getWidth() / 2, Oy - insideRadiu - lastBmp.getHeight(), mPaint);
        canvas.drawBitmap(nextBmp, Ox - nextBmp.getWidth() / 2, Oy + insideRadiu, mPaint);
        canvas.restoreToCount(layerId);

        //画左半边圆弧
        float strokew = (outRadiu - insideRadiu) / 2;
        mPaint.setStrokeWidth(strokew * 2);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawArc(getPaddingLeft() + strokew, getPaddingTop() + strokew,
                getWidth() - getPaddingRight() - strokew,
                getHeight() - getPaddingBottom() - strokew, -110, -130, false, mPaint);
    }

    /**
     * @param bitmap
     * @return 返回bitmap外切圆的直径
     */
    private double getImgExCicleDiameter(Bitmap bitmap) {
        double w = bitmap.getWidth();
        double h = bitmap.getHeight();
        return Math.sqrt(Math.pow(w, 2) + Math.pow(h, 2));
    }

    private double getOutCircleDiameter() {
        double centerDiameter = getImgExCicleDiameter(centerImg);
        Bitmap tmpB = imgs[0];
        double ringSize = getImgExCicleDiameter(tmpB);
        for (int i = 1; i < imgs.length; i++) {
            if (getImgExCicleDiameter(imgs[i]) > ringSize)
                ringSize = getImgExCicleDiameter(imgs[i]);
        }
        return ringSize * 2 + centerDiameter;
    }

    public static Bitmap tintBitmap(Bitmap inBitmap , int tintColor) {
        if (inBitmap == null) {
            return null;
        }
        Bitmap outBitmap = Bitmap.createBitmap (inBitmap.getWidth(), inBitmap.getHeight() , inBitmap.getConfig());
        Canvas canvas = new Canvas(outBitmap);
        Paint paint = new Paint();
        paint.setColorFilter( new PorterDuffColorFilter(tintColor, PorterDuff.Mode.SRC_IN)) ;
        canvas.drawBitmap(inBitmap , 0, 0, paint) ;
        return outBitmap ;
    }
}
