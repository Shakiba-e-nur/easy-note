package com.shakibaenur.easynote.ui.notelist;

import android.app.Application;
import android.view.ViewGroup;

import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProvider;

import com.shakibaenur.easynote.R;
import com.shakibaenur.easynote.databinding.ListItemBinding;
import com.shakibaenur.easynote.ui.notelist.listitem.ListItemViewModel;
import com.shakibaenur.easynote.util.base.BaseAdapter;
import com.shakibaenur.easynote.util.base.BaseViewHolder;
import com.shakibaenur.easynote.util.provider.room.model.Note;

public class NoteListAdapter extends BaseAdapter<Note> {
private Application application;

    public NoteListAdapter(Application application) {
        this.application = application;
    }

    @Override
    public boolean isEqual(Note left, Note right) {
        return false;
    }

    @Override
    public BaseViewHolder<Note> newViewHolder(ViewGroup parent, int viewType) {
        return new NoteListAdapterViewHolder(inflate(parent, R.layout.list_item));
    }

    private class NoteListAdapterViewHolder extends BaseAdapterViewHolder<Note> {
    private ListItemBinding mItemBinding;
        public NoteListAdapterViewHolder(ViewDataBinding viewDataBinding) {
            super(viewDataBinding);
            mItemBinding = (ListItemBinding) getViewDataBinding();
        }

        @Override
        public void bind(Note item) {
            ListItemViewModel listItemViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(application).create(ListItemViewModel.class);
            mItemBinding.setListItemViewModel(listItemViewModel);
            listItemViewModel.noteMutableLiveData.setValue(item);
            //Drawable drawable = mItemBinding.getRoot().getContext().getDrawable(R.drawable.ic_pink_favorite);
            //mItemBinding.imageViewFavorite.setImageResource(R.drawable.ic_pink_favorite);
        }
    }
}
