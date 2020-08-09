package com.example.administrator.game_mission.More.lh.fragment;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.administrator.game_mission.AllCtl;
import com.example.administrator.game_mission.More.lh.ResultActivity;
import com.example.administrator.game_mission.More.lh.widget.MyDialog;
import com.example.administrator.game_mission.More.lh.widget.Turntable;
import com.example.administrator.game_mission.R;
import com.example.administrator.game_mission.Random.QuestionActivity;

import java.util.Random;


public class PanFragment extends Fragment implements View.OnClickListener {
    private Turntable pan;
    private ImageView go;
    private ImageView back;
    private AnimatorSet animatorSet;
    private int i;
    private boolean running = true;
    private String[] s = new String[10];
    private SoundPool soundPool;//音频通知声音播放器
    private int soundID;//音频资源ID
    private int stream;
    private ImageView animal;
    private Button changeQuestionButton;

    public static PanFragment newInstance(String[] items) {
        PanFragment panFragment = new PanFragment();
        Bundle bundle = new Bundle();
        bundle.putStringArray("items", items);
        panFragment.setArguments(bundle);
        return panFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lh_fragment_pan, container, false);
        initView(view);
        initSound();
        return view;
    }

    private void initView(View view) {
        pan = (Turntable) view.findViewById(R.id.pan);
        go = (ImageView) view.findViewById(R.id.go);
        back = (ImageView) view.findViewById(R.id.back);
        go.setOnClickListener(this);
        back.setOnClickListener(this);
        animatorSet = new AnimatorSet();
        s = getArguments().getStringArray("items");
        pan.setStr(s);

        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                running = false;
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                //暂停声音
                Handler handler = new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        stopSound();
                    }
                });
                //弹出对话框
                running = true;
                Log.d("TAG", s[i]);
                final MyDialog myDialog = new MyDialog(getActivity(), R.style.defaultDialogStyle);
                myDialog.setMessage(s[i]);
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
                        switch (s[i]) {
                            case "真心话":
                                Intent intent = new Intent(getActivity(), ResultActivity.class);
                                intent.putExtra("types", 1);
                                startActivity(intent);
                                break;
                            case "大冒险":
                                Intent intent2 = new Intent(getActivity(), ResultActivity.class);
                                intent2.putExtra("types", 0);
                                startActivity(intent2);
                                break;
                            case "真心话大冒险":
                                Intent intent3 = new Intent(getActivity(), ResultActivity.class);
                                intent3.putExtra("types", 2);
                                startActivity(intent3);
                                break;
                            case "再来一次":
                                startRotate();
                                break;
                            default:
                                Toast.makeText(getActivity(), "完成挑战，" + s[i], Toast.LENGTH_SHORT).show();
                                break;
                        }
                        Dialog.dismiss();
                    }

                });
                myDialog.show();
            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });
        animal = (ImageView) view.findViewById(R.id.animal);
        animal.setOnClickListener(this);
        changeQuestionButton = (Button) view.findViewById(R.id.changeQuestionButton);
        changeQuestionButton.setOnClickListener(this);
    }

    private void startRotate() {
        double onceTime = 100;
        int random = new Random().nextInt(361) + 1080;
        int ooeNum = random / 36;
        double totalTime = ooeNum * onceTime;
        i = (ooeNum - 4) % 10 - 1;
        if (i == -1) {
            i = 9;
        }
        ObjectAnimator rotation = ObjectAnimator.ofFloat(pan, "rotation", -(float) (ooeNum * 36) + 18);
        animatorSet.play(rotation);
        animatorSet.setDuration((long) totalTime);
        animatorSet.start();
        playSound();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.go:
                if (running) {
                    startRotate();
                } else {
                    Toast.makeText(getActivity(), "正在抽取...", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.back:
                if (running) {
                    getActivity().onBackPressed();
                } else {
                    Toast.makeText(getActivity(), "正在抽取...", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.changeQuestionButton:
                if (running){
                    startActivity(new Intent(getContext(),QuestionActivity.class));
                }
                break;
        }
    }

    //音频
    @SuppressLint("NewApi")
    private void initSound() {
        soundPool = new SoundPool.Builder().build();
        soundID = soundPool.load(getActivity(), R.raw.zhuanpan, 1);
    }//实例化soundPool和soundID  R.raw.qipao为音频资源位置

    private void playSound() {
        if (AllCtl.sound) {
            stream = soundPool.play(
                    soundID,
                    1f,      //左耳道音量【0~1】
                    1f,      //右耳道音量【0~1】
                    0,         //播放优先级【0表示最低优先级】
                    -1,         //循环模式【0表示循环一次，-1表示一直循环，其他表示数字+1表示当前数字对应的循环次数】
                    1          //播放速度【1是正常，范围从0~2】
            );
        }
    }

    private void stopSound() {
        soundPool.stop(stream);
    }

    @Override
    public void onResume() {
        super.onResume();
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_BACK && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    if (running) {
                        getActivity().onBackPressed();
                    } else {
                        Toast.makeText(getActivity(), "正在抽取...", Toast.LENGTH_SHORT).show();
                    }
                }
                return true;
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        stopSound();
    }
}
