package org.lol.wazirbuild.msilib;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import static android.widget.Toast.LENGTH_LONG;

public class RegistrationScreen extends AppCompatActivity implements OpenDialog.OpenDialogListener {
    private ToggleButton college;
    private RadioGroup msi, msit, shif;
    private TextInputLayout enroll, nam;
    private String collegeSelect;
    private Button sbmtBtn;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_screen);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Register");
        enroll = findViewById(R.id.textInputLayout2);
        nam = findViewById(R.id.textInputLayout);
        college = findViewById(R.id.college_change);
        msi = findViewById(R.id.msi_programs);
        msit = findViewById(R.id.msit_programs);
        shif = findViewById(R.id.shift);
        mAuth = FirebaseAuth.getInstance();
        sbmtBtn = findViewById(R.id.sbmt_btn);
        collegeSelect = "MSIT";
        db = FirebaseFirestore.getInstance();
        final OpenDialog dialog = new OpenDialog();
        sbmtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (enroll.getEditText().getText().toString().isEmpty()) {
                    Toast.makeText(RegistrationScreen.this, "Invalid Entry", LENGTH_LONG).show();
                } else {
                    dialog.showNow(getSupportFragmentManager(), "registration Confirmation");
                }
            }
        });

        college.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    msit.setVisibility(View.INVISIBLE);
                    msi.setVisibility(View.VISIBLE);
                    collegeSelect = "MSI";
                    sbmtBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            dialog.showNow(getSupportFragmentManager(), "registration Confirmation");
                        }
                    });
                } else {
                    msit.setVisibility(View.VISIBLE);
                    msi.setVisibility(View.INVISIBLE);
                    collegeSelect = "MSIT";
                    sbmtBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            dialog.showNow(getSupportFragmentManager(), "registration Confirmation");

                        }
                    });
                }
            }
        });

    }

    @Override
    public void OnYesClicked() {
        if (collegeSelect.equals("MSIT")) {
            engineer();
        } else {
            non_engineers();
        }
    }

    private void engineer() {
        final String name = nam.getEditText().getText().toString().trim();
        final String enrollment = enroll.getEditText().getText().toString().trim();
        String prog, sh;
        int program = msit.getCheckedRadioButtonId();
        int shift = shif.getCheckedRadioButtonId();
        final Student object = new Student();
        object.setName(name);
        object.setEnrollment(enrollment);
        switch (shift) {
            case (R.id.morning):
                sh = "Morning";
                object.setShift(sh);
                break;
            case (R.id.evening):
                sh = "Evening";
                object.setShift(sh);
                break;
        }
        switch (program) {
            case (R.id.cse_rdo):
                prog = "CSE";
                object.setBranch(prog);
                break;
            case (R.id.it_rdo):
                prog = "IT";
                object.setBranch(prog);
                break;
            case (R.id.ece_rdo):
                prog = "ECE";
                object.setBranch(prog);
                break;
            case (R.id.eee_rdo):
                prog = "EEE";
                object.setBranch(prog);
                object.setShift("Morning");
                break;
        }
        String email = enrollment + "@msit.com";
        final String year = enrollment.substring(enrollment.length() - 2);
        object.setYear(year);

        mAuth.createUserWithEmailAndPassword(email, enrollment).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    db.collection(collegeSelect).document(year).collection(object.getBranch()).document(enrollment).set(object).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(RegistrationScreen.this, "Welcome " + name, LENGTH_LONG).show();
                            setResult(RESULT_OK, new Intent());
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(RegistrationScreen.this, "Failed to register Try again", LENGTH_LONG).show();
                        }
                    });
                } else {
                    Toast.makeText(RegistrationScreen.this, "SignUp Failed", LENGTH_LONG).show();
                }
            }
        });
    }

    private void non_engineers() {
        final String name = nam.getEditText().getText().toString().trim();
        final String enrollment = enroll.getEditText().getText().toString().trim();
        String sh;
        int program = msi.getCheckedRadioButtonId();
        int shift = shif.getCheckedRadioButtonId();
        final Student object = new Student();
        object.setName(name);
        object.setEnrollment(enrollment);
        switch (shift) {
            case (R.id.morning):
                sh = "Morning";
                object.setShift(sh);
                break;
            case (R.id.evening):
                sh = "Evening";
                object.setShift(sh);
                break;
        }
        String prog = "";
        switch (program) {
            case (R.id.prog_bba):
                prog = "BBA General";
                object.setBranch(prog);
                break;
            case (R.id.prog_bbabi):
                prog = "BBA B & I";
                object.setBranch(prog);
                break;
            case (R.id.prog_bca):
                prog = "BCA";
                object.setBranch(prog);
                break;
            case (R.id.prog_bcom):
                prog = "B.Ed";
                object.setBranch(prog);
                break;
        }

        String email = enrollment + "@msit.com";
        final String year = enrollment.substring(enrollment.length() - 2);
        object.setYear(year);
        mAuth.createUserWithEmailAndPassword(email, enrollment).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    db.collection(collegeSelect).document(year).collection(object.getBranch()).document(enrollment).set(object).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(RegistrationScreen.this, "Welcome " + name, LENGTH_LONG).show();
                            setResult(RESULT_OK, new Intent());
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(RegistrationScreen.this, "Failed to register Try again", LENGTH_LONG).show();
                        }
                    });
                } else {
                    Toast.makeText(RegistrationScreen.this, "SignUp Failed", LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        setResult(RESULT_CANCELED, new Intent());
        finish();
    }
}
