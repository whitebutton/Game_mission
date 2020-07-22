package com.example.administrator.game_mission.More.lhy;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.administrator.game_mission.AllCtl;
import com.example.administrator.game_mission.More.lh.ResultActivity;
import com.example.administrator.game_mission.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LHYActivity extends AppCompatActivity {

    private ImageView back;
    private GridView flopcard;
    private flopAdapter flopAdapter;
    private Integer img[] = {R.mipmap.fenye, R.mipmap.hehua, R.mipmap.maisui, R.mipmap.meigui, R.mipmap.shuye, R.mipmap.yujinxiang, R.mipmap.xiangri, R.mipmap.damaoxian, R.mipmap.zhenxinhua};
    private Integer img2[] = {R.mipmap.eyu2, R.mipmap.shizi2, R.mipmap.xiong2, R.mipmap.pneghuo2, R.mipmap.tuzi2, R.mipmap.huolie2, R.mipmap.shayu2, R.mipmap.yazi2, R.mipmap.qingwa2};
    private ArrayList<Integer> allnum = new ArrayList<>();
    private SoundPool soundPool;//音频通知声音播放器
    private int soundID;//音频资源ID
    private Boolean oneStart=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lhy);
        initView();
        flushArr(img);
        flushArr(img2);
        flopAdapter = new flopAdapter();
        flopcard.setAdapter(flopAdapter);
        initSound();
    }

    private void initView() {
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        flopcard = (GridView) findViewById(R.id.flopcard);
    }

    private void flop(final ImageView imageView, ImageView textView) {
        textView.setVisibility(View.VISIBLE);
        AnimatorSet inAnimator = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.rotate_in_anim);
        AnimatorSet outAnimator = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.rotate_out_anim);
        int distance = 16000;
        float scale = getResources().getDisplayMetrics().density * distance;
        imageView.setCameraDistance(scale); //设置镜头距离
        textView.setCameraDistance(scale); //设置镜头距离
        outAnimator.setTarget(imageView);
        inAnimator.setTarget(textView);
        outAnimator.start();
        inAnimator.start();
        outAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                imageView.setVisibility(View.GONE);
                imageView.setAlpha(1.0f);
                imageView.setRotationY(0.0f);
            }
        });
    }
    //音频
    @SuppressLint("NewApi")
    private void initSound() {
        soundPool = new SoundPool.Builder().build();
        soundID = soundPool.load(this, R.raw.fanpai, 1);
    }//实例化soundPool和soundID  R.raw.qipao为音频资源位置

    private void playSound() {
        if (AllCtl.sound){
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
    private class flopAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 9;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(final int i, View view, final ViewGroup viewGroup) {
            View view1 = View.inflate(LHYActivity.this, R.layout.flopcardadapter, null);
            final ViewHolder viewHolder = new ViewHolder(view1);
            viewHolder.img1.setImageResource(img2[i]);
            viewHolder.img2.setImageResource(img[i]);
            int number = 0;
            for (int num : allnum) {
                if (num == i) {
                    viewHolder.img2.setVisibility(View.VISIBLE);
                    viewHolder.img1.setVisibility(View.VISIBLE);
                    break;
                }
                number++;
                if (number == allnum.size()) {
                    flop(viewHolder.img1, viewHolder.img2);
                }
            }
            viewHolder.img1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    if (!oneStart) return;
                    playSound();
                    flop(viewHolder.img1, viewHolder.img2);
                    allnum.add(i);
                    if (img[i].equals(R.mipmap.damaoxian)) {
                        oneStart=false;
                        Toast.makeText(LHYActivity.this, "您抽中的是大冒险，即将跳转", Toast.LENGTH_SHORT).show();
                        longtiemall();
                        goToTheGame(0);
                    }
                    if (img[i].equals(R.mipmap.zhenxinhua)) {
                        oneStart=false;
                        Toast.makeText(LHYActivity.this, "您抽中的是真心话，即将跳转", Toast.LENGTH_SHORT).show();
                        longtiemall();
                        goToTheGame(1);
                    }
                }
            });
            return view1;
        }
        private void goToTheGame(final int num){
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(LHYActivity.this, ResultActivity.class);
                    intent.putExtra("types", num);
                    startActivity(intent);
                }
            }, 3000);
        }
        private void longtiemall() {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    flopAdapter.notifyDataSetChanged();
                }
            }, 1000);
        }

        public class ViewHolder {
            public View rootView;
            public ImageView img1;
            public ImageView img2;
            public FrameLayout fl;

            public ViewHolder(View rootView) {
                this.rootView = rootView;
                this.img1 = (ImageView) rootView.findViewById(R.id.img1);
                this.img2 = (ImageView) rootView.findViewById(R.id.img2);
                this.fl = (FrameLayout) rootView.findViewById(R.id.fl);
            }

        }
    }

    public static void flushArr(Integer[] arr) {
        int length = arr.length;

        int index = length - 1;

        for (int i = 0; i < length && index > 0; i++) {
            int num = createRandom(index);
            int temp = arr[num];
            arr[num] = arr[index];
            arr[index] = temp;
            index--;
        }
    }

    public static int createRandom(int end) {
        return (new Random().nextInt(end));
    }

    @Override
    protected void onResume() {
        super.onResume();
        flushArr(img);
        flushArr(img2);
        oneStart=true;
        allnum.clear();
        flopAdapter = new flopAdapter();
        flopcard.setAdapter(flopAdapter);
    }
}
