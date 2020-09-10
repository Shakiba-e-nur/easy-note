package com.shakibaenur.easynote.util.provider.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.shakibaenur.easynote.util.provider.room.model.Note;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface NoteDao {
    @Insert
    long insertNote(Note note);

    @Update
    int updateNote(Note note);

    @Query("SELECT * from note_table")
    Flowable<List<Note>> getNotes();

    @Delete
    int deleteNote(Note note);

    @Query("SELECT COUNT(id) from note_table")
    Flowable<Integer> getNotesCount();

    @Query("SELECT COUNT(id) from note_table WHERE time NOT NULL")
    Flowable<Integer> getAlarmCount();

    @Query("SELECT * from note_table WHERE isFavourite = 1")
    Flowable<List<Note>> getFavouriteItems();
}
