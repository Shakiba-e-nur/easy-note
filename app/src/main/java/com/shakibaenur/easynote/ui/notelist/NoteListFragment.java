package com.shakibaenur.easynote.ui.notelist;


import android.view.inputmethod.EditorInfo;

import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.shakibaenur.easynote.R;
import com.shakibaenur.easynote.databinding.FragmentNoteListBinding;
import com.shakibaenur.easynote.util.base.BaseFragment;


public class NoteListFragment extends BaseFragment {
    NoteViewModel noteViewModel;
    FragmentNoteListBinding mBinding;
    private NoteListAdapter noteListAdapter;

    @Override
    public int setLayoutId() {
        return R.layout.fragment_note_list;
    }

    @Override
    public void startUI() {
        mBinding = (FragmentNoteListBinding) getViewDataBinding();
        noteViewModel = (NoteViewModel) getViewModel(NoteViewModel.class);
        mBinding.setNoteViewModel(noteViewModel);
        mBinding.recycleViewNoteList.setLayoutManager(new LinearLayoutManager(getActivity()));

        noteListAdapter = new NoteListAdapter(getActivity().getApplication());
        mBinding.recycleViewNoteList.setAdapter(noteListAdapter);

        noteListAdapter.setItemClickListener((view, item) -> {
            noteViewModel.showPreviewDialog(getActivity(), item);
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
        noteViewModel.enableSwipeToDeleteAndUndo(getActivity(),mBinding.recycleViewNoteList);
        setObservers();
    }


    private void setObservers() {
        noteViewModel.noteListLiveData.observe(this, notes -> {
            noteListAdapter.clear();
            noteListAdapter.addItems(notes);
        });
    }
}