package com.shakibaenur.easynote.ui.favorites;

import android.app.Activity;
import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.shakibaenur.easynote.util.SwipeToDeleteCallback;
import com.shakibaenur.easynote.util.provider.room.database.NoteDatabase;
import com.shakibaenur.easynote.util.provider.room.model.Note;

import java.util.List;

import io.reactivex.Observable;
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
    public void enableSwipeToDeleteAndUndo(Activity activity, RecyclerView recyclerView) {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(activity) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                int position = viewHolder.getLayoutPosition();
                Observable.fromCallable(() -> NoteDatabase.getNoteDatabase(getApplication())
                        .noteDao().deleteNote(noteListLiveData.getValue().get(position-1)))
                        .subscribeOn(Schedulers.io()).subscribe(aLong -> {

                });


            }
        };

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(recyclerView);
    }
}
