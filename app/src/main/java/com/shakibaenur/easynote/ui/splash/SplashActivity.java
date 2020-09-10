package com.shakibaenur.easynote.ui.splash;

import android.content.Intent;

import com.shakibaenur.easynote.R;
import com.shakibaenur.easynote.databinding.ActivitySplashBinding;
import com.shakibaenur.easynote.ui.main.MainActivity;

import com.shakibaenur.easynote.util.AppConstraints;
import com.shakibaenur.easynote.util.SharedPrefUtil;
import com.shakibaenur.easynote.util.base.BaseActivity;

public class SplashActivity extends BaseActivity {
    ActivitySplashBinding mBinding;


    @Override
    public int setLayoutId() {
        checkFirstLogin();
        return R.layout.activity_splash;
    }

    @Override
    public int setDefaultTheme() {
        return R.style.AppTheme_NoActionBar;
    }

    @Override
    public void startUI() {
        mBinding = (ActivitySplashBinding) getViewDataBinding();
        mBinding.btnGetStarted.setOnClickListener(view -> {
            goNext();
        });
    }

    private void goNext() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void checkFirstLogin() {
        String isLogin = SharedPrefUtil.GET_PREFERENCE(getApplication(), AppConstraints.PreferenceData.IS_LOGIN);
        if (isLogin.equals(AppConstraints.PreferenceData.VALUE_POSITIVE_LOGIN)) {
            goNext();
        } else {
            SharedPrefUtil.ADD_PREFERENCE(getApplication(), AppConstraints.PreferenceData.IS_LOGIN, AppConstraints.PreferenceData.VALUE_POSITIVE_LOGIN);
        }
    }
}