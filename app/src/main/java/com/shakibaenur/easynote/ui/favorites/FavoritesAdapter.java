package com.shakibaenur.easynote.ui.favorites;

import android.app.Application;
import android.view.ViewGroup;

import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProvider;

import com.shakibaenur.easynote.R;
import com.shakibaenur.easynote.databinding.ItemFavoriteListBinding;
import com.shakibaenur.easynote.databinding.ListItemBinding;
import com.shakibaenur.easynote.ui.favorites.item.FavoriteListItemViewModel;
import com.shakibaenur.easynote.ui.notelist.listitem.ListItemViewModel;
import com.shakibaenur.easynote.util.base.BaseAdapter;
import com.shakibaenur.easynote.util.base.BaseViewHolder;
import com.shakibaenur.easynote.util.provider.room.model.Note;

public class FavoritesAdapter extends BaseAdapter<Note> {
private Application application;

    public FavoritesAdapter(Application application) {
        this.application = application;
    }

    @Override
    public boolean isEqual(Note left, Note right) {
        return false;
    }

    @Override
    public BaseViewHolder<Note> newViewHolder(ViewGroup parent, int viewType) {
        return new NoteListAdapterViewHolder(inflate(parent, R.layout.item_favorite_list));
    }

    private class NoteListAdapterViewHolder extends BaseAdapterViewHolder<Note> {
    private ItemFavoriteListBinding  mItemBinding;
        public NoteListAdapterViewHolder(ViewDataBinding viewDataBinding) {
            super(viewDataBinding);
            mItemBinding = (ItemFavoriteListBinding) getViewDataBinding();
        }

        @Override
        public void bind(Note item) {
            FavoriteListItemViewModel listItemViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(application).create(FavoriteListItemViewModel.class);
            mItemBinding.setListItemViewModel(listItemViewModel);
            listItemViewModel.noteMutableLiveData.setValue(item);

        }
    }
}
