package org.lol.wazirbuild.msilib;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Timer;
import java.util.TimerTask;

public class mainScreen extends AppCompatActivity implements View.OnClickListener {
    private int currentPage = 0;
    private Timer timer;
    private final long DELAY_MS = 500;
    private final long PERIOD_MS = 4000;
    private ViewPager viewPager;
    private NewsFeedAdapter adapter;
    ConstraintLayout layout;
    TextView t_view;
    FrameLayout fl_fo_trans;

    private FirebaseAuth mAuth;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        mAuth = FirebaseAuth.getInstance();
        toolbar = findViewById(R.id.tool_bar);
        t_view = findViewById(R.id.t2);
        fl_fo_trans = findViewById(R.id.frameLayout2);
        layout = findViewById(R.id.notes_start);
        viewPager = findViewById(R.id.news_feed);
        setSupportActionBar(toolbar);
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == 3) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
            }
        };

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);
        adapter = new NewsFeedAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        layout.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_screen_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case (R.id.signout):
                mAuth.signOut();
                startActivity(new Intent(mainScreen.this, MainActivity.class));
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.notes_start):
                Intent go = new Intent(mainScreen.this, Notes_Activity.class);
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(mainScreen.this,
                        new Pair[]{Pair.create(t_view, "trans_notes"), Pair.create(fl_fo_trans, "trans_back")});
                startActivity(go, options.toBundle());
                break;
        }

    }
}
