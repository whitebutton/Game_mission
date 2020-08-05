package com.example.administrator.game_mission.More.tyx;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.administrator.game_mission.AllCtl;
import com.example.administrator.game_mission.More.lh.ResultActivity;
import com.example.administrator.game_mission.More.lh.widget.MyDialog;
import com.example.administrator.game_mission.R;
import com.example.administrator.game_mission.Random.QuestionActivity;

import java.util.Random;

public class TYXActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView img1;
    private Button bt1;
    private SensorManager sensorManager; //传感器管理
    private MediaPlayer player;
    private AnimationDrawable animationDrawable;
    private int i;// j避免摇一摇时重复执行
    private String[] arr = {"真心话", "大冒险", "喝杯酒", "亲一口", "唱首歌", "跳支舞"};

    private ImageView back;
    private ImageView touzi;
    private Boolean onceStart = true;
    private ImageView animal2;
    private ImageView animal;
    private Button changeQuestionButton;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tyx);
        initView();
        initData();
    }

    /**
     * 摇一摇传感器
     */
    private void initData() {
        sensorManager = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void initView() {
        setData();
        img1 = findViewById(R.id.img1);
        bt1 = findViewById(R.id.bt1);
        player = new MediaPlayer();
        animationDrawable = (AnimationDrawable) getResources().getDrawable(R.drawable.animation);
        img1.setBackgroundDrawable(animationDrawable);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onceStart) {
                    play();
                }
            }
        });
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onceStart) {
                    finish();
                }
            }
        });
        touzi = (ImageView) findViewById(R.id.touzi);
        touzi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!onceStart) return;
                AlertDialog.Builder builder = new AlertDialog.Builder(TYXActivity.this, R.style.defaultDialogStyle);
                View view1 = View.inflate(TYXActivity.this, R.layout.dialog_touzi, null);
                builder.setView(view1);
                builder.create();
                final AlertDialog dialog = builder.show();
                final EditText ed1 = view1.findViewById(R.id.ed1);
                final EditText ed2 = view1.findViewById(R.id.ed2);
                final EditText ed3 = view1.findViewById(R.id.ed3);
                final EditText ed4 = view1.findViewById(R.id.ed4);
                final EditText ed5 = view1.findViewById(R.id.ed5);
                final EditText ed6 = view1.findViewById(R.id.ed6);
                ed1.setText(arr[0]);
                ed2.setText(arr[1]);
                ed3.setText(arr[2]);
                ed4.setText(arr[3]);
                ed5.setText(arr[4]);
                ed6.setText(arr[5]);
                Button fanhui = view1.findViewById(R.id.fanhui);
                Button baocun = view1.findViewById(R.id.baocun);
                fanhui.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setData();
                        dialog.dismiss();
                    }
                });
