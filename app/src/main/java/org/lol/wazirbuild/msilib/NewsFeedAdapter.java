package org.lol.wazirbuild.msilib;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class NewsFeedAdapter extends FragmentStatePagerAdapter {


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
        return 4;
    }
}
