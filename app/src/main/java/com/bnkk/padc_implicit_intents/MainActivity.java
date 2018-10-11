package com.bnkk.padc_implicit_intents;

import android.Manifest;
import android.app.SearchManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.CalendarContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ShareCompat;
import android.support.v4.app.ShareCompat.IntentBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends BaseActivity {

    private static final int PERMISSION_REQUEST_PHONE_CALL = 100;
    private static final int PERMISSION_REQUEST_CAMERA = 101;

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_FROM_DEVICE = 2;

    public static final String URI_TO_OPEN_IN_MAP = "http://maps.google.com/maps?daddr=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                // Write the work to do with image here
            } else if (requestCode == REQUEST_IMAGE_FROM_DEVICE) {
                // Write the code here
            }
        }
    }

    // On Button Click Methods
    public void onTapBtnShareCompat(View view) {
        shareViaShareCompat("This is message");
        Toast.makeText(getApplicationContext(), "shareWithShareCompat", Toast.LENGTH_LONG).show();
    }

    public void onTapBtnNavigateMap(View view) {
        navigateOnMap("Yangon");
        Toast.makeText(getApplicationContext(), "navigateOnMap", Toast.LENGTH_LONG).show();
    }

    public void onTapBtnMakePhCall(View view) {
        makePhoneCall("0912345678");
        Toast.makeText(getApplicationContext(), "makePhoneCall", Toast.LENGTH_LONG).show();
    }

    public void onTapBtnSendEmail(View view) {
        sendEmail("example@gmail.com", "This is title", "This is message body");
        Toast.makeText(getApplicationContext(), "sendEmail", Toast.LENGTH_LONG).show();
    }

    public void onTapBtnOpenCamera(View view) {
        takePhoto();
        Toast.makeText(getApplicationContext(), "takePictureWithCamera", Toast.LENGTH_LONG).show();
    }

    public void onTapBtnPictureFromStorage(View view) {
        selectPictureFromDevice();
        Toast.makeText(getApplicationContext(), "selectPictureFromDeviceStorage", Toast.LENGTH_LONG).show();
    }

    public void onTapBtnSaveEventInCalendar(View view) {
        saveEventInCalendar("Title", "This is description", "Yangon");
        Toast.makeText(getApplicationContext(), "saveEventInCalendar", Toast.LENGTH_LONG).show();
    }

    // Methods for Intents
    public void shareViaShareCompat(String message) {
        Intent shareCompatIntent = ShareCompat.IntentBuilder.from(this)
                .setType("text/plain")
                .setText(message)
                .getIntent();
        startActivity(shareCompatIntent);
    }

    public void navigateOnMap(String location) {
        String uriToOpen = URI_TO_OPEN_IN_MAP + location;
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uriToOpen));
        startActivity(intent);
    }

    public void makePhoneCall(String PhNumber) {
        PhNumber = PhNumber.replaceAll(" ", "");
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + PhNumber));

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    PERMISSION_REQUEST_PHONE_CALL);
        }
        startActivity(intent);
    }

    public void sendEmail(String emailAddress, String title, String message) {
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + emailAddress));
        intent.putExtra(Intent.EXTRA_SUBJECT, title);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        startActivity(intent);
    }

    public void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    PERMISSION_REQUEST_CAMERA);
        }
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
    }

    public void selectPictureFromDevice() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_IMAGE_FROM_DEVICE);
    }

    public void saveEventInCalendar(String title, String description, String location) {
        Intent intent = new Intent(Intent.ACTION_INSERT);
        intent.setData(CalendarContract.Events.CONTENT_URI);
        intent.putExtra(CalendarContract.Events.TITLE, title);
        intent.putExtra(CalendarContract.Events.DESCRIPTION, description);
        intent.putExtra(CalendarContract.Events.EVENT_LOCATION, location);
        startActivity(intent);
    }
}
