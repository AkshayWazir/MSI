package org.lol.wazirbuild.msilib;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.lol.wazirbuild.msilib.RecyclerViews.creation_adapter;
import org.lol.wazirbuild.msilib.model.creation_item_model;

import java.util.ArrayList;

public class Creation_Activity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    creation_adapter creationAdapter;

    ArrayList<creation_item_model> list=new ArrayList<>();

    private FirebaseFirestore dataBase = FirebaseFirestore.getInstance();

    public Creation_Activity() {

        dataBase.collection("Creation")
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot d : queryDocumentSnapshots) {
                    creation_item_model cim=new creation_item_model(d.get("description").toString()
                            ,d.get("imageUrl").toString(),d.get("title").toString());
                    list.add(cim);
                    creationAdapter.notifyDataSetChanged();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.creationStatusBar));
        }
        setContentView(R.layout.activity_creation_);

        creationAdapter = new creation_adapter(this,list);

        recyclerView = findViewById(R.id.creation_recycler);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        ((LinearLayoutManager) manager).setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(creationAdapter);

    }
}
