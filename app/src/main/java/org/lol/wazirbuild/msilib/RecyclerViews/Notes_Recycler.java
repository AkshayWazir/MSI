package org.lol.wazirbuild.msilib.RecyclerViews;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.lol.wazirbuild.msilib.Notes_Category_activity;
import org.lol.wazirbuild.msilib.R;

import java.util.ArrayList;

public class Notes_Recycler extends RecyclerView.Adapter<Notes_Recycler.viewHolder> {
    Context context;
    ArrayList<String> TITLE;

    public Notes_Recycler(Context context, ArrayList<String> T) {
        this.context = context;
        TITLE = T;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_reycler_item, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final viewHolder viewHolder, final int position) {
        viewHolder.Title.setText(TITLE.get(position));
        viewHolder.c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) context, viewHolder.c, "notes-notes_category");
                Intent i = new Intent(context, Notes_Category_activity.class);
                i.putExtra("choosen_Subject", TITLE.get(position));
                context.startActivity(i, options.toBundle());
            }
        });
    }

    @Override
    public int getItemCount() {
        return TITLE.size();
    }


    public class viewHolder extends RecyclerView.ViewHolder {
        TextView Title;
        CardView c;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            Title = itemView.findViewById(R.id.Notes_Title);
            c = itemView.findViewById(R.id.notes_cardView);

        }
    }

}
