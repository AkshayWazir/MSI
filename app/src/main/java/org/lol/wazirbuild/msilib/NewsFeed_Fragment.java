package org.lol.wazirbuild.msilib;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFeed_Fragment extends Fragment {
    private FirebaseFirestore dr;
    private TextView txtmsg, txttit, txtbranch;
    private ImageView back;


    public NewsFeed_Fragment() {
        dr = FirebaseFirestore.getInstance();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_news_feed_, container, false);

        txtmsg = view.findViewById(R.id.frag_message);
        txttit = view.findViewById(R.id.frag_title);
        txtbranch = view.findViewById(R.id.frag_branch);
        back = view.findViewById(R.id.frag_background);

        int message = getArguments().getInt("pos");
        message = (message > 3) ? message % 3 + 1 : message;

        if (message == 1) {
            dr.collection("NewsFeed").document("CSE").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    txtmsg.setText(documentSnapshot.get("DES").toString());
                    txttit.setText(documentSnapshot.get("TIT").toString());
                    txtbranch.setText("CSE");
                    back.setImageResource(R.drawable.cse_news_feed);
                }
            });
        } else if (message == 2) {
            dr.collection("NewsFeed").document("IT").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    txtmsg.setText(documentSnapshot.get("DES").toString());
                    txttit.setText(documentSnapshot.get("TIT").toString());
                    txtbranch.setText("IT");
                    back.setImageResource(R.drawable.it_news_feed);
                }
            });

        } else if (message == 3) {
            dr.collection("NewsFeed").document("ECE").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    txtmsg.setText(documentSnapshot.get("DES").toString());
                    txttit.setText(documentSnapshot.get("TIT").toString());
                    txtbranch.setText("ECE");
                    back.setImageResource(R.drawable.ece_news_feed);
                }
            });
        }

        return view;
    }

}
