package org.lol.wazirbuild.msilib;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.google.firebase.firestore.FirebaseFirestore;

public class NewsFeedAdapter extends FragmentStatePagerAdapter {
    private int[] mImageID = {R.drawable.it_news_feed, R.drawable.ece_news_feed, R.drawable.it_news_feed};
    private String[] branches = {"CSE", "ECE", "IT"};
    private String[] titles = new String[3];
    private String[] messages = new String[3];

    public NewsFeedAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        NewsFeed_Fragment frag = new NewsFeed_Fragment();
        Bundle bundle = new Bundle();
        position += 1;
        bundle.putString("pos", Integer.toString(position));
        frag.setArguments(bundle);
        return frag;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
