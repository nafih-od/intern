package com.example.educare;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.VideoView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Custom_view_lecture_note extends ArrayAdapter<String> {

    private Activity context;       // For getting current activity context
    private SharedPreferences sh;
    private String[] subject, teacher_name, title, file, summarize, date, note_id, sem;

    public static String files;

    public Custom_view_lecture_note(Activity context, String[] subject, String[] teacher_name, String[] title,
                                    String[] file, String[] summarize, String[] date, String[] note_id, String[] sem) {
        super(context, R.layout.activity_custom_view_lecture_note, title);
        this.context = context;
        this.subject = subject;
        this.teacher_name = teacher_name;
        this.title = title;
        this.file = file;
        this.summarize = summarize;
        this.date = date;
        this.note_id = note_id;
        this.sem = sem;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Inflate the layout if convertView is null
        if (convertView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(R.layout.activity_custom_view_lecture_note, parent, false);
        }

        sh = PreferenceManager.getDefaultSharedPreferences(context);
        // Find all the views in the layout
        TextView txtTitle = convertView.findViewById(R.id.title);
        TextView txtSubject = convertView.findViewById(R.id.subject);
        TextView txtTeacher = convertView.findViewById(R.id.teacher);
        TextView txtSummarize = convertView.findViewById(R.id.summarize);
        TextView txtDate = convertView.findViewById(R.id.date);
        TextView txtSem = convertView.findViewById(R.id.sem);
        TextView txtFileType = convertView.findViewById(R.id.file_type);
        Button viewButton = convertView.findViewById(R.id.viewButton);

        // Find the container for summarization and the summarize button
        LinearLayout summarizationContainer = convertView.findViewById(R.id.sumcont);
        MaterialButton summarizeButton = convertView.findViewById(R.id.sumbut);

        // Find the image view, video view, and download button
        ImageView imageView = convertView.findViewById(R.id.imageView);
        VideoView videoView = convertView.findViewById(R.id.videoView);
        MaterialButton vplay = convertView.findViewById(R.id.vplay);
        MaterialButton downloadButton = convertView.findViewById(R.id.downloadButton);

        // Set initial visibility for all views
        imageView.setVisibility(View.GONE);
        videoView.setVisibility(View.GONE);
        vplay.setVisibility(View.GONE);
        viewButton.setVisibility(View.GONE);
        summarizationContainer.setVisibility(View.GONE);
        summarizeButton.setVisibility(View.GONE);
        downloadButton.setVisibility(View.GONE);

        // Set data to views
        txtTitle.setText(title[position]);
        txtSubject.setText(subject[position]);
        txtTeacher.setText(teacher_name[position]);
        txtDate.setText(date[position]);
        txtSem.setText(sem[position]);
        txtSummarize.setText(summarize[position]);

        // Determine the type of content
        String filePath = file[position];
        if (filePath.endsWith(".pdf")) {
            // Show view button for PDF
            viewButton.setVisibility(View.VISIBLE);
            txtFileType.setText("PDF");
        } else if (filePath.endsWith(".jpg") || filePath.endsWith(".png")) {
            // Show image
            String imagePath = "http://" + sh.getString("ip", "") + "/" + file[position];
            imagePath = imagePath.replace("~", "");
            Picasso.with(context).load(imagePath).into(imageView);
            imageView.setVisibility(View.VISIBLE);
            txtFileType.setText("Image");


//            String pth = "http://" + sh.getString("ip", "") + "/" + file[position];
//            pth = pth.replace("~", "");
//
//            Picasso.with(context)
//                    .load(pth)
//                    .placeholder(R.drawable.ic_launcher_background)
//                    .error(R.drawable.ic_launcher_background)
//                    .into(Image);
        } else if (filePath.endsWith(".mp4")) {
            // Show video play button and summarize button
            vplay.setVisibility(View.VISIBLE);
            summarizeButton.setVisibility(View.VISIBLE);
            txtFileType.setText("Video");

            // Set up video play button
            vplay.setOnClickListener(v -> {
                videoView.setVisibility(View.VISIBLE);
                String videoPath = "http://" + sh.getString("ip", "") + "/" + filePath;
                videoPath = videoPath.replace("~", "");
                Uri videoUri = Uri.parse(videoPath);
                videoView.setVideoURI(videoUri);
                videoView.setOnPreparedListener(mp -> videoView.start());
            });
        }

        // Set up summarization button and container
        summarizeButton.setOnClickListener(v -> {
            if (summarizationContainer.getVisibility() == View.GONE) {
                summarizationContainer.setVisibility(View.VISIBLE);
                summarizeButton.setText("Hide Summary");
            } else {
                summarizationContainer.setVisibility(View.GONE);
                summarizeButton.setText("Show Summary");
            }
        });

        // Set up view button for PDF
        viewButton.setOnClickListener(v -> {
            files = file[position];
            Intent intent = new Intent(context.getApplicationContext(), View_lecture_note.class);
            context.startActivity(intent);
        });

        return convertView;
    }


}