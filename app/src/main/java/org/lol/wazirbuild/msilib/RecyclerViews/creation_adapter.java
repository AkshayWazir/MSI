package org.lol.wazirbuild.msilib.RecyclerViews;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.lol.wazirbuild.msilib.R;
import org.lol.wazirbuild.msilib.model.creation_item_model;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class creation_adapter extends RecyclerView.Adapter<creation_adapter.viewHolder> {
    Context context;
    ArrayList<creation_item_model> list;

    public creation_adapter(Context c, ArrayList<creation_item_model> list) {
        context = c;
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.creation_item_cardview, parent, false);
        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final viewHolder holder, final int position) {
        holder.Title.setText(list.get(position).getTitle());
        holder.Description.setText(list.get(position).getDescription());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView Title;
        TextView Description;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.creation_TitleImage);
            Title = itemView.findViewById(R.id.creation_item_Titletext);
            Description = itemView.findViewById(R.id.creation_item_Descriptiontext);
        }
    }

}
