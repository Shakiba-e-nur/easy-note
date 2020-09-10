package com.shakibaenur.easynote.ui.home;


import androidx.lifecycle.ViewModelProvider;

import com.shakibaenur.easynote.R;
import com.shakibaenur.easynote.util.base.BaseFragment;

public class HomeFragment extends BaseFragment {
HomeViewModel homeViewModel;

    @Override
    public int setLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void startUI() {
        homeViewModel = (HomeViewModel) getViewModel(HomeViewModel.class);
    }
}