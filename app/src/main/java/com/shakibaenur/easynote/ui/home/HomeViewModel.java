package com.shakibaenur.easynote.ui.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.shakibaenur.easynote.util.provider.room.database.NoteDatabase;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class HomeViewModel extends AndroidViewModel {
    public MutableLiveData<String> noteCount = new MutableLiveData<>();
    public MutableLiveData<String> reminderCount = new MutableLiveData<>();
    public MutableLiveData<String> scheduleCount = new MutableLiveData<>();
    public HomeViewModel(@NonNull Application application) {
        super(application);
        getCountData();
    }
    void getCountData()
    {
        NoteDatabase.getNoteDatabase(getApplication()).noteDao().getNotesCount()
                .subscribeOn(Schedulers.io()).subscribe(count -> {
            noteCount.postValue(""+count);
        });
        NoteDatabase.getNoteDatabase(getApplication()).noteDao().getAlarmCount()
                .subscribeOn(Schedulers.io()).subscribe(count -> {
            reminderCount.postValue(""+count);
        });
        NoteDatabase.getNoteDatabase(getApplication()).noteDao().getNotesCount()
                .subscribeOn(Schedulers.io()).subscribe(count -> {
            scheduleCount.postValue(""+count);
        });
    }
}
