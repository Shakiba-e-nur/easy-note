package com.shakibaenur.easynote.ui.main;

import com.shakibaenur.easynote.R;


import com.shakibaenur.easynote.util.base.BaseActivity;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends BaseActivity {
   com.shakibaenur.easynote.databinding.ActivityMainBinding mBinding;

    @Override
    public int setLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void startUI() {
        mBinding = (com.shakibaenur.easynote.databinding.ActivityMainBinding) getViewDataBinding();
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_note_list, R.id.navigation_favorites, R.id.navigation_schedules, R.id.navigation_settings)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
      //  NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(mBinding.navView, navController);
    }


}