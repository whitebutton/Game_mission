package com.example.administrator.game_mission.More.lh;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.example.administrator.game_mission.More.lh.fragment.ChooseFragment;
import com.example.administrator.game_mission.R;

public class LHActivity extends AppCompatActivity {

    private FrameLayout fl1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lh);
        initView();

    }

    private void initView() {
        fl1 = (FrameLayout) findViewById(R.id.fl1);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl1,new ChooseFragment()).commit();
    }


}
