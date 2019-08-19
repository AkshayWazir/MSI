package org.lol.wazirbuild.msilib;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import org.lol.wazirbuild.msilib.RecyclerViews.Notes_Recycler;

public class Notes_Activity extends AppCompatActivity {

    RecyclerView recyclerView;
    Notes_Recycler NR;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_);
        getSupportActionBar().hide();

        recyclerView=findViewById(R.id.notes_recycler);
        NR=new Notes_Recycler(this);
        RecyclerView.LayoutManager manager=new LinearLayoutManager(this);
        ((LinearLayoutManager) manager).setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(NR);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
    }

}
