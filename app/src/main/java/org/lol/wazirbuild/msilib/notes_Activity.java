package org.lol.wazirbuild.msilib;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import org.lol.wazirbuild.msilib.RecyclerViews.Notes_Recycler;

public class notes_Activity extends AppCompatActivity {
    RecyclerView notes_Recycler;
    Notes_Recycler NR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_);

        notes_Recycler=findViewById(R.id.notes_Recycler);
        NR=new Notes_Recycler(this);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this);
        ((LinearLayoutManager) layoutManager).setOrientation(RecyclerView.VERTICAL);
        DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(notes_Recycler.getContext(),((LinearLayoutManager) layoutManager).getOrientation());
        notes_Recycler.addItemDecoration(dividerItemDecoration);
        notes_Recycler.setLayoutManager(layoutManager);
        notes_Recycler.setAdapter(NR);
    }
}
