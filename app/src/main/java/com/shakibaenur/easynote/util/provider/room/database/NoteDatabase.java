package com.shakibaenur.easynote.util.provider.room.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.shakibaenur.easynote.util.provider.room.dao.NoteDao;
import com.shakibaenur.easynote.util.provider.room.model.Note;

@Database(entities = {Note.class}, version = 1)
public abstract class NoteDatabase extends RoomDatabase {
    public abstract NoteDao noteDao();
    private static volatile NoteDatabase noteDatabase;
    public static NoteDatabase getNoteDatabase(final Context context) {
        if (noteDatabase == null) {
            synchronized (NoteDatabase.class) {
                if (noteDatabase == null) {
                    noteDatabase = Room.databaseBuilder(context.getApplicationContext(), NoteDatabase.class, "note_table").build();
                }
            }
        }
        return noteDatabase;
    }



}
