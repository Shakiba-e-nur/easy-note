package com.shakibaenur.easynote.util.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.shakibaenur.easynote.R;
import com.shakibaenur.easynote.util.AppConstraints;


public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    public abstract int setLayoutId();

    private ViewDataBinding viewDataBinding;

    public abstract void startUI();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(setDefaultTheme());
        super.onCreate(savedInstanceState);
        viewDataBinding = DataBindingUtil.setContentView(this, setLayoutId());
        startUI();
    }

    public int setDefaultTheme() {
        return R.style.AppTheme_NoActionBar;
    }

    public ViewDataBinding getViewDataBinding() {
        return viewDataBinding;
    }

    //setting up back functionality with custom view
    public void setBackPressViews(View... Views) {
        for (View v : Views) {
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
    }

    public AndroidViewModel getViewModel(Class<? extends AndroidViewModel> viewmodel) {
        return ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(viewmodel);
    }

    //setting up back functionality with custom view
    public void setClickListeners(View... Views) {
        for (View v : Views) {
            v.setOnClickListener(this);
        }
    }


    public void showLongToast(String msg) {
        Toast.makeText(getBaseContext(), msg, Toast.LENGTH_LONG).show();
    }

    public void showShortToast(String msg) {
        Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public void resetViews(View... Views) {
        for (View v : Views) {
            boolean b1 = v instanceof TextView ? true : false;
            if (b1 == true) {
                TextView textView = (TextView) v;

                textView.setText("");


            }
            boolean b2 = v instanceof EditText ? true : false;
            if (b2 == true) {
                EditText editText = (EditText) v;

                editText.setText("");


            }
            boolean b3 = v instanceof ImageView ? true : false;

            if (b3 == true) {
                ImageView imageView = (ImageView) v;

                imageView.setVisibility(View.GONE);

            }
        }
    }

    public boolean getValidation(String validationMsg, View... Views) {
        boolean flag = true;
        for (View v : Views) {
            boolean b1 = v instanceof TextView ? true : false;
            if (b1 == true) {
                TextView textView = (TextView) v;
                if (textView.getText().toString().trim().isEmpty()) {
                    textView.setError(validationMsg);
                    flag = false;

                }
            }
            boolean b2 = v instanceof EditText ? true : false;
            if (b2 == true) {
                EditText editText = (EditText) v;
                if (editText.getText().toString().trim().isEmpty()) {
                    editText.setError(validationMsg);
                    flag = false;

                }
            }
            boolean b3 = v instanceof ImageView ? true : false;

            if (b3 == true) {
                ImageView imageView = (ImageView) v;
                if (imageView.getVisibility() == View.GONE || imageView.getVisibility() == imageView.INVISIBLE) {
                    flag = false;
                }
            }
        }
        return flag;
    }

    public void printSuccessLog(String msg) {
        Log.d(AppConstraints.LogConstraints.COMMON_TAG, "Success: " + msg);
    }

    public void printFailedLog(Throwable t) {
        Log.d(AppConstraints.LogConstraints.COMMON_TAG, t.getMessage());
    }

    @Override
    public void onClick(View v) {

    }
}
