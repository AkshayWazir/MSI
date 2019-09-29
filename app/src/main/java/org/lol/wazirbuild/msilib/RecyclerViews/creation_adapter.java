package org.lol.wazirbuild.msilib.RecyclerViews;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
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

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alt = new AlertDialog.Builder(context);
                LayoutInflater inflater = LayoutInflater.from(context);
                View view = inflater.inflate(R.layout.creation_item_alert_dialog, null);
                alt.setView(view);

                AlertDialog alertDialog = alt.create();

                TextView title = view.findViewById(R.id.creation_title_alert);
                TextView description = view.findViewById(R.id.creation_description_alert);
                ImageView whatsapp = view.findViewById(R.id.creation_contact);


                title.setText(list.get(position).getTitle());
                description.setText(list.get(position).getDescription());
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                alertDialog.show();

            }
        });

        DownloadImageTask d = new DownloadImageTask(holder.image,holder.layout);
        d.execute(list.get(position).getImageUrl());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView image, like;
        LinearLayout layout;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
//            like = itemView.findViewById(R.id.like);
            image = itemView.findViewById(R.id.creation_TitleImage);
            layout = itemView.findViewById(R.id.linlay);

        }
    }


    private static class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;
        LinearLayout manage;

        private DownloadImageTask(ImageView bmImage, LinearLayout layout) {
            this.bmImage = bmImage;
            this.manage = layout;

        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap bitmap = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                bitmap = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
            manage.setVisibility(View.VISIBLE);
        }

    }

}
