package org.lol.wazirbuild.msilib;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;

public class NewsFeedAdapter extends PagerAdapter {
    private Context mcontext;
    private LayoutInflater inflater;

    private ArrayList<Integer> mImageID;
    private ArrayList<String> titles;
    private ArrayList<String> messages;
    private ArrayList<String> branchs;


    NewsFeedAdapter(Context context) {
        mcontext = context;
    }

    @Override
    public int getCount() {
        return mImageID.size();//this represents the length of the newsfeed
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return false;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        inflater = (LayoutInflater) mcontext.getSystemService(mcontext.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.news_feed_adapter, container, false);
        ImageView back = view.findViewById(R.id.background);
        TextView title = view.findViewById(R.id.title_newsFeed);
        TextView message = view.findViewById(R.id.information_newsFeed);
        TextView branch = view.findViewById(R.id.branch_newsFeed);

        back.setImageResource(mImageID.get(position));
        title.setText(titles.get(position));
        message.setText(messages.get(position));
        branch.setText(branchs.get(position));

        container.addView(view);
        return view;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout) object);
    }
}
