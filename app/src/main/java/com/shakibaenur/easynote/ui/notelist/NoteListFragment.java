package com.shakibaenur.easynote.ui.notelist;


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
            noteViewModel.showPreviewDialog(getActivity(),item);
        });
        setObservers();
    }


    private void setObservers() {
        noteViewModel.noteListLiveData.observe(this, notes -> {
            noteListAdapter.clear();
            noteListAdapter.addItems(notes);
        });
    }
}