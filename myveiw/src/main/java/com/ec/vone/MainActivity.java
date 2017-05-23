package com.ec.vone;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;

import com.ec.vone.view.ImgWheelView;
import com.ec.vone.view.MsgBubbleView;

public class MainActivity extends AppCompatActivity {

    private MsgBubbleView msgBubbleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        msgBubbleView.setmBubbleStateListener(new MsgBubbleView.BubbleStateListener() {
            @Override
            public void onDrag() {

            }

            @Override
            public void onMove() {

            }

            @Override
            public void onReset() {

            }

            @Override
            public void onDismiss() {

            }
        });
    }

    private void initView() {
        msgBubbleView = (MsgBubbleView) findViewById(R.id.msgbubbleview);
    }
}