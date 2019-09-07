package org.lol.wazirbuild.msilib.RecyclerViews;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.lol.wazirbuild.msilib.R;
import org.lol.wazirbuild.msilib.Database.model.creation_item_model;

import java.io.InputStream;
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
        holder.option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context, holder.option);
                popupMenu.inflate(R.menu.notes_catogery_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.note_details:
                                return true;
                            case R.id.rate_note:
                                return true;
                            default:
                                return false;
                        }
                    }
                });
                popupMenu.show();
            }
        });
        DownloadImageTask d= new DownloadImageTask(holder.image);
        d.execute(list.get(position).getImageUrl());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView image,option;
        TextView Title;
        TextView Description;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            option=itemView.findViewById(R.id.creation_menu_option);
            image = itemView.findViewById(R.id.creation_TitleImage);
            Title = itemView.findViewById(R.id.creation_item_Titletext);
            Description = itemView.findViewById(R.id.creation_item_Descriptiontext);
        }
    }


    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;
        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

}
