package com.shakibaenur.easynote.ui.favorites;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.shakibaenur.easynote.util.provider.room.database.NoteDatabase;
import com.shakibaenur.easynote.util.provider.room.model.Note;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class FavouritesViewModel extends AndroidViewModel {
    MutableLiveData<List<Note>> noteListLiveData = new MutableLiveData<>();

    public FavouritesViewModel(@NonNull Application application) {
        super(application);
        getNoteList();
    }

    public void getNoteList() {
        NoteDatabase.getNoteDatabase(getApplication()).noteDao().getFavouriteItems().observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(notes -> {
            noteListLiveData.setValue(notes);
        });
    }
}
