package com.example.administrator.game_mission;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {


    private ImageView back;
    private Switch soundButton;
    private Switch shockButton;
    private TextView removeAdvice;
    private TextView writeCom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
        choseFlag();
    }

    private void initView() {
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(this);
        soundButton = (Switch) findViewById(R.id.soundButton);
        soundButton.setOnClickListener(this);
        shockButton = (Switch) findViewById(R.id.shockButton);
        shockButton.setOnClickListener(this);
//        removeAdvice = (TextView) findViewById(R.id.removeAdvice);
//        removeAdvice.setOnClickListener(this);
//        writeCom = (TextView) findViewById(R.id.writeCom);
//        writeCom.setOnClickListener(this);
    }

    private void choseFlag() {
        soundButton.setChecked(AllCtl.sound);
        shockButton.setChecked(AllCtl.shock);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.soundButton:
                AllCtl.currTypeeditor.putBoolean("sound", !AllCtl.sound);
                AllCtl.currTypeeditor.apply();
                AllCtl.sound = !AllCtl.sound;
                break;
            case R.id.shockButton:
                AllCtl.currTypeeditor.putBoolean("shock", !AllCtl.shock);
                AllCtl.currTypeeditor.apply();
                AllCtl.shock = !AllCtl.shock;
                break;
//            case R.id.removeAdvice:
//                break;
//            case R.id.writeCom:
//                break;
        }
    }
}
