package org.lol.wazirbuild.msilib;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Timer;
import java.util.TimerTask;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainScreen_Frag extends Fragment {
    private final long DELAY_MS = 500;
    private final long PERIOD_MS = 4000;
    ImageView view;
    private ViewPager viewPager;
    private NewsFeedAdapter adapter;
    private int currentPage = 0;
    private Timer timer;
    private FirebaseAuth mAuth;

    public MainScreen_Frag() {
        // Required empty public constructor
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
        viewPager = view.findViewById(R.id.news_feed);
        adapter = new NewsFeedAdapter(getActivity().getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        return view;
    }

}
