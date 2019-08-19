package org.lol.wazirbuild.msilib;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;

public class mainScreen extends AppCompatActivity {

    private FirebaseAuth mAuth;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        mAuth = FirebaseAuth.getInstance();
        toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        Fragment fragment = new MainScreen_Frag();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.main_container, fragment);
        ft.commit();

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
    //--------------------------------------------------------------------------------
    //OnClickListeners

    void onClickNotes(View view){
        ActivityOptions options=ActivityOptions
                .makeSceneTransitionAnimation(mainScreen.this,noteTitleImage,"main_to_notes");
        startActivity(new Intent(mainScreen.this,Notes_Activity.class),options.toBundle());
    }
}
