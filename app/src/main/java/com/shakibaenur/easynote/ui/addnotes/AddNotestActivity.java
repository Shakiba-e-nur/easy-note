package com.shakibaenur.easynote.ui.addnotes;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.shakibaenur.easynote.R;
import com.shakibaenur.easynote.databinding.ActivityAddNoteBinding;
import com.shakibaenur.easynote.util.base.BaseActivity;

public class AddNotestActivity extends BaseActivity {
    private AddNotesViewModel addNotesViewModel;
    private ActivityAddNoteBinding mBinding;

    @Override
    public int setLayoutId() {
        return R.layout.activity_add_note;
    }

    @Override
    public void startUI() {
        addNotesViewModel = (AddNotesViewModel) getViewModel(AddNotesViewModel.class);
        mBinding = (ActivityAddNoteBinding) getViewDataBinding();
        addNotesViewModel.setContext(mBinding.getRoot().getContext());
        // mBinding.setAddNotesViewModel(addNotesViewModel);

        mBinding.imageViewSchedules.setOnClickListener(view -> {
            addNotesViewModel.showDatePickerDialog(this);
        });
        mBinding.imageViewReminder.setOnClickListener(view ->
        {
            addNotesViewModel.showTimePickerDialog(this);
        });

        mBinding.floatingActionButtonAdd.setOnClickListener(view -> {
            addNotesViewModel.addNotes(mBinding.editTextTitle.getText().toString().trim(), mBinding.editTextDescription.getText().toString().trim());
        });
        mBinding.imgBack.setOnClickListener(view -> {
            finish();
        });
        setObservers();
    }

    private void setObservers() {
        addNotesViewModel.text_date.observe(this, s ->
        {
            mBinding.textDate.setText(s);
        });
        addNotesViewModel.text_time.observe(this, s ->
        {
            mBinding.textTime.setText(s);
        });

        addNotesViewModel.text_error_title.observe(this, s -> {
            mBinding.editTextTitle.setError(s);
        });
        addNotesViewModel.text_error_description.observe(this, s -> {
            mBinding.editTextDescription.setError(s);
        });
        addNotesViewModel.isRefreshed.observe(this, aBoolean -> {
            if (aBoolean) {
                refreshUi();
            }
        });
    }

    private void refreshUi() {
        resetViews(mBinding.editTextDescription, mBinding.editTextTitle, mBinding.textDate, mBinding.textTime);
    }


}
