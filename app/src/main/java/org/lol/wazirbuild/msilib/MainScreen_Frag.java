package org.lol.wazirbuild.msilib;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import java.util.Timer;
import java.util.TimerTask;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainScreen_Frag extends Fragment {
    private final long DELAY_MS = 500;
    private final long PERIOD_MS = 4000;
    private ViewPager viewPager;
    private NewsFeedAdapter adapter;
    private int currentPage = 0;
    private Timer timer;
    ConstraintLayout layout;
    ConstraintLayout creation_start;

    public MainScreen_Frag() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == 3) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
            }
        };

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);


        View view = inflater.inflate(R.layout.fragment_main_screen_, container, false);
        layout = view.findViewById(R.id.notes_start);
        creation_start=view.findViewById(R.id.creation_start);
        creation_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Creation_Activity.class));
            }
        });
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Notes_Activity.class));
            }
        });
        viewPager = view.findViewById(R.id.news_feed);
        adapter = new NewsFeedAdapter(getActivity().getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        return view;
    }

}
