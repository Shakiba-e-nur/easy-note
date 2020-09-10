package com.shakibaenur.easynote.ui.addnotes;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.databinding.library.baseAdapters.BR;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.shakibaenur.easynote.R;
import com.shakibaenur.easynote.util.AppConstraints;
import com.shakibaenur.easynote.util.provider.room.database.NoteDatabase;
import com.shakibaenur.easynote.util.provider.room.model.Note;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AddNotesViewModel extends AndroidViewModel {
    private Application application;
    private Context context;

    public MutableLiveData<String> text_error_title = new MutableLiveData<>();
    public MutableLiveData<String> text_error_description = new MutableLiveData<>();
    public MutableLiveData<String> text_date = new MutableLiveData<>();
    public MutableLiveData<String> text_time = new MutableLiveData<>();
    public MutableLiveData<Boolean> isRefreshed = new MutableLiveData<>();

    public AddNotesViewModel(@NonNull Application application) {
        super(application);
        this.application = application;

    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void goBack() {
        if (context != null) {
            ((Activity) context).finish();
        }
    }

    public void addNotes(String notetitle, String description) {
        Note note = new Note();
        note.setTitle(notetitle);
        note.setDescription(description);
        note.setDate(text_date.getValue());
        note.setTime(text_time.getValue());

        Log.d(AppConstraints.LogConstraints.COMMON_TAG, "Title:" + note.getTitle());
        if (getValidation(note)) {
            insertNote(note);
        } else {
            Log.d(AppConstraints.LogConstraints.COMMON_TAG, "data not valid!");
        }

    }

    private boolean getValidation(Note note) {
        boolean isValid = true;
        if (!note.isTiteValid()) {
            text_error_title.setValue(AppConstraints.ErrorMessage.ERROR_NOTE_TITLE);
            isValid = false;

        } else {
            text_error_title.setValue(null);
        }
        if (!note.isDescriptionValid()) {
            text_error_description.setValue(AppConstraints.ErrorMessage.ERROR_NOTE_DESCRIPTION);
            isValid = false;
        } else {
            text_error_description.setValue(null);
        }
        return isValid;
    }

    public void showDatePickerDialog(final Activity activity) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.dialog_date_picker);
        DatePicker datePicker = dialog.findViewById(R.id.date_picker);
        datePicker.setOnDateChangedListener((datePicker1, i, i1, i2) -> {

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy");
            Calendar calendar = Calendar.getInstance();
            calendar.set(datePicker1.getYear(), datePicker1.getMonth(), datePicker1.getDayOfMonth());
            String dateString = sdf.format(calendar.getTime());

            text_date.setValue(dateString);
            dialog.dismiss();
        });

        dialog.show();
    }

    public void showTimePickerDialog(final Activity activity) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.dialog_time_picker);
        TimePicker timePicker = dialog.findViewById(R.id.time_picker);
        timePicker.setOnTimeChangedListener((timePicker1, hour, minute) ->
        {
            text_time.setValue(hour + ":" + minute);
            dialog.dismiss();

        });
        dialog.show();
    }

    void insertNote(Note note) {
        Observable.fromCallable(() -> NoteDatabase.getNoteDatabase(getApplication())
                .noteDao().insertNote(note))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(aLong -> {
            if (aLong > 0) {
                isRefreshed.setValue(true);
            } else {
                isRefreshed.setValue(false);
            }

        });

    }


}