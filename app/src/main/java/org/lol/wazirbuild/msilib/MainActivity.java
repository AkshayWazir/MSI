package org.lol.wazirbuild.msilib;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    FrameLayout signInBtn;
    TextView sigInText;
    ProgressBar bar;
    View revealView;
    TextInputLayout EuserId, password;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("MSI Library");
        this.EuserId = findViewById(R.id.user_id);
        this.password = findViewById(R.id.user_password);
        this.signInBtn = findViewById(R.id.signinbtn);
        this.sigInText = findViewById(R.id.signinText);
        this.bar = findViewById(R.id.progressBar);
        this.revealView = findViewById(R.id.revealView);
        mAuth = FirebaseAuth.getInstance();
        permissionCheck();
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
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {
        if (currentUser != null) {
            startActivity(new Intent(MainActivity.this, mainScreen.class));
            finish();
        }
    }

    public void load(View view) {
        if (!validateEnrollment() | !validatePassword()) {
            Toast.makeText(MainActivity.this, "Unauthorised", Toast.LENGTH_LONG).show();
            return;
        }
        String email = EuserId.getEditText().getText().toString().trim();
        String pass = password.getEditText().getText().toString();
        email += "@msit.com";

        mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    animateButtonWidth();
                    fadeoutTextAndSetProgressDialogBarVisible();
                    nextAction();
                } else {
                    Toast.makeText(MainActivity.this, "Failed to LogIn", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void animateButtonWidth() {
        ValueAnimator anim = ValueAnimator.ofInt(signInBtn.getMeasuredWidth(), getFinalWidth());
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (Integer) animation.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = signInBtn.getLayoutParams();
                layoutParams.width = value;
                signInBtn.requestLayout();
            }
        });
        anim.setDuration(350);
        anim.start();
    }

    private void fadeoutTextAndSetProgressDialogBarVisible() {
        sigInText.animate().alpha(0f).setDuration(250).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                showProgressDialog();
            }
        }).start();
    }

    private void showProgressDialog() {
        bar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#0000ff"), PorterDuff.Mode.SRC_IN);
        bar.setVisibility(View.VISIBLE);
    }

    private void nextAction() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                revealButton();
                fadeoutProgressDialog();
                delayedStartNext();
            }
        }, 2000);
    }

    private void revealButton() {
        signInBtn.setElevation(0f);
        revealView.setVisibility(View.VISIBLE);
        int x = revealView.getWidth();
        int y = revealView.getHeight();

        int startX = (int) (getFinalWidth() / 2 + signInBtn.getX());
        int startY = (int) (getFinalWidth() / 2 + signInBtn.getY());
        float radius = Math.max(x, y) * 1.2f;
        Animator reveal = ViewAnimationUtils.createCircularReveal(revealView, startX, startY, getFinalWidth(), radius);
        reveal.setDuration(350);
        reveal.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                finish();
            }
        });
        reveal.start();
    }

    private void fadeoutProgressDialog() {
        bar.animate().alpha(0f).setDuration(200).start();
        EuserId.animate().alpha(0f).setDuration(200).start();
        password.animate().alpha(0f).setDuration(200).start();
        sigInText.animate().alpha(0f).setDuration(100).start();

    }

    private void delayedStartNext() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(MainActivity.this, mainScreen.class));
            }
        }, 250);
    }

    private int getFinalWidth() {
        return (int) getResources().getDimension(R.dimen.get_width);
    }


    public void register(View view) {
//        finish();
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
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    return;
                } else {
                    Toast.makeText(this, "Permission Denied for"+permissions, Toast.LENGTH_SHORT).show();
                    permissionCheck();
                }

            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void  permissionCheck(){
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 123);
            return;
            }

        }
    }


}
