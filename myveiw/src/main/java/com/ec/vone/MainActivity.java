package com.ec.vone;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;

import com.ec.vone.view.CircleLoadingView;
import com.ec.vone.view.MyDialView;
import com.ec.vone.view.MyImageView;
import com.ec.vone.view.MyLoadingView;
import com.ec.vone.view.MyTextView;
import com.ec.vone.view.MyVoiceControlView;

public class MainActivity extends AppCompatActivity {

    private MyLoadingView myLoadingView;
    private MyVoiceControlView myVoiceControlView;
    private MyTextView myTextView;
    private MyImageView myImageView;
    private CircleLoadingView circleLoadingView;
    private MyDialView myDialView;
    private SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                myDialView.setProgress(progress);
                circleLoadingView.setProgress(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void initView() {
        myLoadingView = (MyLoadingView) findViewById(R.id.myLoadingView);
        myVoiceControlView = (MyVoiceControlView) findViewById(R.id.myVoiceControlView);
        myTextView = (MyTextView) findViewById(R.id.myTextView);
        myImageView = (MyImageView) findViewById(R.id.myImageView);
        circleLoadingView = (CircleLoadingView) findViewById(R.id.circleLoadingView);
        myDialView = (MyDialView) findViewById(R.id.myDialView);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
    }
}
