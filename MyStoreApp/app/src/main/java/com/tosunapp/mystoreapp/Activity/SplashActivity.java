package com.tosunapp.mystoreapp.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import com.tosunapp.mystoreapp.BaseActivity.BaseActivity;
import com.tosunapp.mystoreapp.R;
import com.tosunapp.mystoreapp.TinyDB.TinyDB;
import com.tosunapp.mystoreapp.Utils.Constant;

public class SplashActivity extends BaseActivity {

    /**
     * Activity class name holder variable
     */
    private static final String CLASS_NAME = SplashActivity.class.getSimpleName();
    /**
     * Variables
     */
    public TinyDB tinyDB;
    CountDownTimer cTimer = null;



    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_splash;
    }

    @Override
    protected Activity getActivity() {
        return this;
    }

    @Override
    protected void initView() {
        tinyDB = new TinyDB(getActivity());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startTimer();
    }


    //start timer func
    void startTimer() {
        cTimer = new CountDownTimer(2000, 1000) {
            public void onTick(long millisUntilFinished) {

            }
            public void onFinish() {


                if (tinyDb.getBoolean(Constant.isChecked)) {
                    startActivity(new Intent(getActivity(), MainActivity.class));
                    activity.finish();
                }
                else {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    activity.finish();
                }
            }
        };
        cTimer.start();
    }

    //Stop timer func
    void cancelTimer() {
        if(cTimer!=null)
            cTimer.cancel();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        cancelTimer();
    }


}
