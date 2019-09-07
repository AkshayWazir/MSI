package org.lol.wazirbuild.msilib.Database.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.lol.wazirbuild.msilib.Database.Notes_repository;
import org.lol.wazirbuild.msilib.Database.model.notes_category_model;

import java.util.List;

public class notes_category_viewModel extends AndroidViewModel {

    public LiveData<List<notes_category_model>> viewModelList;
    public Notes_repository repository;

    public notes_category_viewModel(@NonNull Application application) {
        super(application);
        repository = Notes_repository.getInstance(application.getApplicationContext());
        viewModelList = repository.listLiveData;
    }

    public void InsertNewData(List<notes_category_model> newList) {
        repository.InsertNewData(newList);
    }

    public void InsertSingle(notes_category_model newList) {
        repository.InsertSingle(newList);
    }
}
