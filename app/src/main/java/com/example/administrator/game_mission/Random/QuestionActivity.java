package com.example.administrator.game_mission.Random;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAdManager;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAppDownloadListener;
import com.bytedance.sdk.openadsdk.TTRewardVideoAd;
import com.example.administrator.game_mission.Ad.TTAdManagerHolder;
import com.example.administrator.game_mission.Ad.TToast;
import com.example.administrator.game_mission.AllCtl;
import com.example.administrator.game_mission.MainActivity;
import com.example.administrator.game_mission.More.lh.ResultActivity;
import com.example.administrator.game_mission.More.lh.widget.MyDialog;
import com.example.administrator.game_mission.R;

public class QuestionActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "RewardVideoActivity";
    private TTAdNative mTTAdNative;
    private TTRewardVideoAd mttRewardVideoAd;
    private boolean mIsExpress = false; //是否请求模板广告
    @SuppressWarnings("RedundantCast")

    private ImageView back;
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    private Button button6;
    private TextView currTopic;
    private Button lastButton;
    private Button[] allChoseButton;
    private SoundPool soundPool;//音频通知声音播放器
    private int soundID;//音频资源ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        initView();
        initSound();
        lockAllButton();
        firstChose(AllCtl.currType.getString("curr", "普通"));
        currTopic.setText("当前题库：" + AllCtl.currType.getString("curr", "普通"));

        //step1:初始化sdk
        TTAdManager ttAdManager = TTAdManagerHolder.get();
        //step2:(可选，强烈建议在合适的时机调用):申请部分权限，如read_phone_state,防止获取不了imei时候，下载类广告没有填充的问题。
        TTAdManagerHolder.get().requestPermissionIfNecessary(this);
        //step3:创建TTAdNative对象,用于调用广告请求接口
        mTTAdNative = ttAdManager.createAdNative(getApplicationContext());

    }
    private boolean mHasShowDownloadActive = false;

    private void loadAd(final String codeId, int orientation)   {
        //step4:创建广告请求参数AdSlot,具体参数含义参考文档
        AdSlot adSlot;
        if (mIsExpress) {
            //个性化模板广告需要传入期望广告view的宽、高，单位dp，
            adSlot = new AdSlot.Builder()
                    .setCodeId(codeId)
                    .setSupportDeepLink(true)
                    .setRewardName("解锁题库") //奖励的名称
                    .setRewardAmount(1)  //奖励的数量
                    //模板广告需要设置期望个性化模板广告的大小,单位dp,激励视频场景，只要设置的值大于0即可
                    .setExpressViewAcceptedSize(500,500)
                    .setUserID("user123")//用户id,必传参数
                    .setMediaExtra("media_extra") //附加参数，可选
                    .setOrientation(orientation) //必填参数，期望视频的播放方向：TTAdConstant.HORIZONTAL 或 TTAdConstant.VERTICAL
                    .build();
        } else {
            //模板广告需要设置期望个性化模板广告的大小,单位dp,代码位是否属于个性化模板广告，请在穿山甲平台查看
            adSlot = new AdSlot.Builder()
                    .setCodeId(codeId)
                    .setSupportDeepLink(true)
                    .setRewardName("解锁题库") //奖励的名称
                    .setRewardAmount(1)  //奖励的数量
                    .setUserID("user123")//用户id,必传参数
                    .setMediaExtra("media_extra") //附加参数，可选
                    .setOrientation(orientation) //必填参数，期望视频的播放方向：TTAdConstant.HORIZONTAL 或 TTAdConstant.VERTICAL
                    .build();
        }
        //step5:请求广告
        mTTAdNative.loadRewardVideoAd(adSlot, new TTAdNative.RewardVideoAdListener() {
            @Override
            public void onError(int code, String message) {
                Log.e(TAG, "Callback --> onError: " + code + ", " + String.valueOf(message));
                TToast.show(QuestionActivity.this, "错误：" + message);
            }

            //视频广告加载后，视频资源缓存到本地的回调，在此回调后，播放本地视频，流畅不阻塞。
            @Override
            public void onRewardVideoCached() {
                Log.e(TAG, "Callback --> onRewardVideoCached");
//                TToast.show(RewardVideoActivity.this, "Callback --> rewardVideoAd video cached");
            }

            //视频广告的素材加载完毕，比如视频url等，在此回调后，可以播放在线视频，网络不好可能出现加载缓冲，影响体验。
            @Override
            public void onRewardVideoAdLoad(TTRewardVideoAd ad) {
                Log.e(TAG, "Callback --> onRewardVideoAdLoad");
                mttRewardVideoAd = ad;
                mttRewardVideoAd.setRewardAdInteractionListener(new TTRewardVideoAd.RewardAdInteractionListener() {

                    @Override
                    public void onAdShow() {
                        Log.d(TAG, "Callback --> rewardVideoAd show");
//                        TToast.show(RewardVideoActivity.this, "rewardVideoAd show");
                    }

                    @Override
                    public void onAdVideoBarClick() {
                        Log.d(TAG, "Callback --> rewardVideoAd bar click");
//                        TToast.show(RewardVideoActivity.this, "rewardVideoAd bar click");
                    }

                    @Override
                    public void onAdClose() {
                        Log.d(TAG, "Callback --> rewardVideoAd close");
//                        TToast.show(RewardVideoActivity.this, "rewardVideoAd close");
                    }

                    //视频播放完成回调
                    @Override
                    public void onVideoComplete() {
                        Log.d(TAG, "Callback --> rewardVideoAd complete");
                        TToast.show(QuestionActivity.this, "已解锁该题库");
                    }

                    @Override
                    public void onVideoError() {
                        Log.e(TAG, "Callback --> rewardVideoAd error");
                        TToast.show(QuestionActivity.this, "rewardVideoAd error");
                    }

                    //视频播放完成后，奖励验证回调，rewardVerify：是否有效，rewardAmount：奖励梳理，rewardName：奖励名称
                    @Override
                    public void onRewardVerify(boolean rewardVerify, int rewardAmount, String rewardName) {
                        String logString = "verify:" + rewardVerify + " amount:" + rewardAmount +
                                " name:" + rewardName;
                        Log.e(TAG, "Callback --> " + logString);
                        TToast.show(QuestionActivity.this, logString);
                    }

                    @Override
                    public void onSkippedVideo() {
                        Log.e(TAG, "Callback --> rewardVideoAd has onSkippedVideo");
//                        TToast.show(RewardVideoActivity.this, "rewardVideoAd has onSkippedVideo");
                    }
                });
                mttRewardVideoAd.setDownloadListener(new TTAppDownloadListener() {
                    @Override
                    public void onIdle() {
                        mHasShowDownloadActive = false;
                    }

                    @Override
                    public void onDownloadActive(long totalBytes, long currBytes, String fileName, String appName) {
                        Log.d("DML", "onDownloadActive==totalBytes=" + totalBytes + ",currBytes=" + currBytes + ",fileName=" + fileName + ",appName=" + appName);

                        if (!mHasShowDownloadActive) {
                            mHasShowDownloadActive = true;
                            TToast.show(QuestionActivity.this, "下载中，点击下载区域暂停", Toast.LENGTH_LONG);
                        }
                    }

                    @Override
                    public void onDownloadPaused(long totalBytes, long currBytes, String fileName, String appName) {
                        Log.d("DML", "onDownloadPaused===totalBytes=" + totalBytes + ",currBytes=" + currBytes + ",fileName=" + fileName + ",appName=" + appName);
                        TToast.show(QuestionActivity.this, "下载暂停，点击下载区域继续", Toast.LENGTH_LONG);
                    }

                    @Override
                    public void onDownloadFailed(long totalBytes, long currBytes, String fileName, String appName) {
                        Log.d("DML", "onDownloadFailed==totalBytes=" + totalBytes + ",currBytes=" + currBytes + ",fileName=" + fileName + ",appName=" + appName);
                        TToast.show(QuestionActivity.this, "下载失败，点击下载区域重新下载", Toast.LENGTH_LONG);
                    }

                    @Override
                    public void onDownloadFinished(long totalBytes, String fileName, String appName) {
                        Log.d("DML", "onDownloadFinished==totalBytes=" + totalBytes + ",fileName=" + fileName + ",appName=" + appName);
                        TToast.show(QuestionActivity.this, "下载完成，点击下载区域重新下载", Toast.LENGTH_LONG);
                    }

                    @Override
                    public void onInstalled(String fileName, String appName) {
                        Log.d("DML", "onInstalled==" + ",fileName=" + fileName + ",appName=" + appName);
                        TToast.show(QuestionActivity.this, "安装完成，点击下载区域打开", Toast.LENGTH_LONG);
                    }
                });
            }
        });
    }


    private void initView() {
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(this);
        button4 = (Button) findViewById(R.id.button4);
        button4.setOnClickListener(this);
        button5 = (Button) findViewById(R.id.button5);
        button5.setOnClickListener(this);
        button6 = (Button) findViewById(R.id.button6);
        button6.setOnClickListener(this);
        currTopic = (TextView) findViewById(R.id.currTopic);
        currTopic.setOnClickListener(this);
        allChoseButton = new Button[]{button2, button3, button4, button5, button6};
    }

    private void lockOneButton(Button button) {
        button.setCompoundDrawablePadding(-20);
        button.setBackgroundResource(R.drawable.button_not_selected);
        Drawable drawable = ContextCompat.getDrawable(this, R.mipmap.suo);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        button.setCompoundDrawables(null, null, drawable, null);
        button.setCompoundDrawablePadding(-70);
    }

    private void choseTopic(String string, Button button) {
        playSound();
        AllCtl.currTypeeditor.putString("curr", string);
        AllCtl.currTypeeditor.apply();
        currTopic.setText("当前题库：" + AllCtl.currType.getString("curr", "普通"));
        if (lastButton != null) {
            lastButton.setBackgroundResource(R.drawable.button_main_bg);
        }
        lastButton = button;
        button.setBackgroundResource(R.drawable.button_selected);
        MainActivity.choseQusetionBank(AllCtl.currType.getString("curr", "普通"));
    }

    private void lockAllButton() {
        for (int i = 0; i < allChoseButton.length; i++) {
            if (!AllCtl.currType.getBoolean(String.valueOf(allChoseButton[i].getId()), false)) {
                lockOneButton(allChoseButton[i]);
            }
        }
    }

    private void unLockButton(Button button) {
        button.setBackgroundResource(R.drawable.button_main_bg);
        button.setCompoundDrawables(null, null, null, null);
        AllCtl.currTypeeditor.putBoolean(String.valueOf(button.getId()), true);
        AllCtl.currTypeeditor.apply();
    }

    private void firstChose(String s) {
        switch (s) {
            case "普通":
                choseTopic("普通", button1);
                break;
            case "好朋友":
                choseTopic("好朋友", button2);
                break;
            case "同学聚会":
                choseTopic("同学聚会", button3);
                break;
            case "酒吧娱乐":
                choseTopic("酒吧娱乐", button4);
                break;
            case "情侣专题":
                choseTopic("情侣专题", button5);
                break;
            case "自定义题库":
                choseTopic("自定义题库", button6);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                choseTopic("普通", button1);
//                startActivity(new Intent(QuestionActivity.this,BuiltInActivity.class));
                break;
            case R.id.button2:
                needUnLock("好朋友", 0);
//                startActivity(new Intent(QuestionActivity.this,CustomActivity.class));
                break;
            case R.id.button3:
                needUnLock("同学聚会", 1);
                break;
            case R.id.button4:
                needUnLock("酒吧娱乐", 2);
                break;
            case R.id.button5:
                needUnLock("情侣专题", 3);
                break;
            case R.id.button6:
                needUnLock("自定义题库", 4);
                break;
        }
    }

    private void needUnLock(String string, int num) {
        loadAd("945329898", TTAdConstant.VERTICAL);
        if (AllCtl.currType.getBoolean(String.valueOf(allChoseButton[num].getId()), false)) {
            choseTopic(string, allChoseButton[num]);
        } else {
            diaglo(allChoseButton[num]);
        }
    }

    private void diaglo(final Button button) {
        final MyDialog myDialog = new MyDialog(this, R.style.defaultDialogStyle);
        myDialog.setMessage("观看广告后解锁");
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
//                unLockButton(button);
                if (mttRewardVideoAd != null) {
                    mttRewardVideoAd.showRewardVideoAd(QuestionActivity.this, TTAdConstant.RitScenes.CUSTOMIZE_SCENES, "scenes_test");
                    mttRewardVideoAd = null;
                    unLockButton(button);
                } else {
                    TToast.show(QuestionActivity.this, "请先加载广告");
                }
//                Toast.makeText(QuestionActivity.this, "已观看完广告", Toast.LENGTH_SHORT).show();
                Dialog.dismiss();
            }
        });
        myDialog.show();
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
}
