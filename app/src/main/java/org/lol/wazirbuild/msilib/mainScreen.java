package org.lol.wazirbuild.msilib;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class mainScreen extends AppCompatActivity {
    private FirebaseAuth mAuth;
    FirebaseUser currentUser;
    ImageView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
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
        startActivity(new Intent(mainScreen.this,notes_Activity.class));

    }
}
