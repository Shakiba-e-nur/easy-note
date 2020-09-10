package com.shakibaenur.easynote.ui.addnotes;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.shakibaenur.easynote.R;
import com.shakibaenur.easynote.databinding.ActivityAddNoteBinding;
import com.shakibaenur.easynote.util.AppConstraints;
import com.shakibaenur.easynote.util.base.BaseActivity;
import com.shakibaenur.easynote.util.provider.room.model.Note;

public class AddNotestActivity extends BaseActivity {
    private AddNotesViewModel addNotesViewModel;
    private ActivityAddNoteBinding mBinding;
    private Boolean editable = false;
    private int noteId;

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
            if (!editable) {
                addNotesViewModel.addNotes(mBinding.editTextTitle.getText().toString().trim(), mBinding.editTextDescription.getText().toString().trim());
            } else {
                updateNote();
            }
        });
        mBinding.imgBack.setOnClickListener(view -> {
            finish();
        });
        Intent intent = getIntent();
        noteId = intent.getIntExtra(AppConstraints.IntentData.DATA_ID, 0);
        setObservers();
        if (noteId != 0) {
            editable = true;
            String description = intent.getStringExtra(AppConstraints.IntentData.DATA_DESCRIPTION);
            String date = intent.getStringExtra(AppConstraints.IntentData.DATA_DATE);
            String time = intent.getStringExtra(AppConstraints.IntentData.DATA_TIME);
            String title = intent.getStringExtra(AppConstraints.IntentData.DATA_TITLE);
            mBinding.editTextTitle.setText(title);
            mBinding.editTextDescription.setText(description);
            addNotesViewModel.text_time.postValue(time);
            addNotesViewModel.text_date.postValue(date);
        }

    }

    private void updateNote() {
        Note note = new Note();
        note.setId(noteId);
        note.setTitle(mBinding.editTextTitle.getText().toString().trim());
        note.setDate(mBinding.textDate.getText().toString().trim());
        note.setDescription(mBinding.editTextDescription.getText().toString().trim());
        note.setTime(mBinding.textTime.getText().toString().trim());
        addNotesViewModel.updateNote(this,note);
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
