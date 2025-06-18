package com.example.educare;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class View_lecture_note extends AppCompatActivity implements JsonResponse {

    ImageView pdfImageView;
    ArrayList<String> pdfPages;
    int currentPage = 0; // Keep track of the current page
    Button nextButton, previousButton, downloadButton;
    public static String file;
    PdfRenderer pdfRenderer;
    PdfRenderer.Page currentPdfPage;
    ParcelFileDescriptor parcelFileDescriptor;

    private static final int PERMISSION_REQUEST_CODE = 100; // Request code for permissions

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_question_paper);

        pdfImageView = findViewById(R.id.pdfImageView);
        pdfPages = new ArrayList<>();
        nextButton = findViewById(R.id.nextPage);
        previousButton = findViewById(R.id.previousPage);
        downloadButton = findViewById(R.id.download);

        // Adding the PDF file to be viewed
        pdfPages.add(Custom_view_question_paper.files);

        // Handle next and previous button clicks
        nextButton.setOnClickListener(v -> {
            if (pdfRenderer != null && currentPage < pdfRenderer.getPageCount() - 1) {
                currentPage++;
                showPage(currentPage);
            }
        });

        previousButton.setOnClickListener(v -> {
            if (pdfRenderer != null && currentPage > 0) {
                currentPage--;
                showPage(currentPage);
            }
        });

        // Handle download button click
        downloadButton.setOnClickListener(v -> {
            // Check for storage permissions
            if (checkPermission()) {
                // Download the PDF to external storage
                new DownloadPdfTask("http://" + IpSetting.text + "/" + Custom_view_lecture_note.files, true).execute();
            } else {
                // Request storage permissions
                requestPermission();
            }
        });

        // Initial PDF load
        String pdfUrl = "http://" + IpSetting.text + "/" + Custom_view_lecture_note.files;
        new DownloadPdfTask(pdfUrl, false).execute();
    }

    @Override
    public void response(JSONObject jsonObject) throws JSONException {
        // Handle the response here if needed
    }

    private class DownloadPdfTask extends AsyncTask<Void, Void, File> {
        private final String pdfUrl;
        private final boolean isDownload; // If true, the file will be downloaded to storage
        private String errorMessage = null;
        private String downloadPath = null;

        public DownloadPdfTask(String pdfUrl, boolean isDownload) {
            this.pdfUrl = pdfUrl;
            this.isDownload = isDownload;
        }

        @Override
        protected File doInBackground(Void... params) {
            try {
                // Download PDF file from the URL
                URL url = new URL(pdfUrl);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                File pdfFile = new File(getCacheDir(), "downloaded_pdf.pdf");
                InputStream inputStream = urlConnection.getInputStream();
                FileOutputStream fileOutputStream = new FileOutputStream(pdfFile);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) > 0) {
                    fileOutputStream.write(buffer, 0, length);
                }
                fileOutputStream.close();
                inputStream.close();

                if (isDownload) {
                    // Save to external storage if requested
                    downloadPath = savePdfToExternalStorage(pdfFile);
                }

                return pdfFile;
            } catch (IOException e) {
                e.printStackTrace();
                errorMessage = "Error downloading PDF: " + e.getMessage();
                return null;
            } catch (Exception e) {
                e.printStackTrace();
                errorMessage = "Error: " + e.getMessage();
                return null;
            }
        }

        @Override
        protected void onPostExecute(File pdfFile) {
            if (pdfFile != null) {
                try {
                    openPdfRenderer(pdfFile);
                    showPage(currentPage); // Always show the current page

                    if (isDownload && downloadPath != null) {
                        Toast.makeText(getApplicationContext(), "PDF downloaded to: " + downloadPath, Toast.LENGTH_LONG).show();
                    }
                } catch (IOException e) {
                    Toast.makeText(getApplicationContext(), "Error opening PDF", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), errorMessage != null ? errorMessage : "Error loading PDF", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private String savePdfToExternalStorage(File pdfFile) {
        try {
            // Define the path for saving the PDF
            File downloadDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "educare");
            if (!downloadDir.exists()) {
                downloadDir.mkdirs(); // Create the directory if it doesn't exist
            }

            File downloadFile = new File(downloadDir, "Lecture_note.pdf");

            // Copy the downloaded PDF to external storage
            try (FileInputStream inputStream = new FileInputStream(pdfFile);
                 FileOutputStream outputStream = new FileOutputStream(downloadFile)) {
                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, length);
                }

                return downloadFile.getPath();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to save PDF: " + e.getMessage());
        }
    }

    private void openPdfRenderer(File pdfFile) throws IOException {
        // Close the previous renderer if it exists
        closeRenderer();

        parcelFileDescriptor = ParcelFileDescriptor.open(pdfFile, ParcelFileDescriptor.MODE_READ_ONLY);
        pdfRenderer = new PdfRenderer(parcelFileDescriptor);
        updateNavigationButtons();
    }

    private void closeRenderer() {
        try {
            if (currentPdfPage != null) {
                currentPdfPage.close();
                currentPdfPage = null;
            }
            if (pdfRenderer != null) {
                pdfRenderer.close();
                pdfRenderer = null;
            }
            if (parcelFileDescriptor != null) {
                parcelFileDescriptor.close();
                parcelFileDescriptor = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showPage(int index) {
        // Ensure the page index is within bounds and pdfRenderer is initialized
        if (pdfRenderer == null || index < 0 || index >= pdfRenderer.getPageCount()) {
            return;
        }

        // Close the current page before opening another one
        if (currentPdfPage != null) {
            currentPdfPage.close();
        }

        // Open the requested page
        currentPdfPage = pdfRenderer.openPage(index);

        // Create a bitmap to display the page
        Bitmap bitmap = Bitmap.createBitmap(currentPdfPage.getWidth(), currentPdfPage.getHeight(),
                Bitmap.Config.ARGB_8888);

        // Render the page onto the bitmap
        currentPdfPage.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);

        // Display the rendered bitmap on the ImageView
        pdfImageView.setImageBitmap(bitmap);

        // Update the button states
        updateNavigationButtons();
    }

    // Enable/Disable navigation buttons based on the current page
    private void updateNavigationButtons() {
        if (pdfRenderer != null) {
            previousButton.setEnabled(currentPage > 0);
            nextButton.setEnabled(currentPage < pdfRenderer.getPageCount() - 1);
        } else {
            previousButton.setEnabled(false);
            nextButton.setEnabled(false);
        }
    }

    // Check if storage permissions are granted
    private boolean checkPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    // Request storage permission
    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, download the file
                new DownloadPdfTask("http://" + IpSetting.text + "/" + Custom_view_lecture_note.files, true).execute();
            } else {
                Toast.makeText(this, "Storage permission required to download files", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        closeRenderer();
        super.onDestroy();
    }
}