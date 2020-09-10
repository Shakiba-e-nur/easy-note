package com.shakibaenur.easynote.util.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.shakibaenur.easynote.ui.home.HomeViewModel;

public abstract class BaseFragment extends Fragment {
    public abstract int setLayoutId();

    private ViewDataBinding viewDataBinding;

    public abstract void startUI();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewDataBinding = DataBindingUtil.inflate(inflater, setLayoutId(), container, false);
        startUI();
        return viewDataBinding.getRoot();
    }


    public ViewDataBinding getViewDataBinding() {
        return viewDataBinding;
    }

    public AndroidViewModel getViewModel(Class<? extends AndroidViewModel> viewmodel) {
        return ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(viewmodel);
    }

}
