package org.lol.wazirbuild.msilib.RecyclerViews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import org.lol.wazirbuild.msilib.R;

public class Notes_Category_Recycler extends RecyclerView.Adapter<Notes_Category_Recycler.viewHolder> {
    private Context context;
    public Notes_Category_Recycler(Context context){
        this.context=context;
    }

    @NonNull
    @Override
    public Notes_Category_Recycler.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.category_notes_item,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Notes_Category_Recycler.viewHolder holder, int position){
        holder.Title.setText("Notes");
        holder.notes_provider.setText("Notes Provider");

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"PDF will be loaded",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        TextView Title,notes_provider;
        ConstraintLayout constraintLayout;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            Title=itemView.findViewById(R.id.category_title);
            constraintLayout=itemView.findViewById(R.id.notes_category_rowItem);
            notes_provider=itemView.findViewById(R.id.notes_provider);
        }
    }
}
