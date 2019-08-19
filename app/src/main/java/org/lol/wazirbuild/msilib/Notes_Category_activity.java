package org.lol.wazirbuild.msilib;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import org.lol.wazirbuild.msilib.RecyclerViews.notes_categoryRecycler;

public class Notes_Category_activity extends AppCompatActivity {

    RecyclerView recyclerView;
    notes_categoryRecycler NCR;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notes_category_activity);
        getSupportActionBar().hide();

        recyclerView = findViewById(R.id.category_notes_Recycler);
        NCR = new notes_categoryRecycler(this);
        RecyclerView.LayoutManager l = new LinearLayoutManager(this);
        ((LinearLayoutManager) l).setOrientation(RecyclerView.VERTICAL);

        recyclerView.setLayoutManager(l);
        recyclerView.setAdapter(NCR);
        recyclerView.getAdapter().notifyDataSetChanged();
    }

}
