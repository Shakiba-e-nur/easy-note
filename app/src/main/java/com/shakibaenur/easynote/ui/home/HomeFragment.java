package com.shakibaenur.easynote.ui.home;


import androidx.lifecycle.ViewModelProvider;

import com.shakibaenur.easynote.R;
import com.shakibaenur.easynote.databinding.FragmentHomeBinding;
import com.shakibaenur.easynote.util.base.BaseFragment;

public class HomeFragment extends BaseFragment {
    HomeViewModel homeViewModel;
    private FragmentHomeBinding mBinding;

    @Override
    public int setLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void startUI() {
        mBinding = (FragmentHomeBinding) getViewDataBinding();
        homeViewModel = (HomeViewModel) getViewModel(HomeViewModel.class);
        mBinding.setHomeViewModel(homeViewModel);
        setObservers();
    }

    private void setObservers() {
        homeViewModel.noteCount.observe(this,countText -> {
            mBinding.textViewNotesCount.setText(countText);
        });
        homeViewModel.reminderCount.observe(this,countText -> {
            mBinding.textViewReminderCount.setText(countText);
        });
        homeViewModel.scheduleCount.observe(this,countText -> {
            mBinding.textViewSchedulesCount.setText(countText);
        });
    }


}