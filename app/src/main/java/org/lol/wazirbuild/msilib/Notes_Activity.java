package org.lol.wazirbuild.msilib;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.lol.wazirbuild.msilib.RecyclerViews.Notes_Recycler;

public class Notes_Activity extends AppCompatActivity {

    RecyclerView recyclerView;
    Notes_Recycler NR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_);

        recyclerView = findViewById(R.id.notes_recycler);
        NR = new Notes_Recycler(this);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        ((LinearLayoutManager) manager).setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(NR);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
    }

}
