package org.lol.wazirbuild.msilib;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class NewsFeedAdapter extends FragmentStatePagerAdapter {
    String mess[] = new String[3];
    String title[] = new String[3];
    String Branches[] = new String[3];
    int image[] = new int[3];

    public NewsFeedAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        NewsFeed_Fragment frag = new NewsFeed_Fragment();
        Bundle bundle = new Bundle();
        position += 1;
        bundle.putInt("pos", position);
        frag.setArguments(bundle);
        return frag;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
