package com.tosunapp.mystoreapp.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.tosunapp.mystoreapp.BaseActivity.BaseActivity;
import com.tosunapp.mystoreapp.R;
import com.tosunapp.mystoreapp.TinyDB.TinyDB;
import com.tosunapp.mystoreapp.Utils.Constant;

public class LoginActivity extends BaseActivity implements
        CompoundButton.OnCheckedChangeListener {

    /**
     * Activity class name holder variable
     */
    private static final String CLASS_NAME = LoginActivity.class.getSimpleName();
    /**
     * Variables
     */
    public  TinyDB tinyDB;
    private EditText usernameEt,passwordEt;
    private Button  loginBtn;
    private SwitchCompat switchCompat;
    private LinearLayout userLn,passwordLn;

    private Boolean isChecked = false;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_login;
    }

    @Override
    protected Activity getActivity() {
        return this;
    }

    @Override
    protected void initView() {

        tinyDB = new TinyDB(getActivity());

        usernameEt   = findViewById(R.id.et_userName);
        passwordEt   = findViewById(R.id.et_password);
        loginBtn     = findViewById(R.id.btn_login);
        switchCompat = findViewById(R.id.swch);
        userLn       = findViewById(R.id.ln_userName);
        passwordLn   = findViewById(R.id.ln_password);

        switchCompat.setOnCheckedChangeListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        setListeners();
    }

    private void setListeners() {

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userName = usernameEt.getText().toString();
                String password = passwordEt.getText().toString();

                if(isNullOrEmpty(userName)){
                    userLn.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.shake));
                }
                else if(isNullOrEmpty(password)){
                    passwordLn.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.shake));
                }
                else {
                    if(userName.equals("kariyer") && password.equals("2019ADev")){
                        startActivity(new Intent(getActivity(),MainActivity.class));
                        tinyDB.putBoolean(Constant.isChecked,isChecked);
                        finish();
                    }
                    else {
                        getAlert(getString(R.string.login_failed));
                    }

                }

            }
        });
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.swch:
                if(isChecked){
                   this.isChecked = isChecked;
                }
                break;
        }

    }

    //Check String isNull or Empty
    public static boolean isNullOrEmpty(String param) {
        return param == null || param.trim().length() == 0;
    }

}
