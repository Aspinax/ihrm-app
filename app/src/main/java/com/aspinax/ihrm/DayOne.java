package com.aspinax.ihrm;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.EditText;
import android.widget.RelativeLayout;
import com.google.firebase.firestore.Query;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

public class DayOne extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "DayOne";
    private FirebaseAuth auth;
    private DocumentSnapshot lastVisible;
    private DocumentSnapshot firstVisible;
    private String lastCommand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_one);

        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() == null) {
            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivity(loginIntent);
        }

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.WHITE);
        }
        getSupportActionBar().hide();

        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        //end of user experience
        ImageView gotoattendance = findViewById(R.id.gotoattendance);

        gotoattendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DayOne.this, MainActivity.class);
                startActivity(intent);
            }
        });


        final RelativeLayout next = findViewById(R.id.PreviousPage);
        final RelativeLayout previous = findViewById(R.id.NextPage);
        final Integer date = getIntent().getExtras().getInt("date");
        final String text = getIntent().getExtras().getString("text");

        // Search
        EditText search = findViewById(R.id.searchQuery);
        TextView dateView = findViewById(R.id.firstdatetext);
        TextView textView = findViewById(R.id.attendancelisttext);
        dateView.setText(date + "th October 2019");
        textView.setText(text);

        // Search Stuff
        final LinearLayout searchBtn = findViewById(R.id.searchBtnAtt);
        final String searchQuery = search.getText().toString();

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.collection("delegates")
                        .orderBy("attendee")
                        .startAt(searchQuery)
                        .endAt(searchQuery + '\uf8ff')
                        .limit(25)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    final List<Attendees> attendanceList = new ArrayList<>();
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Attendees attend = document.toObject(Attendees.class);
                                        Log.e("attend", attend.getAttendee() + searchQuery);
                                        attendanceList.add(attend);
                                    }

                                    ListView attendeeList = findViewById(R.id.attendeeList);
                                    final attendeeAdapter attendeeAdapt = new attendeeAdapter(DayOne.this, attendanceList, date);
                                    attendeeList.setAdapter(attendeeAdapt);
                                    attendeeAdapt.notifyDataSetChanged();

                                    if (task.getResult().size() > 0) {
                                        lastVisible = task.getResult().getDocuments().get(task.getResult().size() - 1);
                                        firstVisible = task.getResult().getDocuments().get(0);
                                    }
                                }
                            }
                        });
        }
    });

        db.collection("delegates")
                .orderBy("attendee").limit(25)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            final List<Attendees> attendanceList = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Attendees attend = document.toObject(Attendees.class);
                                attendanceList.add(attend);
                            }

                            ListView attendeeList = findViewById(R.id.attendeeList);
                            final attendeeAdapter attendeeAdapt = new attendeeAdapter(DayOne.this, attendanceList, date);
                            attendeeList.setAdapter(attendeeAdapt);

                            if (task.getResult().size() > 0) {
                                lastVisible = task.getResult().getDocuments().get(task.getResult().size() - 1);
                                firstVisible = task.getResult().getDocuments().get(0);
                            }
                        }


                        /* subsequent queries, after clicking the next button */
                        next.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Query nextQuery;
                                if(lastCommand == "next") {
                                    nextQuery = db.collection("delegates").orderBy("attendee").startAfter(lastVisible).limit(25);
                                } else {
                                    nextQuery = db.collection("delegates").orderBy("attendee").startAfter(firstVisible).limit(25);
                                }
                                nextQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> t) {
                                        if (t.isSuccessful()) {
                                            final List<Attendees> attendanceList = new ArrayList<Attendees>();
                                            for (DocumentSnapshot d : t.getResult()) {
                                                Attendees attend = d.toObject(Attendees.class);
                                                attendanceList.add(attend);
                                            }

                                            if(t.getResult().size() - 1 > 0) {
                                                ListView attendanceListView = findViewById(R.id.attendeeList);
                                                final attendeeAdapter attendeeAdapt = new attendeeAdapter(DayOne.this, attendanceList, date);
                                                attendanceListView.setAdapter(attendeeAdapt);
                                                attendeeAdapt.notifyDataSetChanged();
                                                lastVisible = t.getResult().getDocuments().get(t.getResult().size() - 1);
                                                firstVisible = t.getResult().getDocuments().get(0);
                                                lastCommand = "next";
                                            }
                                        }
                                    }
                                });
                            }
                        });

                        /* clicking back button */
                        previous.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Query nextQuery;
                                if(lastCommand == "back") {
                                    nextQuery = db.collection("delegates").orderBy("attendee", com.google.firebase.firestore.Query.Direction.DESCENDING).startAfter(lastVisible).limit(25);
                                } else {
                                    nextQuery = db.collection("delegates").orderBy("attendee", com.google.firebase.firestore.Query.Direction.DESCENDING).startAfter(firstVisible).limit(25);
                                }
                                nextQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> t) {
                                        if (t.isSuccessful()) {
                                            final List<Attendees> attendanceList = new ArrayList<Attendees>();
                                            for (DocumentSnapshot d : t.getResult()) {
                                                Attendees attend = d.toObject(Attendees.class);
                                                attendanceList.add(attend);
                                            }
                                            Collections.reverse(attendanceList);

                                            if(t.getResult().size() - 1 > 0) {
                                                ListView attendanceListView = findViewById(R.id.attendeeList);
                                                final attendeeAdapter attendeeAdapter = new attendeeAdapter(DayOne.this, attendanceList, date);
                                                attendanceListView.setAdapter(attendeeAdapter);
                                                attendeeAdapter.notifyDataSetChanged();
                                                lastVisible = t.getResult().getDocuments().get(t.getResult().size() - 1);
                                                firstVisible = t.getResult().getDocuments().get(0);
                                                lastCommand = "back";
                                            }
                                        }
                                    }
                                });
                            }
                        });
                    };
                });



        FloatingActionButton launchqr = findViewById(R.id.launchqr);

        launchqr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator integrator = new IntentIntegrator(DayOne.this);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setPrompt("Align the QR code.");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(true);
                integrator.setBarcodeImageEnabled(false);
                integrator.setOrientationLocked(true);
                integrator.setCaptureActivity(CaptureActivityPortrait.class);
                integrator.initiateScan();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        final Integer date = getIntent().getExtras().getInt("date");
        final String event = getIntent().getExtras().getString("event");
        /* Handles the results of the QR Scan */
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Log.e("Scan*******", "Cancelled scan");

            } else {
                /* QR Scan successful */
                Log.e("Scan", "Scanned");

                /* fetch attendee information from firebase */
                DocumentReference ticketRef = db.collection("delegates").document(result.getContents());
                final MediaPlayer mp = MediaPlayer.create (this, R.raw.success);
                final MediaPlayer error = MediaPlayer.create (this, R.raw.error);
                ticketRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                /* attendee found */;
//                                final String dateStr = getIntent().getStringExtra("date");
                                String getpaymentstatus = document.getString("payment_status");
                                if(getpaymentstatus.equals("PAID")) {
                                    if (!document.getBoolean(event + date)) {
                                        Date todayDate = Calendar.getInstance().getTime();
                                        SimpleDateFormat timeF = new SimpleDateFormat("HH:mm:ss");
                                        final String time = timeF.format(todayDate);

                                        Map<String, Object> checkInInfo = new HashMap<>();
                                        checkInInfo.put(event + date, true);
                                        checkInInfo.put(event + date + "Time", time);
                                        db.collection("delegates").document(document.getString("unique_id")).update(checkInInfo);
                                    }

                                    qrcheckinSuccessful alert = new qrcheckinSuccessful(document.getString("attendee"), document.getString("sponsor_name"), event);
                                    alert.showDialog(DayOne.this, "found");
                                    mp.start();
                                }else if(getpaymentstatus.equals("CREDIT")){
                                    if (!document.getBoolean(event + date)) {
                                        Date todayDate = Calendar.getInstance().getTime();
                                        SimpleDateFormat timeF = new SimpleDateFormat("HH:mm:ss");
                                        final String time = timeF.format(todayDate);

                                        Map<String, Object> checkInInfo = new HashMap<>();
                                        checkInInfo.put(event + date, true);
                                        checkInInfo.put(event + date + "Time", time);
                                        db.collection("delegates").document(document.getString("unique_id")).update(checkInInfo);
                                    }

                                    qrcheckinSuccessful alert = new qrcheckinSuccessful(document.getString("attendee"), document.getString("sponsor_name"), event);
                                    alert.showDialog(DayOne.this, "found");
                                    mp.start();
                                }else{
                                    CheckinError alert = new CheckinError();
                                    alert.showDialog(DayOne.this, "Update delegate's payment status", document.getString("unique_id"), document.getString("attendee"), document.getString("sponsor_name"));
                                    error.start();
                                }


                                } else {
                                /* id not found */
                                Toast.makeText(DayOne.this, "Unique ID not found.", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            /* error */
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                });
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}