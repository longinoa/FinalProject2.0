package com.codepath.finalproject;

import android.Manifest;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ListActivity { // TODO: 7/12/17 make the app work if the device is turned sideways

    RecyclerView rvText;
    User user;
    ArrayList<User> users;
    Context context;

    private static final int  MY_PERMISSIONS_REQUEST_READ_SMS = 1;
    private static final int  MY_PERMISSIONS_REQUEST_READ_CONTACTS = 2;

    String recipientName;
    String recipientNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //if statement for requesting info
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_SMS},
                    MY_PERMISSIONS_REQUEST_READ_SMS);
        }

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CONTACTS},
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS);
        }
        List<SMS> smsList = new ArrayList<SMS>();

        Uri uri = Uri.parse("content://sms/inbox");
        Cursor c = getContentResolver().query(uri, null, null, null, null);
        startManagingCursor(c);

        // Read the sms data and store it in the list
        if (c.moveToFirst()) {
            for (int i = 0; i < c.getCount(); i++) {
                SMS sms = new SMS();
                recipientNumber = c.getString(c.getColumnIndexOrThrow("address")).toString(); //think this is the name of who sent it
                String body = c.getString(c.getColumnIndexOrThrow("body")).toString();
                recipientName = getContactName(recipientNumber, this); //make sure that the body is written by who you think it is


                sms.setBody(body);
                sms.setNumber(recipientNumber);
                sms.setContact(recipientName);

                smsList.add(sms);
                c.moveToNext();
            }
        }
        c.close();
        // Set smsList in the ListAdapter
        setListAdapter(new ListAdapter(this, smsList));

        ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
        ivProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, ProfileActivity.class);
                i.putExtra("name", recipientName);
                i.putExtra("number", recipientNumber);
                MainActivity.this.startActivity(i);
            }
        });
    }


        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void launchProfileActivity(MenuItem item) {
        //launches the profile view
        Intent i = new Intent(MainActivity.this, ProfileActivity.class);

        i.putExtra("user", user);
        MainActivity.this.startActivity(i);
    }

    public void launchComposeActivity(MenuItem item) {
        //launches the profile view
        Intent i = new Intent(MainActivity.this, ComposeActivity.class);
        MainActivity.this.startActivity(i);
    }

    //this might be why we can't get to the messaging activity
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        SMS sms = (SMS) getListAdapter().getItem(position);

        Intent intent = new Intent(this, MessagingActivity.class);
        intent.putExtra("recipientName", recipientName);
        intent.putExtra("recipientNumber", recipientNumber);

        startActivity(intent);

        //want to send to MessageActivity
        //want to send name and number of whose text you clicked in intent
    }

    public String getContactName(final String phoneNumber,Context context)

    {

        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI,Uri.encode(phoneNumber));

        String[] projection = new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME};

        String contactName = "";
        Cursor cursor = context.getContentResolver().query(uri,projection,null,null,null);

        if(cursor.moveToFirst())
        {

            contactName=cursor.getString(0);

        }
        cursor.close();
        cursor = null;

        return contactName;
    }
}

