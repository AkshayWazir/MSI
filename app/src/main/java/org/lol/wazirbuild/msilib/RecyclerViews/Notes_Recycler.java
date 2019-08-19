package org.lol.wazirbuild.msilib.RecyclerViews;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import org.lol.wazirbuild.msilib.Notes_Category_activity;
import org.lol.wazirbuild.msilib.R;

public class Notes_Recycler extends RecyclerView.Adapter<Notes_Recycler.viewHolder> {
    Context context;

    public Notes_Recycler(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public Notes_Recycler.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_reycler_item, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Notes_Recycler.viewHolder holder, int position) {
        holder.Title.setText("Maths");

        holder.c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE));
                } else {
                    //deprecated in API 26
                    vibrator.vibrate(25);
                }
                ActivityOptions options = ActivityOptions
                        .makeSceneTransitionAnimation((Activity) context, holder.cardView, "notes-notes_category");
                context.startActivity(new Intent(context, Notes_Category_activity.class), options.toBundle());

            }
        });
    }

    @Override
    public int getItemCount() {
        return 7;
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView Title;
        ConstraintLayout c;
        CardView cardView;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            Title = itemView.findViewById(R.id.Notes_Title);
            c = itemView.findViewById(R.id.Notes_recycler_row);
            cardView = itemView.findViewById(R.id.notes_cardView);
        }
    }


}
