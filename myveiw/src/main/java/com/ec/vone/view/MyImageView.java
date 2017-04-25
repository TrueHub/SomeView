package com.ec.vone.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.ec.vone.R;

/**
 * Created by user on 2017/4/20.
 */

public class MyImageView extends View {


    private Paint mPaint;
    private int mImageTitleColor;
    private int mImageTitleSize;
    private  String mImageTitle;
    private int mImageScale;
    private Bitmap mImage;
    private int mWidth;
    private int mHeight;
    private Rect rect;

    public MyImageView(Context context) {
        this(context,null);
    }

    public MyImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs,R.styleable.CustomImageView,defStyleAttr,0);

        for (int i = 0; i < typedArray.length(); i++) {

            int attr = typedArray.getIndex(i);
            switch (attr) {
                case R.styleable.CustomImageView_image:
                    mImage = BitmapFactory.decodeResource(getResources(),typedArray.getResourceId(attr,0));
                    break;
                case R.styleable.CustomImageView_scaleType:
                    mImageScale = typedArray.getInt(attr,-0);
                    break;
                case R.styleable.CustomImageView_titleText:
                    mImageTitle = typedArray.getString(attr);
                    break;

                case R.styleable.CustomImageView_titleTextColor:
                    mImageTitleColor = typedArray.getColor(attr, Color.BLACK);
                    break;

                case R.styleable.CustomImageView_titleTextSize:
                    mImageTitleSize = typedArray.getDimensionPixelSize(attr,
                            (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,16,getResources().getDisplayMetrics()));
                    break;

            }

        }
        typedArray.recycle();

        mPaint = new Paint();
        mPaint.setTextSize(mImageTitleSize);
        mPaint.setAntiAlias(true);

        rect = new Rect();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            mWidth = specSize;
        }else if (specMode == MeasureSpec.AT_MOST){
            int textWidth = (int) (mPaint.measureText(mImageTitle) + getPaddingLeft() + getPaddingRight());
            int imageWidth = mImage.getWidth() + getPaddingLeft() + getPaddingRight();
            int desire = Math.max(textWidth,imageWidth);
            mWidth = Math.min(desire,specSize);
        }

        specMode = MeasureSpec.getMode(heightMeasureSpec);
        specSize = MeasureSpec.getSize(heightMeasureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            mHeight = specSize;
        }else if (specMode == MeasureSpec.AT_MOST){
            int textHeight = (int) Math.abs(mPaint.getFontMetrics().bottom - mPaint.getFontMetrics().top);
            int imageHeight =  mImage.getHeight();
            int desire = textHeight + imageHeight + getPaddingBottom() + getPaddingTop();
            mHeight = Math.min(desire,specSize);
        }

        setMeasuredDimension(mWidth,mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //画边框
        mPaint.setColor(Color.CYAN);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(3);
        canvas.drawRect(0,0,mWidth,mHeight,mPaint);

        rect.left = getPaddingLeft();
        rect.right = mWidth - getPaddingRight();
        rect.top = getPaddingTop();
        rect.bottom = mHeight -  getPaddingBottom();

        //画title
        //1.mWidth已经指定，即不是wrapContent的状态：

        mPaint.setColor(mImageTitleColor);
//        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(3);

        if (mPaint.measureText(mImageTitle) > mWidth) {
            TextPaint textPaint = new TextPaint(mPaint);
            String str = TextUtils.ellipsize(mImageTitle,textPaint,(float)(mWidth -getPaddingLeft() - getPaddingRight()),
                    TextUtils.TruncateAt.END).toString();
            canvas.drawText(str,getPaddingLeft(),mHeight - getPaddingBottom()- mPaint.getFontMetrics().bottom,mPaint);
        } else {
            canvas.drawText(mImageTitle,(mWidth - mPaint.measureText(mImageTitle))/2 + getPaddingLeft(),
                    mHeight - getPaddingBottom() - mPaint.getFontMetrics().bottom,mPaint);
        }

        rect.bottom -= (int) Math.abs(mPaint.getFontMetrics().bottom - mPaint.getFontMetrics().top);

        //画bitmap
        if (mImageScale == 0){
            canvas.drawBitmap(mImage,null,rect,mPaint);
        } else {
            rect.left = (mWidth - mImage.getWidth()) /2 + getPaddingLeft();
            rect.right = (mWidth + mImage.getWidth()) /2 + getPaddingRight();
            rect.top = (mHeight - mImage.getHeight()
                    - (int) Math.abs(mPaint.getFontMetrics().bottom - mPaint.getFontMetrics().top))
                    /2 + getPaddingTop();
            rect.bottom = (mHeight + mImage.getHeight()
                    -(int) Math.abs(mPaint.getFontMetrics().bottom - mPaint.getFontMetrics().top))
                    /2 + getPaddingBottom();

            canvas.drawBitmap(mImage,null,rect,mPaint);
        }

    }

    //字体居中的y
    public float getY() {
        Paint.FontMetricsInt fm = mPaint.getFontMetricsInt();
        return (getHeight() + fm.bottom - fm.top) / 2 - fm.descent;
    }
}
