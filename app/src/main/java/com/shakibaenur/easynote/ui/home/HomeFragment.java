package com.shakibaenur.easynote.ui.home;


import android.view.inputmethod.EditorInfo;

import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.shakibaenur.easynote.R;
import com.shakibaenur.easynote.databinding.FragmentHomeBinding;
import com.shakibaenur.easynote.ui.notelist.NoteListAdapter;
import com.shakibaenur.easynote.util.base.BaseFragment;

public class HomeFragment extends BaseFragment {
    HomeViewModel homeViewModel;
    private FragmentHomeBinding mBinding;
    private HomeAdapter noteListAdapter;

    @Override
    public int setLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void startUI() {
        mBinding = (FragmentHomeBinding) getViewDataBinding();
        homeViewModel = (HomeViewModel) getViewModel(HomeViewModel.class);
        mBinding.setHomeViewModel(homeViewModel);

        noteListAdapter = new HomeAdapter(getActivity().getApplication());
        mBinding.notesRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.notesRecyclerview.setAdapter(noteListAdapter);

        noteListAdapter.setItemClickListener((view, item) -> {
            homeViewModel.showPreviewDialog(getActivity(), item);
        });
        mBinding.editTextSearch.setOnClickListener(view -> {
            mBinding.editTextSearch.onActionViewExpanded();
        });
        //enable search filter
        mBinding.editTextSearch.setImeOptions(EditorInfo.IME_ACTION_DONE);
        mBinding.editTextSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                noteListAdapter.getFilter().filter(newText);
                return false;
            }
        });

        //enable swipe delete
        homeViewModel.enableSwipeToDeleteAndUndo(getActivity(), mBinding.notesRecyclerview);

        setObservers();
    }

    private void setObservers() {
        homeViewModel.noteCount.observe(this, countText -> {
            mBinding.textViewNotesCount.setText(countText);
        });
        homeViewModel.reminderCount.observe(this, countText -> {
            mBinding.textViewReminderCount.setText(countText);
        });
        homeViewModel.scheduleCount.observe(this, countText -> {
            mBinding.textViewSchedulesCount.setText(countText);
        });
        homeViewModel.noteListLiveData.observe(this, notes -> {
            noteListAdapter.clear();
            noteListAdapter.addItems(notes);
        });
    }


}