package com.bendroidapps.pdfdownloader;



import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class DownloadReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
        // Handle download completion logic here (e.g., show a notification)
        Toast.makeText(context, "Download complete!", Toast.LENGTH_SHORT).show();
    }
}
