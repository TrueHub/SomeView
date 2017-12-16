package com.ec.vone.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Region;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * Created by longyang on 2017/12/6.
 *
 */

public class RegionTestView extends View {
    private Region region;
    private Path path;
    private Paint mPaint;

    public RegionTestView(Context context) {
        this(context, null);
    }

    public RegionTestView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RegionTestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        region = new Region();
        path = new Path();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.GREEN);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        path.addCircle(w / 2, h / 2, 200, Path.Direction.CW);
        region.setPath(path, new Region(-w, -h, w, h));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Path mPath = this.path;
        canvas.drawPath(mPath,mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                int x = (int) event.getX();
                int y = (int) event.getY();
                if (region.contains(x,y)){
                    Toast.makeText(getContext(), "点击了圆", Toast.LENGTH_SHORT).show();
                }

                break;
        }

        return true;
    }
}