//                保存骰子点数对应的内容
                baocun.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        arr[0] = ed1.getText().toString().trim();
                        arr[1] = ed2.getText().toString().trim();
                        arr[2] = ed3.getText().toString().trim();
                        arr[3] = ed4.getText().toString().trim();
                        arr[4] = ed5.getText().toString().trim();
                        arr[5] = ed6.getText().toString().trim();
                        boolean b = false;
                        for (int i = 0; i < arr.length; i++) {
//                            判断是否有没填的项
                            if (arr[i].equals("") || arr[i]==null){
                                b = true;
                                break;
                            }
                        }
                        if (!b){
//                            都填了，执行更新操作
                            for (int i = 0; i < arr.length; i++) {
                                editor.putString("arr"+i,arr[i]);
                            }
                            if (editor.commit()){
                                Toast.makeText(TYXActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            } else {
                                Toast.makeText(TYXActivity.this, "保存失败", Toast.LENGTH_SHORT).show();
                            }
                        } else {
//                            没填，提示
                            Toast.makeText(TYXActivity.this, "内容不能为空", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        animal2 = (ImageView) findViewById(R.id.animal2);
        animal2.setOnClickListener(this);
        animal = (ImageView) findViewById(R.id.animal);
        animal.setOnClickListener(this);
        changeQuestionButton = (Button) findViewById(R.id.changeQuestionButton);
        changeQuestionButton.setOnClickListener(this);
    }
    /**
     * 保存初始值
     */
    private void setData(){
        sharedPreferences = getSharedPreferences("tyx",MODE_PRIVATE);
        editor = sharedPreferences.edit();
        if (sharedPreferences.getBoolean("tyxis",false)){
            for (int j = 0; j < arr.length; j++) {
                arr[j] = sharedPreferences.getString("arr"+j,"");
                Log.i("TAG", "setData: "+arr[j]);
            }
        } else {
            for (int j = 0; j < arr.length; j++) {
                editor.putString("arr"+j,arr[j]);
            }
            editor.putBoolean("tyxis",true);
        }
        editor.commit();
    }
    /**
     * 摇一摇
     */
    private SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = Math.abs(event.values[0]);
            float y = Math.abs(event.values[1]);
            float z = Math.abs(event.values[2]);
//            如果摇动幅度大于15，执行骰子动画
            if ((x > 15 || y > 15 || z > 15) && onceStart) {
                play();
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    /**
     * 骰子的动画
     */
    private void play() {
        onceStart = false;
        player = new MediaPlayer();
        animationDrawable = (AnimationDrawable) getResources().getDrawable(R.drawable.animation);
        img1.setBackgroundDrawable(animationDrawable);
        player = MediaPlayer.create(TYXActivity.this, R.raw.m1683);
        animationDrawable.setOneShot(false);
        animationDrawable.start();
        Log.i("aaa", "play: " + AllCtl.sound);
        if (AllCtl.sound) {
            player.start();
        }
        i = new Random().nextInt(6);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                switch (i) {
                    case 0:
                        img1.setBackgroundResource(R.drawable.touzi1);
                        break;
                    case 1:
                        img1.setBackgroundResource(R.drawable.touzi2);
                        break;
                    case 2:
                        img1.setBackgroundResource(R.drawable.touzi3);
                        break;
                    case 3:
                        img1.setBackgroundResource(R.drawable.touzi4);
                        break;
                    case 4:
                        img1.setBackgroundResource(R.drawable.touzi5);
                        break;
                    case 5:
                        img1.setBackgroundResource(R.drawable.touzi6);
                        break;
                }
                animationDrawable.stop();

            }
        }, 1500);
        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                final String name = arr[i];
                final MyDialog myDialog = new MyDialog(TYXActivity.this, R.style.defaultDialogStyle);
                myDialog.setMessage(name);
                //点击外侧不消失
                myDialog.setCanceledOnTouchOutside(false);
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
                        switch (name) {
                            case "真心话":
                                Intent intent = new Intent(TYXActivity.this, ResultActivity.class);
                                intent.putExtra("types", 1);
                                startActivity(intent);
                                break;
                            case "大冒险":
                                Intent intent2 = new Intent(TYXActivity.this, ResultActivity.class);
                                intent2.putExtra("types", 0);
                                startActivity(intent2);
                                break;
                            default:
                                Toast.makeText(TYXActivity.this, "完成挑战，" + name, Toast.LENGTH_SHORT).show();
                                break;
                        }
                        Dialog.dismiss();
                    }

                });
                myDialog.show();
                onceStart = true;
            }
        }, 2000);
    }

    //取消监听
    @Override
    protected void onDestroy() {
        super.onDestroy();
        sensorManager = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);
        sensorManager.unregisterListener(sensorEventListener);
    }


    @Override
    public void onBackPressed() {
        if (onceStart) {
            finish();
        } else {
            Toast.makeText(this, "正在抽取...", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.changeQuestionButton:
                if (onceStart) {
                    startActivity(new Intent(this, QuestionActivity.class));
                }
                break;
        }
    }
}
