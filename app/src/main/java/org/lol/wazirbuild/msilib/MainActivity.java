package org.lol.wazirbuild.msilib;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    FrameLayout signInBtn;
    TextView sigInText;
    ProgressBar bar;
    String email;
    TextInputLayout EuserId, password;
    private FirebaseAuth mAuth;
    int sw, sh; //width of SignIn btn
    private FirebaseFirestore db;
    private DocumentReference dr1, dr2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.EuserId = findViewById(R.id.user_id);
        this.password = findViewById(R.id.user_password);
        this.signInBtn = findViewById(R.id.signinbtn);
        this.sigInText = findViewById(R.id.signinText);
        this.bar = findViewById(R.id.progressBar);
        this.mAuth = FirebaseAuth.getInstance();
        this.db = FirebaseFirestore.getInstance();
        permissionCheck();
        sw = signInBtn.getWidth();
        sh = signInBtn.getHeight();
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                load(v);
            }
        });
    }

    private boolean validateEnrollment() {
        String email = EuserId.getEditText().getText().toString().trim();
        if (email.isEmpty()) {
            EuserId.setError("Field Cant be empty");
            return false;
        } else {
            EuserId.setError(null);
            return true;
        }
    }

    private boolean validatePassword() {
        String pass = password.getEditText().getText().toString();
        if (pass.isEmpty()) {
            password.setError("Field Cant be empty");
            return false;
        } else {
            password.setError(null);
            Toast.makeText(MainActivity.this, "password Verified", Toast.LENGTH_LONG).show();
            return true;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String rollNum = Objects.requireNonNull(currentUser).getEmail();
            setInstanceOfUser(rollNum);
        }
    }

    public void load(View view) {
        if (!validateEnrollment() | !validatePassword()) {
            Toast.makeText(MainActivity.this, "Unauthorised", Toast.LENGTH_LONG).show();
            return;
        }
        email = EuserId.getEditText().getText().toString().trim();
        String pass = password.getEditText().getText().toString();
        rev_or_hid(true);
        mAuth.signInWithEmailAndPassword(email + "@msit.com", pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    setInstanceOfUser(email);
                } else {
                    rev_or_hid(false);
                    Toast.makeText(MainActivity.this, "Failed to LogIn", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void setInstanceOfUser(String number) {
        StringBuilder year_string = new StringBuilder();
        StringBuilder course_string = new StringBuilder();
        StringBuilder college_string = new StringBuilder();
        StringBuilder roll_string = new StringBuilder();
        int i;
        for (i = 0; i <= 2; i++) {
            roll_string.append(number.charAt(i));
        }
        for (i = 3; i <= 6; i++) {
            college_string.append(number.charAt(i));
        }
        for (i = 7; i <= 8; i++) {
            course_string.append(number.charAt(i));
        }
        for (i = 9; i <= 10; i++) {
            year_string.append(number.charAt(i));
        }
        final Intent intent = new Intent(MainActivity.this, mainScreen.class);
        dr1 = db
                .collection(year_string.toString())// here is the year
                .document(college_string.toString()) // here is the college
                .collection(course_string.toString())//  here is the course
                .document(roll_string.toString());//  here is the roll number
        dr2 = db
                .collection(year_string.toString())
                .document(college_string.toString())
                .collection(course_string.toString())
                .document("NEWS_FEED");


        dr1
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Student obj = documentSnapshot.toObject(Student.class);
                        intent.putExtra("STUDENT_OBJECT", new Gson().toJson(obj));
                    }
                });
        dr2
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot snapshot = task.getResult();
                        if (task.isSuccessful()) {
                            HashMap<String, Object> obj1 = (HashMap<String, Object>) snapshot.getData();
                            intent.putExtra("FEEDS", obj1);
                        }
                    }
                });
        startActivity(intent);
        finish();
    }

    private void rev_or_hid(boolean res) {
        if (res) {
            sigInText.animate().alpha(0f).setDuration(500);
            bar.animate().alpha(1).setDuration(500);
            bar.setVisibility(View.VISIBLE);
        } else {
            bar.animate().alpha(0).setDuration(500);
            sigInText.animate().alpha(1f).setDuration(500);
        }

    }

    public void register(View view) {
        startActivity(new Intent(MainActivity.this, RegistrationScreen.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            startActivity(new Intent(this, mainScreen.class));
            finish();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 123:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    return;
                } else {
                    Toast.makeText(this, "Permission Denied for" + permissions, Toast.LENGTH_SHORT).show();
                    permissionCheck();
                }

            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void permissionCheck() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 123);
                return;
            }

        }
    }


}
