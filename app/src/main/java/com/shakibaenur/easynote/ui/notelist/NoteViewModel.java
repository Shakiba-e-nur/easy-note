package com.shakibaenur.easynote.ui.notelist;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.shakibaenur.easynote.R;
import com.shakibaenur.easynote.ui.addnotes.AddNotestActivity;
import com.shakibaenur.easynote.util.AppConstraints;
import com.shakibaenur.easynote.util.SharedPrefUtil;
import com.shakibaenur.easynote.util.SwipeToDeleteCallback;
import com.shakibaenur.easynote.util.provider.room.database.NoteDatabase;
import com.shakibaenur.easynote.util.provider.room.model.Note;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class NoteViewModel extends AndroidViewModel {
    MutableLiveData<List<Note>> noteListLiveData = new MutableLiveData<>();
    MutableLiveData<Boolean> isShowConfirmationDialog = new MutableLiveData<>();

    public NoteViewModel(@NonNull Application application) {
        super(application);
        getNoteList();
    }

    public void goToAddNotes() {
        Intent intent = new Intent(getApplication(), AddNotestActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getApplication().startActivity(intent);
    }

    public void getNoteList() {
        NoteDatabase.getNoteDatabase(getApplication()).noteDao().getNotes().observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(notes -> {
            noteListLiveData.setValue(notes);
        });
    }

    public void showPreviewDialog(final Activity activity, Note note) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.dialog_preview);
        TextView date_text = dialog.findViewById(R.id.text_view_date);
        TextView month_text = dialog.findViewById(R.id.text_view_month);
        TextView text_title = dialog.findViewById(R.id.text_view_note_title);
        TextView text_description = dialog.findViewById(R.id.text_view_note_description);
        ImageView img_action_delete = dialog.findViewById(R.id.img_delete);
        ImageView img_action_edit = dialog.findViewById(R.id.img_edit);
        ImageView img_action_favorite = dialog.findViewById(R.id.img_favorite);
        ImageView img_close = dialog.findViewById(R.id.btn_close);

        //setup contents
        date_text.setText(note.getDateNumber());
        month_text.setText(note.getMonth());
        text_title.setText(note.getTitle());
        text_description.setText(note.getDescription());
        img_action_favorite.setImageResource(note.getFavoriteImageId());

        //click events
        img_action_edit.setOnClickListener(view -> {
            Intent intent = new Intent(activity, AddNotestActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(AppConstraints.IntentData.DATA_ID, note.getId());
            intent.putExtra(AppConstraints.IntentData.DATA_TITLE, note.getTitle());
            intent.putExtra(AppConstraints.IntentData.DATA_DESCRIPTION, note.getDescription());
            intent.putExtra(AppConstraints.IntentData.DATA_DATE, note.getDate());
            intent.putExtra(AppConstraints.IntentData.DATA_TIME, note.getTime());

            activity.startActivity(intent);
            dialog.dismiss();
        });
        img_close.setOnClickListener(view -> {
            dialog.dismiss();
        });
        img_action_delete.setOnClickListener(view -> {
            confirmationDialog(activity, dialog, note);

        });


        int width = (int) (activity.getResources().getDisplayMetrics().widthPixels * 0.90);
        int height = (int) (activity.getResources().getDisplayMetrics().heightPixels * 0.90);

        dialog.getWindow().setLayout(width, height);
        dialog.show();
    }

    private void confirmationDialog(Activity activity, Dialog dialogParent, Note note) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        //Setting message manually and performing action on button click
        builder.setMessage(R.string.dialog_message)
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Observable.fromCallable(() -> NoteDatabase.getNoteDatabase(getApplication())
                                .noteDao().deleteNote(note))
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribeOn(Schedulers.io()).subscribe(aLong -> {
                            dialog.dismiss();
                            dialogParent.dismiss();

                        });
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                       // dialogParent.dismiss();

                    }
                });

        AlertDialog alert = builder.create();

        alert.setTitle(R.string.dialog_title);
        alert.show();
    }

    public void enableSwipeToDeleteAndUndo(Activity activity, RecyclerView recyclerView) {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(activity) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                int position = viewHolder.getLayoutPosition();
                Observable.fromCallable(() -> NoteDatabase.getNoteDatabase(getApplication())
                        .noteDao().deleteNote(noteListLiveData.getValue().get(position - 1)))
                        .subscribeOn(Schedulers.io()).subscribe(aLong -> {

                });


            }
        };

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(recyclerView);
    }
}
