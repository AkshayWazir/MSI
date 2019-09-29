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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class mainScreen extends AppCompatActivity implements View.OnClickListener {
    private int currentPage = 0;
    private Timer timer;
    private final long DELAY_MS = 500;
    private final long PERIOD_MS = 4000;
    private ViewPager viewPager;
    private NewsFeedAdapter adapter;
    ConstraintLayout layout, layout1;

    private FirebaseAuth mAuth;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        mAuth = FirebaseAuth.getInstance();

        layout = findViewById(R.id.notes_start);
        layout1 = findViewById(R.id.cre_launch);

        viewPager = findViewById(R.id.news_feed);

        toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        // here i make use of the passed object of the user
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String jsonMyObject = extras.getString("STUDENT_OBJECT");
            Student myObject = new Gson().fromJson(jsonMyObject, Student.class);
            Toast.makeText(mainScreen.this, "Welcome " + myObject.getName(), Toast.LENGTH_LONG).show();
            Map<String, Object> news = (HashMap<String, Object>) getIntent().getSerializableExtra("FEED");
        }

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
        layout1.setOnClickListener(this);
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
                        Pair.create(findViewById(R.id.t2), "trans_notes"),
                        Pair.create(findViewById(R.id.frameLayout2), "trans_back"));
                startActivity(go, options.toBundle());
                break;
            case (R.id.cre_launch):
                Intent go1 = new Intent(mainScreen.this, Creation_Activity.class);
                ActivityOptions opt1 = ActivityOptions.makeSceneTransitionAnimation(mainScreen.this,
                        Pair.create(findViewById(R.id.textView5), "cre_title"),
                        Pair.create(findViewById(R.id.frameLayout4), "cre_back"));
                startActivity(go1, opt1.toBundle());
                break;
        }

    }
}
