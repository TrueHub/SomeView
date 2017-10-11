package com.ec.vone.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.ec.vone.R;
import com.ec.vone.utils.DataUtils;

import static com.ec.vone.utils.DataUtils.byte2Int;
import static com.ec.vone.utils.DataUtils.bytes2hex;
import static com.ec.vone.utils.DataUtils.int2Bytes;

/**
 * Created by longyang on 2017/9/7.
 */
public class LinerView extends View {
    private float mPaintWidth;
    private int color;
    private boolean startLeft; //左边细
    private Paint mPaint;

    public LinerView(Context context) {
        this(context, null);
    }

    public LinerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LinerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.LinerView, defStyleAttr, 0);

        for (int i = 0; i < typedArray.length(); i++) {
            int attr = typedArray.getIndex(i);
            switch (attr) {
                case R.styleable.LinerView_color:
                    color = typedArray.getColor(attr, Color.GRAY);
                    break;
                case R.styleable.LinerView_lineHeight:
                    mPaintWidth = typedArray.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics())) / 2;
                    break;
                case R.styleable.LinerView_startLeft:
                    startLeft = typedArray.getBoolean(attr, true);
                    break;
            }
        }
        typedArray.recycle();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(color);
        mPaint.setStrokeWidth(mPaintWidth);
    }

    public static int getTranslationColor(int color){
        byte[] b = int2Bytes(color);

        b[0] = (byte)0x00;

        int c = Color.argb(0, byte2Int(b[1]), byte2Int(b[2]), byte2Int(b[3]));
        Log.i("MSL,LinerView", "getTranslationColor: " + bytes2hex(DataUtils.int2Bytes(c)));
        return c;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int translationColor = getTranslationColor(color);

        int[] colors;
        float positions[];
        if (startLeft){
            colors = new int[]{translationColor,  color};
            positions = new float[]{0, 1};
        }else {
            colors = new int[]{color,translationColor };
            positions = new float[]{1, 0};
        }

        LinearGradient linearGradient = new LinearGradient(0, 0, getMeasuredWidth(), 0,
                colors, positions, Shader.TileMode.CLAMP);
        mPaint.setShader(linearGradient);
        canvas.drawLine(0,0,getMeasuredWidth(),0,mPaint);
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
                width = (int) (mPaintWidth * 20 + getPaddingLeft() + getPaddingRight());
                break;
        }

        specMode = MeasureSpec.getMode(heightMeasureSpec);
        specSize = MeasureSpec.getSize(heightMeasureSpec);
        switch (specMode) {
            case MeasureSpec.EXACTLY:
                height = specSize + getPaddingTop() + getPaddingBottom();
                break;
            case MeasureSpec.AT_MOST:
                height = (int) (mPaintWidth * 20 + getPaddingTop() + getPaddingBottom());
                break;
        }
        setMeasuredDimension(width, height);
    }
}
