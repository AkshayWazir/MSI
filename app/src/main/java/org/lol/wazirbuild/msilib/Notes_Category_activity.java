package org.lol.wazirbuild.msilib;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.lol.wazirbuild.msilib.Database.model.notes_category_model;
import org.lol.wazirbuild.msilib.Database.viewModels.notes_category_viewModel;
import org.lol.wazirbuild.msilib.RecyclerViews.notes_categoryRecycler;

import java.util.ArrayList;
import java.util.List;

public class Notes_Category_activity extends AppCompatActivity {

    String TAG = "Mytag";
    private String TITLE = "Title";
    private String DATE = "date";
    private String PROVIDER = "notes_provider";
    private String URL = "url";
    private String ID = "id";

    private notes_category_viewModel viewModel;


    RecyclerView recyclerView;
    notes_categoryRecycler NCR;
    Toolbar toolbar;

    String ref;
    List<notes_category_model> list = new ArrayList<>();
    notes_category_model notesCategoryModel;

    private FirebaseFirestore dataBase = FirebaseFirestore.getInstance();
    private DocumentReference reference = dataBase.collection("Notes").document("MSIT")
            .collection("IT").document("Second Year");


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.notes_category_activity);

        recyclerView = findViewById(R.id.category_notes_Recycler);

        initViewModel();
        initRecyclerView();


        Toast.makeText(this, isNetworkAvailable() + "", Toast.LENGTH_SHORT).show();

        Bundle bundle = getIntent().getExtras();
        ref = bundle.getString("choosen_Subject");
        toolbar = findViewById(R.id.tool_bar_subject);
        toolbar.setTitle(ref);


        if (isNetworkAvailable()) {

            reference.collection("Subjects").document(ref).collection("Notes")
                    .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    for (DocumentSnapshot d : queryDocumentSnapshots) {
                        notesCategoryModel = new notes_category_model(Integer.parseInt(d.get(ID).toString()),
                                d.get(TITLE) + "", d.get(DATE) + "",
                                d.get(PROVIDER) + "", d.get(URL) + "");
                        viewModel.InsertSingle(notesCategoryModel);
                        NCR.notifyDataSetChanged();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            Toast.makeText(this, "Offline", Toast.LENGTH_LONG).show();
        }


    }

    private void initRecyclerView() {
        LinearLayoutManager l = new LinearLayoutManager(this);
        l.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(l);
        recyclerView.setAdapter(NCR);
    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this)
                .get(notes_category_viewModel.class);
        viewModel.viewModelList.observe(Notes_Category_activity.this, new Observer<List<notes_category_model>>() {
            @Override
            public void onChanged(List<notes_category_model> notes_category_models) {
                list.clear();
                list.addAll(notes_category_models);

                if (NCR == null) {
                    NCR = new notes_categoryRecycler(Notes_Category_activity.this, (ArrayList<notes_category_model>) list);
                    recyclerView.setAdapter(NCR);
                } else {
                    NCR.notifyDataSetChanged();
                }
            }
        });

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
