package com.shakibaenur.easynote.util.provider.room.model;

import android.util.Log;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.shakibaenur.easynote.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@Entity(tableName = "note_table")
public class Note {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String description;
    private String date;
    private String time;
    private boolean isFavourite;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getFavoriteImageId() {
        if (isFavourite) {
            return R.drawable.ic_pink_favorite;
        } else {
            return R.drawable.ic_favorite;
        }
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMonth() {
        String month = "Jan";
        if (date != null) {
            String[] dateString = date.split("/");
            month = dateString[1];
        }

        return month;
    }
    public String getDateNumber() {
        String month = "1";
        if (date != null) {
            String[] dateString = date.split("/");
            month = dateString[0];
        }

        return month;
    }

//    public int getColorValue()
//    {
//        int index = randInt()
//    }
    public static int randInt(int min, int max) {


        Random rand=new Random();

        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }
    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }

    public boolean isTiteValid() {
        boolean isValid = true;
        if (title != null) {
            if (title.isEmpty()) {
                isValid = false;
            }
        } else {
            isValid = false;
        }
        Log.d("chktitle", "+" + title + ":" + isValid);
        return isValid;
    }

    public boolean isDescriptionValid() {
        boolean isValid = true;
        if (description != null) {
            if (description.isEmpty()) {
                isValid = false;
            }
        } else {
            isValid = false;
        }
        Log.d("chktitle", "+" + description + ":" + isValid);
        return isValid;
    }
}
