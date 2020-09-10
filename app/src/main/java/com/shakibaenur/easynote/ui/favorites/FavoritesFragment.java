package com.shakibaenur.easynote.ui.favorites;



import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.shakibaenur.easynote.R;
import com.shakibaenur.easynote.databinding.FragmentFavoritesBinding;
import com.shakibaenur.easynote.util.base.BaseFragment;

public class FavoritesFragment extends BaseFragment {
FragmentFavoritesBinding mBinding;
FavouritesViewModel favouritesViewModel;
FavoritesAdapter mAdapter;
    @Override
    public int setLayoutId() {
        return R.layout.fragment_favorites;
    }

    @Override
    public void startUI() {
        mBinding = (FragmentFavoritesBinding) getViewDataBinding();
        favouritesViewModel = (FavouritesViewModel) getViewModel(FavouritesViewModel.class);
        mAdapter = new FavoritesAdapter(getActivity().getApplication());
        mBinding.recycleViewNoteList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mBinding.recycleViewNoteList.setAdapter(mAdapter);
        mBinding.editTextSearch.setOnClickListener(view -> {
            mBinding.editTextSearch.onActionViewExpanded();
        });
        //active search
        mBinding.editTextSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);
                return false;
            }
        });
        //enable swipe delete
        favouritesViewModel.enableSwipeToDeleteAndUndo(getActivity(),mBinding.recycleViewNoteList);
        setObservers();
    }

    private void setObservers() {
        favouritesViewModel.noteListLiveData.observe(this,notes -> {
            mAdapter.clear();
            mAdapter.addItems(notes);
        });
    }
}