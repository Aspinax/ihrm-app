package com.aspinax.ihrm;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import static java.security.AccessController.getContext;

public class RegisterActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String payment_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Spinner spinner = findViewById(R.id.regPaymentStatus);

        final EditText name = findViewById(R.id.regDelegateName);
        final EditText sponsor_name = findViewById(R.id.regSponsorName);
        final Button submit = findViewById(R.id.regSubmit);
        ImageView gosomewhere = findViewById(R.id.gosomewhere);

        gosomewhere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gohome = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(gohome);
            }
        });

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.WHITE);
        }
        getSupportActionBar().hide();

        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        //end of user experience

        final List<String> list = new ArrayList<String>();
        list.add("CREDIT");
        list.add("PAID");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                payment_status = list.get(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                payment_status = list.get(0);
            }
        });

        final MediaPlayer success = MediaPlayer.create (this, R.raw.success);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DocumentReference ticketRef = db.collection("n").document("totals");
                ticketRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                final Long lastAssigned = document.getLong("lastAssigned") + 1;
                                String uniqueId = Long.toString(lastAssigned);
                                Map<String, Object> delegateInfo = new HashMap<>();
                                delegateInfo.put("unique_id", uniqueId);
                                delegateInfo.put("attendee", name.getText().toString());
                                delegateInfo.put("sponsor_name", sponsor_name.getText().toString());
                                delegateInfo.put("payment_status", payment_status);
                                delegateInfo.put("checkIn15", false);
                                delegateInfo.put("gift15", false);
                                delegateInfo.put("checkIn16", false);
                                delegateInfo.put("gift16", false);
                                delegateInfo.put("checkIn17", false);
                                delegateInfo.put("gift17", false);
                                delegateInfo.put("checkIn18", false);
                                delegateInfo.put("gift18", false);
                                delegateInfo.put("checkIn15Time", "");
                                delegateInfo.put("checkIn16Time", "");
                                delegateInfo.put("checkIn17Time", "");
                                delegateInfo.put("checkIn18Time", "");

                                db.collection("delegates").document(uniqueId)
                                        .set(delegateInfo)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                registrationSuccessful alert = new registrationSuccessful();
                                                alert.showDialog(RegisterActivity.this, "Let the delegate in");
                                                success.start();
                                                name.getText().clear();
                                                sponsor_name.getText().clear();
                                                db.collection("n").document("totals").update("lastAssigned", lastAssigned);
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(RegisterActivity.this, "Error Saving Document, Check your internet connection", Toast.LENGTH_LONG).show();
                                            }
                                        });
                                }
                        }
                    }
                });
            }
        });


    }
}