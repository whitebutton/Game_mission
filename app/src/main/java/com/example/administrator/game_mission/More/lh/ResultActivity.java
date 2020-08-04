package com.example.administrator.game_mission.More.lh;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.game_mission.AllCtl;
import com.example.administrator.game_mission.More.lh.widget.MyDialog;
import com.example.administrator.game_mission.More.tyx.TYXActivity;
import com.example.administrator.game_mission.R;

import java.util.Random;

public class ResultActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView type;
    private TextView level;
    private ConstraintLayout center;
    private Button close;
    private int types;
    private Button countine;
    private SoundPool soundPool;//音频通知声音播放器
    private int soundID;//音频资源ID
    private Boolean onceGame=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lh_activity_result);
        Intent intent = getIntent();
        types = intent.getIntExtra("types", 0);
        initView();
        initSound();
        setQuestion(types);
        if (AllCtl.continueGame){
            countine.setVisibility(View.VISIBLE);
        }else {
            countine.setVisibility(View.GONE);
        }
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                onceGame=!onceGame;
            }
        },3000);
    }

    private void setQuestion(int num) {
        if (num == 2) {
            Random random = new Random();
            Log.i("aaafa", "setQuestion: " + AllCtl.alllQuestion.size());
            type.setText(AllCtl.alllQuestion.get(random.nextInt(AllCtl.alllQuestion.size())).getContent());
        }
        if (num == 1) {
            Random random = new Random();
            Log.i("aaafa", "setQuestion: " + AllCtl.trueWords.size());
            type.setText(AllCtl.trueWords.get(random.nextInt(AllCtl.trueWords.size())).getContent());
        }
        if (num == 0) {
            Random random = new Random();
            Log.i("aaafa", "setQuestion: " + AllCtl.bigAdventure.size());
            type.setText(AllCtl.bigAdventure.get(random.nextInt(AllCtl.bigAdventure.size())).getContent());
        }
    }

    private void initView() {
        //类型
        type = (TextView) findViewById(R.id.type);
        //等级
        level = (TextView) findViewById(R.id.level);
        //内容
        center = (ConstraintLayout) findViewById(R.id.center);
        close = (Button) findViewById(R.id.close);
        close.setOnClickListener(this);
        level.setText("当前题库：" + AllCtl.currType.getString("curr", "普通"));
        countine = (Button) findViewById(R.id.countine);
        countine.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.close:
                final MyDialog myDialog = new MyDialog(this, R.style.defaultDialogStyle);
                myDialog.setMessage("你确定关闭当前页面吗？");
                myDialog.setCancel(new MyDialog.IOnCancelListener() {
                    @Override
                    public void onCancel(MyDialog Dialog) {
                        Dialog.dismiss();
                    }
                });
                myDialog.setConfirm(new MyDialog.IOnConfirmListener() {
                    @Override
                    public void onConfirm(MyDialog Dialog) {
                        //确定后相应操作
                        Dialog.dismiss();
                        finish();
                    }
                });
                myDialog.show();
                break;
            case R.id.countine:
                if (onceGame){
                    onceGame=!onceGame;
                    setQuestion(types);
                    Handler handler=new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            onceGame=!onceGame;
                        }
                    },3000);
                    if (AllCtl.sound){
                        playSound();
                    }
                }else {
                    Toast.makeText(this, "3秒后才可继续哦", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
    //音频
    @SuppressLint("NewApi")
    private void initSound() {
        soundPool = new SoundPool.Builder().build();
        soundID = soundPool.load(this, R.raw.fanye, 1);
    }//实例化soundPool和soundID  R.raw.qipao为音频资源位置

    private void playSound() {
        if (AllCtl.sound) {
            soundPool.play(
                    soundID,
                    1f,      //左耳道音量【0~1】
                    1f,      //右耳道音量【0~1】
                    0,         //播放优先级【0表示最低优先级】
                    0,         //循环模式【0表示循环一次，-1表示一直循环，其他表示数字+1表示当前数字对应的循环次数】
                    1          //播放速度【1是正常，范围从0~2】
            );
        }
    }

}
