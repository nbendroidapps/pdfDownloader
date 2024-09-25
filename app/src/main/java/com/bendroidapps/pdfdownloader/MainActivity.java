package com.bendroidapps.pdfdownloader;



import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private long downloadId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Button to download the PDF
        Button downloadButton = findViewById(R.id.downloadButton);
        downloadButton.setOnClickListener(v -> downloadPDF("https://firebasestorage.googleapis.com/v0/b/syllabusbooks-soyk54.appspot.com/o/pdfbooks%2Fs05b01%2F11_taohid_risalat_o_akhirat.pdf?alt=media&token=c2a2f905-5051-4765-9f53-170e3900d7d6", "boi.pdf"));

        // Button to open the PDF after download
        Button openButton = findViewById(R.id.openButton);
        openButton.setOnClickListener(v -> {
            File file = getDownloadedPDF("boi.pdf");
            Toast.makeText(MainActivity.this, "File "+file, Toast.LENGTH_SHORT).show();
           // checkStoragePermission();
            if (file != null && file.exists()) {
                openPDF(file);
            } else {
                Toast.makeText(MainActivity.this, "File not found", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Method to download the PDF
    public void downloadPDF(String fileUrl, String fileName) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(fileUrl));
        request.setTitle(fileName);
        request.setDescription("Downloading PDF...");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);

        DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        if (downloadManager != null) {


            downloadId = downloadManager.enqueue(request);  // Save the download ID
            Toast.makeText(this, "Download started", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to get the downloaded PDF file
    public File getDownloadedPDF(String fileName) {
        File downloadsFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File pdfFile = new File(downloadsFolder, fileName);
        return pdfFile.exists() ? pdfFile : null;
    }

    // Method to open the PDF using an external PDF viewer
    public void openPDF(File pdfFile) {
        Uri pdfUri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", pdfFile);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(pdfUri, "application/pdf");
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        try {
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(this, "No PDF viewer found", Toast.LENGTH_SHORT).show();
        }
    }

    private static final int REQUEST_STORAGE_PERMISSION = 1;

    private void checkStoragePermission() {
        if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_STORAGE_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_STORAGE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }


}
