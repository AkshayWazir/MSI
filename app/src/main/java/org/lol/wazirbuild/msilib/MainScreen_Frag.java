package org.lol.wazirbuild.msilib;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainScreen_Frag extends Fragment {
    private FirebaseAuth mAuth;
    FirebaseUser currentUser;
    ImageView view;
    ViewPager viewPager;
    NewsFeedAdapter adapter;

    public MainScreen_Frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_screen_, container, false);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        viewPager = view.findViewById(R.id.news_feed);
        adapter = new NewsFeedAdapter(getActivity().getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        // Inflate the layout for this fragment
        return view;
    }

}
