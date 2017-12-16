package com.ec.vone.ui;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.ec.vone.R;
import com.ec.vone.view.MyJboxLayout;

public class MainActivity extends AppCompatActivity {

    private MyJboxLayout mobikeView;
    private SensorManager sensorManager;
    private Sensor defaultSensor;

    private int[] imgs = new int[]{
            R.drawable.ssdk_oks_classic_facebook,
            R.drawable.ssdk_oks_classic_foursquare,
            R.drawable.ssdk_oks_classic_googleplus,
            R.drawable.ssdk_oks_classic_instapaper,
            R.drawable.ssdk_oks_classic_kakaostory,
            R.drawable.ssdk_oks_classic_linkedin,
            R.drawable.ssdk_oks_classic_pinterest,
            R.drawable.ssdk_oks_classic_qzone,
            R.drawable.ssdk_oks_classic_tumblr,
            R.drawable.ssdk_oks_classic_wechatfavorite,
            R.drawable.ssdk_oks_classic_yixinmoments,
            R.drawable.ssdk_oks_classic_youdao,
            R.drawable.ssdk_oks_classic_wechatmoments
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        getSupportActionBar().setTitle("Mobike Anim");

        mobikeView = (MyJboxLayout) findViewById(R.id.mobikeView);

        initViews();

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        defaultSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    private void initViews() {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        for (int img : imgs) {
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(img);
            imageView.setTag(R.id.mobike_view_circle_tag, true);
            mobikeView.addView(imageView, layoutParams);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mobikeView.getmMobike().onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mobikeView.getmMobike().onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(listener, defaultSensor, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(listener);
    }

    private SensorEventListener listener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                float x = event.values[0];
                float y = event.values[1] * 2.0f;
                mobikeView.getmMobike().onSensorChanged(-x, y);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };
}