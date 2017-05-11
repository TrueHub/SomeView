package com.ec.vone;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;

import com.ec.vone.view.BattaryView;

public class MainActivity extends AppCompatActivity {

    private BattaryView battaryView ;
    private SeekBar seekBar ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                battaryView.setBattaryPercent(progress);
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
        battaryView = (BattaryView) findViewById(R.id.battery_view);
        seekBar = (SeekBar) findViewById(R.id.seek);
    }


}
