package com.example.administrator.game_mission;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.game_mission.More.MoreActivity;
import com.example.administrator.game_mission.Random.QuestionActivity;
import com.example.administrator.game_mission.Random.RandomActivity;

import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button more;
    private Button random;
    private HttpUtil httpUtil;
    private Button tiku;
    private TextView currTopic;
    private SoundPool soundPool;//音频通知声音播放器
    private int soundID;//音频资源ID
    private ImageView set;

    private UserBean userBean;//用户

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        httpUtil = new HttpUtil(MainActivity.this);
        JSONObject jsonObject = new JSONObject();
        httpUtil.httpPost("getAll", jsonObject, new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                AllCtl.allBean = (AllBean) msg.obj;
                choseQusetionBank(AllCtl.currType.getString("curr", "普通"));
            }
        }, AllBean.class);
        AllCtl.currType = getSharedPreferences("currType", MODE_PRIVATE);
        AllCtl.currTypeeditor = AllCtl.currType.edit();
        currTopic.setText("当前题库：" + AllCtl.currType.getString("curr", "普通"));
        initSound();
        initSetting();
        animStart();
        initUser();
    }

    private void initUser() {
        if (!AllCtl.currType.getBoolean("is",false)){
            httpUtil.httpPost("addUser",new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    CodeBean code = (CodeBean) msg.obj;
                    if (code.getCode()==200){
                        httpUtil.httpPost("endUser",new Handler(){
                            @Override
                            public void handleMessage(Message msg) {
                                super.handleMessage(msg);
                                userBean = (UserBean) msg.obj;
                                AllCtl.currTypeeditor.putBoolean("is",true);
                                AllCtl.currTypeeditor.putString("user",userBean.getData().getUserName()+""+userBean.getData().getId());
                                AllCtl.currTypeeditor.commit();
                            }
                        },UserBean.class);
                    }
                }
            },CodeBean.class);
        } else {
            Log.i("TAGG", "initUser: "+AllCtl.currType.getString("user",""));
        }
    }

    private void initView() {
        more = (Button) findViewById(R.id.more);
        random = (Button) findViewById(R.id.random);
        more.setOnClickListener(this);
        random.setOnClickListener(this);
        tiku = (Button) findViewById(R.id.tiku);
        tiku.setOnClickListener(this);
        currTopic = (TextView) findViewById(R.id.currTopic);
        currTopic.setOnClickListener(this);
        set = (ImageView) findViewById(R.id.set);
        set.setOnClickListener(this);
    }

    //音频
    @SuppressLint("NewApi")
    private void initSound() {
        soundPool = new SoundPool.Builder().build();
        soundID = soundPool.load(this, R.raw.click, 1);
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

    private void initSetting() {
        AllCtl.sound = AllCtl.currType.getBoolean("sound", true);
        AllCtl.shock = AllCtl.currType.getBoolean("shock", true);
        Log.i("sound", "initSetting: " + AllCtl.sound);
        Log.i("shock", "initSetting: " + AllCtl.shock);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.more:
                if (reconnection()) {
                    AllCtl.continueGame = false;
                    playSound();
                    startActivity(new Intent(MainActivity.this, MoreActivity.class));
                }
                break;
            case R.id.random:
                if (reconnection()) {
                    AllCtl.continueGame = true;
                    playSound();
                    startActivity(new Intent(MainActivity.this, RandomActivity.class));
                }
                break;
            case R.id.set:
                playSound();
                startActivity(new Intent(MainActivity.this, SettingActivity.class));
                break;
            case R.id.tiku:
                if (reconnection()) {
                    playSound();
                    startActivity(new Intent(MainActivity.this, QuestionActivity.class));
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        currTopic.setText("当前题库：" + AllCtl.currType.getString("curr", "普通"));
    }

    public static void choseQusetionBank(String string) {
        AllCtl.alllQuestion.clear();
        AllCtl.trueWords.clear();
        AllCtl.bigAdventure.clear();
//        Log.i("avx", "choseQusetionBank: "+AllCtl.currType.getString("curr", string));
        for (int i = 0; i < AllCtl.allBean.getData().size(); i++) {
            AllBean.DataBean oneBean = AllCtl.allBean.getData().get(i);
            if (oneBean.getLevels().equals(AllCtl.currType.getString("curr", string))) {
                AllCtl.alllQuestion.add(oneBean);
                if (oneBean.getTypes() == 1) {
                    AllCtl.trueWords.add(oneBean);
                } else {
                    AllCtl.bigAdventure.add(oneBean);
                }
            }
        }
        Log.i("aaavx", "choseQusetionBank: " + AllCtl.bigAdventure.size());
    }

    private boolean reconnection() {
        if (AllCtl.allBean == null) {
            httpUtil = new HttpUtil(MainActivity.this);
            JSONObject jsonObject = new JSONObject();
            httpUtil.httpPost("getAll", jsonObject, new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    AllCtl.allBean = (AllBean) msg.obj;
                    choseQusetionBank(AllCtl.currType.getString("curr", "普通"));
                }
            }, AllBean.class);
            Toast.makeText(this, "正在请求数据，请确保开启网络", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private void animStart() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(currTopic, "alpha", 1.0f, 0.3f, 1.0f);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.setDuration(2000);
        animator.start();
    }
}
