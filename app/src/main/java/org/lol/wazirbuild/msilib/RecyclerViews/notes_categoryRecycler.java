package org.lol.wazirbuild.msilib.RecyclerViews;

import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import org.lol.wazirbuild.msilib.R;

import java.io.File;

public class notes_categoryRecycler extends RecyclerView.Adapter<notes_categoryRecycler.viewHolder> {
    private Context context;
    private int i = 0;


    String[] sites = new String[]{"https://www.tutorialspoint.com/firebase/firebase_tutorial.pdf"
            , "http://ptgmedia.pearsoncmg.com/images/9780134191409/samplepages/9780134191409.pdf"};


    public notes_categoryRecycler(Context context) {
        this.context = context;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }


    @Override
    public notes_categoryRecycler.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_notes_item, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final notes_categoryRecycler.viewHolder holder, int position) {
        holder.Title.setText("Notes");
        holder.notes_provider.setText("Notes Provider");
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i > 1)
                    i = 0;

                File notesDirectory = new File(Environment.getExternalStorageDirectory().toString() + "/MSI Library/Notes");
                File outputFile = new File(notesDirectory, URLUtil.guessFileName(sites[i], null,
                        MimeTypeMap.getFileExtensionFromUrl(sites[i])));
                if (!notesDirectory.exists()) {
                    notesDirectory.mkdirs();
                }

                Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE));
                } else {
                    vibrator.vibrate(25);
                }
                openPDF(outputFile);
                i++;
            }


        });


    }


    public int getItemCount() {
        return 5;
    }


    public class viewHolder extends RecyclerView.ViewHolder {
        TextView Title, notes_provider;
        ConstraintLayout constraintLayout;
        CardView cardView;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            Title = itemView.findViewById(R.id.category_title);
            constraintLayout = itemView.findViewById(R.id.notes_category_rowItem);
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

    private void openPDF(File outputFile) {

        if (!dofileExists(outputFile)) {
            Toast.makeText(context, "Downloading...", Toast.LENGTH_SHORT).show();
            downloadFile(sites[i]);

        } else if (outputFile != null) {
            Toast.makeText(context, "Opening File...", Toast.LENGTH_SHORT).show();
            Uri path = Uri.fromFile(outputFile);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Intent pdf = new Intent(Intent.ACTION_VIEW);
                pdf.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                Uri contentUri = FileProvider.getUriForFile(context.getApplicationContext(), "com.your.package.fileProvider", outputFile);
                pdf.setDataAndType(path, "application/pdf");
                try {
                    context.startActivity(pdf);
                } catch (ActivityNotFoundException e) {
                    e.getMessage();
                }
            }
        }
    }

    private void downloadFile(String download_url) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(download_url));
        request.setTitle("Notes Downloading");
        request.setDescription("being downloaded...");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        String nameOfFile = URLUtil.guessFileName(download_url, null,
                MimeTypeMap.getFileExtensionFromUrl(download_url));
        request.setDestinationInExternalPublicDir("/MSI Library/Notes", nameOfFile);

        DownloadManager manager = (DownloadManager) context
                .getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);

    }

}
