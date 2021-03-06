package com.shakibaenur.easynote.ui.favorites;

import android.app.Application;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

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

import java.util.ArrayList;
import java.util.List;

public class FavoritesAdapter extends BaseAdapter<Note> implements Filterable {
    private Application application;
    private List<Note> tempNotes;

    public FavoritesAdapter(Application application) {
        this.application = application;
        tempNotes = new ArrayList<>();
    }

    @Override
    public void addItems(List<Note> items) {
        super.addItems(items);
        tempNotes.clear();
        tempNotes.addAll(items);
    }

    @Override
    public boolean isEqual(Note left, Note right) {
        return false;
    }

    @Override
    public BaseViewHolder<Note> newViewHolder(ViewGroup parent, int viewType) {
        return new NoteListAdapterViewHolder(inflate(parent, R.layout.item_favorite_list));
    }

    @Override
    public Filter getFilter() {
        return noteDataFilter;
    }

    private class NoteListAdapterViewHolder extends BaseAdapterViewHolder<Note> {
        private ItemFavoriteListBinding mItemBinding;

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

    private Filter noteDataFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Note> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(tempNotes);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Note item : getItems()) {
                    if (item.getTitle().toLowerCase().contains(filterPattern) || item.getDescription().toLowerCase().contains(filterPattern) || item.getDate().toLowerCase().contains(filterPattern) || item.getTime().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            getItems().clear();
            getItems().addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };
}
