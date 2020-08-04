package com.example.administrator.game_mission.More.zwn;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.administrator.game_mission.AllCtl;
import com.example.administrator.game_mission.More.lh.ResultActivity;
import com.example.administrator.game_mission.R;
import com.example.administrator.game_mission.Random.QuestionActivity;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

public class Zwn2Activity extends AppCompatActivity implements View.OnClickListener {

    private ImageView back;
    private GridView grid_zwn2;
    private int num1, num2;
    private Set<Integer> box = new HashSet<>();
    private ImageView grid_anim;
    private AnimationDrawable drawable;
    private GridAdapter adapter = new GridAdapter();
    private Boolean onecStart = true;
    private SoundPool soundPool;//音频通知声音播放器
    private int soundID;//音频资源ID
    private Button changeQuestionButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zwn2);
        Intent intent = getIntent();
        num1 = intent.getIntExtra("num1", 1);
        num2 = intent.getIntExtra("num2", 6);
        Log.i("TAG", "onCreate: " + num1);
        initView();
        initData();
        initSound();
    }

    private void initData() {
        box.clear();
        while (box.size() < num1) {
            int boom = new Random().nextInt(num2);
            box.add(boom);
            Log.i("TAG", "initData: " + boom);
        }
    }

    private void initView() {
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        grid_zwn2 = (GridView) findViewById(R.id.grid_zwn2);
        grid_zwn2.setAdapter(adapter);

        grid_anim = (ImageView) findViewById(R.id.grid_anim);
        drawable = (AnimationDrawable) grid_anim.getDrawable();
        changeQuestionButton = (Button) findViewById(R.id.changeQuestionButton);
        changeQuestionButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.changeQuestionButton:
                startActivity(new Intent(this,QuestionActivity.class));
                break;
        }
    }

    class GridAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return num2;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(Zwn2Activity.this).inflate(R.layout.item_grid_zwn2, parent, false);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.item_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!onecStart) return;
                    viewHolder.item_img.setImageResource(R.drawable.box_open);
                    Iterator iterator = box.iterator();
                    while (iterator.hasNext()) {
                        if (position == (Integer) iterator.next()) {
                            onecStart = false;
                            viewHolder.item_img.setImageResource(R.drawable.box_open2);
                            grid_anim.setVisibility(View.VISIBLE);
                            drawable.start();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(Zwn2Activity.this, ResultActivity.class);
                                    intent.putExtra("types", 2);
                                    startActivity(intent);
                                }
                            }, 1000);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if (AllCtl.sound) {
                                        playSound();
                                    }
                                }
                            }, 800);
                            //震动
                            if (AllCtl.shock) {
                                Vibrator vibrator = (Vibrator) Zwn2Activity.this.getSystemService(VIBRATOR_SERVICE);
                                vibrator.vibrate(800);
                            }
                        }
                    }
                }
            });
            return convertView;
        }

        class ViewHolder {
            public View rootView;
            public ImageView item_img;

            public ViewHolder(View rootView) {
                this.rootView = rootView;
                this.item_img = (ImageView) rootView.findViewById(R.id.item_img);
            }

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
        onecStart = true;
        adapter.notifyDataSetChanged();
        grid_zwn2.setAdapter(adapter);
        grid_anim.setVisibility(View.GONE);
    }

    //音频
    @SuppressLint("NewApi")
    private void initSound() {
        soundPool = new SoundPool.Builder().build();
        soundID = soundPool.load(this, R.raw.boom, 1);
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
