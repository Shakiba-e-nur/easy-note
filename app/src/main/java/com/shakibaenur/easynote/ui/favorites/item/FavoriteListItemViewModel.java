package com.shakibaenur.easynote.ui.favorites.item;

import android.app.Application;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.shakibaenur.easynote.util.provider.room.database.NoteDatabase;
import com.shakibaenur.easynote.util.provider.room.model.Note;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class FavoriteListItemViewModel extends AndroidViewModel {
    public MutableLiveData<Note> noteMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<Drawable> favorite = new MutableLiveData<>();

    public FavoriteListItemViewModel(@NonNull Application application) {
        super(application);
    }

    public void addToFavorite() {
        Note note = noteMutableLiveData.getValue();
        note.setFavourite(!note.isFavourite());
        updateNote(note);

    }

    private void updateNote(Note note) {
        Observable.fromCallable(() -> NoteDatabase.getNoteDatabase(getApplication())
                .noteDao().updateNote(note))
                .subscribeOn(Schedulers.io()).subscribe(aLong -> {

        });
    }

}
