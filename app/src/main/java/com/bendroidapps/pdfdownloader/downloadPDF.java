package com.bendroidapps.pdfdownloader;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;

public class downloadPDF {

    private Context context;

    // Constructor to pass the context
    public void FileDownloader(Context context) {
        this.context = context;
    }

    public void downloadPDF(String fileUrl, String fileName) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(fileUrl));
        request.setTitle(fileName);
        request.setDescription("Downloading PDF...");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);

        // Use the passed context
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        if (downloadManager != null) {
            downloadManager.enqueue(request);
        }
    }
}

//
//public void downloadPDF(Context context, String fileUrl, String fileName) {
//    DownloadManager.Request request = new DownloadManager.Request(Uri.parse(fileUrl));
//
//    // Set the title and description of the download
//    request.setTitle(fileName);
//    request.setDescription("Downloading PDF...");
//
//    // Allow the file to be visible in the system's Downloads UI
//    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
//
//    // Set the destination to save the file in external public storage
//    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);
//
//    // Get download service and enqueue the request
//    DownloadManager downloadManager = (DownloadManager) getSystemService(context.DOWNLOAD_SERVICE);
//    if (downloadManager != null) {
//        downloadManager.enqueue(request);
//        Toast.makeText(this, "Download started", Toast.LENGTH_SHORT).show();
//    }
//}
