package org.lol.wazirbuild.msilib;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowAnimationFrameStats;
import android.view.WindowManager;

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
        notes_Recycler.setLayoutManager(layoutManager);
        notes_Recycler.setAdapter(NR);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.main_screen_menu,menu);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                finish();
        }
        return true;
    }
}
