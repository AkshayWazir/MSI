package org.lol.wazirbuild.msilib;

import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
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

import org.lol.wazirbuild.msilib.RecyclerViews.Notes_Recycler;

import java.util.ArrayList;

public class Notes_Activity extends AppCompatActivity {

    RecyclerView recyclerView;
    Notes_Recycler NR;

    ArrayList<String> TITLE = new ArrayList<>();

    private FirebaseFirestore dataBase = FirebaseFirestore.getInstance();
    private DocumentReference reference = dataBase.collection("Notes").document("MSIT")
            .collection("IT").document("Second Year");

    public Notes_Activity() {
        reference.collection("Subjects").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot d : queryDocumentSnapshots) {
                    TITLE.add(d.getId());
                    NR.notifyDataSetChanged();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.notesActivityStatusBar));
        }
        setContentView(R.layout.activity_notes_);

        recyclerView = findViewById(R.id.notes_recycler);


        NR = new Notes_Recycler(this,TITLE);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        ((LinearLayoutManager) manager).setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(NR);
        NR.notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
    }

}
