package org.lol.wazirbuild.msilib.Database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import org.lol.wazirbuild.msilib.Database.model.notes_category_model;

import java.util.List;

public class Notes_repository {

    public LiveData<List<notes_category_model>> listLiveData;
    private notes_category_database database;
    private static Notes_repository ourInstance;

    public static Notes_repository getInstance(Context context) {
        ourInstance = new Notes_repository(context);
        return ourInstance;
    }

    public Notes_repository(Context context) {
        database = notes_category_database.getInstance(context);
        listLiveData = database.notesdao().getAllNotes();
    }

    public void InsertNewData(List<notes_category_model> List) {
        InsertData insert = new InsertData(List);
        insert.execute();
    }

    public void InsertSingle(notes_category_model newList) {
        InsertData insert = new InsertData(newList);
        insert.execute();
    }

    public class InsertData extends AsyncTask<Void, Void, Void> {
        List<notes_category_model> newList;
        notes_category_model model;

        public InsertData(notes_category_model model) {
            this.model = model;
        }

        public InsertData(List<notes_category_model> newList) {
            this.newList = newList;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if (model != null)
                database.notesdao().insertNote(model);
            else
                database.notesdao().InsertAll(newList);
            return null;
        }
    }
}
