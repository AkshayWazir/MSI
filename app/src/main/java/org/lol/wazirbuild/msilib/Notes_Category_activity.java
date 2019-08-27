package org.lol.wazirbuild.msilib;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.lol.wazirbuild.msilib.RecyclerViews.notes_categoryRecycler;
import org.lol.wazirbuild.msilib.model.notes_category_model;

import java.util.ArrayList;

public class Notes_Category_activity extends AppCompatActivity {

    String TAG="Mytag";
    private String TITLE="Title";
    private String DATE="date";
    private String PROVIDER="notes_provider";
    private String URL="url";


    RecyclerView recyclerView;
    TextView sub_Title;
    notes_categoryRecycler NCR;
    ImageView titleimg;

    String ref;
    ArrayList<notes_category_model> list = new ArrayList<>();
    notes_category_model notesCategoryModel;

    private FirebaseFirestore dataBase = FirebaseFirestore.getInstance();
    private DocumentReference reference = dataBase.collection("Notes").document("MSIT")
            .collection("IT").document("Second Year");


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.notesActivityStatusBar));
        }

        setContentView(R.layout.notes_category_activity);



        Bundle bundle = getIntent().getExtras();
        ref = bundle.getString("choosen_Subject");
        sub_Title=findViewById(R.id.Subject_title);
        sub_Title.setText(ref);


        reference.collection("Subjects").document(ref).collection("Notes")
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot d : queryDocumentSnapshots) {
                    notesCategoryModel = new notes_category_model(d.get(TITLE)+"",d.get(DATE)+""
                            ,d.get(PROVIDER)+"",d.get(URL)+"");
                    list.add(notesCategoryModel);
                    NCR.notifyDataSetChanged();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        recyclerView = findViewById(R.id.category_notes_Recycler);

        NCR = new notes_categoryRecycler(this, list);
        RecyclerView.LayoutManager l = new LinearLayoutManager(this);
        ((LinearLayoutManager) l).setOrientation(RecyclerView.VERTICAL);

        recyclerView.setLayoutManager(l);
        recyclerView.setAdapter(NCR);
        NCR.notifyDataSetChanged();
    }

}
