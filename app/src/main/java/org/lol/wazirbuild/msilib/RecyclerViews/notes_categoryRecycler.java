package org.lol.wazirbuild.msilib.RecyclerViews;

import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.FileProvider;
import androidx.core.content.MimeTypeFilter;
import androidx.recyclerview.widget.RecyclerView;


import org.lol.wazirbuild.msilib.R;
import org.lol.wazirbuild.msilib.model.notes_category_model;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;


public class notes_categoryRecycler extends RecyclerView.Adapter<notes_categoryRecycler.viewHolder> {
    private Context context;
    String TAG = "Mytag";
    ArrayList<notes_category_model> list;

    public notes_categoryRecycler(Context context, ArrayList<notes_category_model> list) {
        this.context = context;
        this.list = list;

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }


    @Override
    public notes_categoryRecycler.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_notes_item, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final notes_categoryRecycler.viewHolder holder, final int position) {

        holder.Title.setText(list.get(position).getTitle());
        holder.notes_provider.setText("Author : " + list.get(position).getNotes_provider());
        holder.date.setText("Upload Date: " + list.get(position).getDate());
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                File notesDirectory = new File(Environment.getExternalStorageDirectory().toString() + "/MSI Library/Notes");
                File outputFile = new File(notesDirectory, URLUtil.guessFileName(list.get(position).getUrl(), null
                        , MimeTypeMap.getFileExtensionFromUrl(list.get(position).getUrl())));
                if (!notesDirectory.exists()) {
                    notesDirectory.mkdirs();
                }

                Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE));
                } else {
                    vibrator.vibrate(25);
                }
                openPDF(outputFile, list.get(position).getUrl(), holder.isDownloading
                        , list.get(position).getTitle(), list.get(position).getNotes_provider());
            }


        });

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


    }


    public int getItemCount() {
        return list.size();
    }


    public class viewHolder extends RecyclerView.ViewHolder {
        TextView Title, notes_provider, date, size;
        ConstraintLayout constraintLayout;
        CardView cardView;
        ImageView option;
        boolean isDownloading = false;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            Title = itemView.findViewById(R.id.category_title);
            constraintLayout = itemView.findViewById(R.id.notes_category_rowItem);
            date = itemView.findViewById(R.id.category_date);
            option = itemView.findViewById(R.id.notes_category_option);
//            size = itemView.findViewById(R.id.size);
            notes_provider = itemView.findViewById(R.id.notes_provider);
            cardView = itemView.findViewById(R.id.notes_category_cardView);
        }
    }

    public boolean dofileExists(File outputFile) {
        if (outputFile.exists()) {
            return true;
        } else
            return false;
    }

    private void openPDF(File outputFile, String link, boolean isDownloading, String Title, String Description) {

        if (!dofileExists(outputFile)) {
            Toast.makeText(context, "Downloading...", Toast.LENGTH_SHORT).show();
            downloadFile(link, isDownloading, Title, Description);
//            Toast.makeText(context, "Being Downloaded...", Toast.LENGTH_SHORT).show();


        } else if (outputFile != null) {
            Toast.makeText(context, "Opening File...", Toast.LENGTH_SHORT).show();

            Uri path = null;
            Intent pdf = new Intent(Intent.ACTION_VIEW);
            pdf.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            pdf.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                path = FileProvider.getUriForFile(context, "ibas.provider", outputFile);

                pdf.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            } else {
                path = Uri.fromFile(outputFile);
            }

            pdf.setDataAndType(path, "application/pdf");
            try {
                context.startActivity(pdf);
            } catch (ActivityNotFoundException e) {
                e.getMessage();
            }
        }
    }

    private void downloadFile(String download_url, boolean isDownloading, String Title, String Description) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(download_url));
        request.setTitle(Title);
        request.setDescription(Description)
                .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE
                        | DownloadManager.Request.NETWORK_WIFI);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        String nameOfFile = URLUtil.guessFileName(download_url, null,
                MimeTypeMap.getFileExtensionFromUrl(download_url));
        request.setDestinationInExternalPublicDir("/MSI Library/Notes", nameOfFile);

        DownloadManager manager = (DownloadManager) context
                .getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);
    }
}
