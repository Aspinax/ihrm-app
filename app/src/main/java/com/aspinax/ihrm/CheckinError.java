package com.aspinax.ihrm;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckinError {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String payment_status;

    public void showDialog(final Activity activity, String msg, final String unique_id, final String attendee, final String sponsor_name){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.checkinerror);

        TextView text = dialog.findViewById(R.id.DialogMessage);
        text.setText(msg);

        Spinner spinner = dialog.findViewById(R.id.errPaymentStatus);
        spinner.setSelection(0);

        final List<String> list = new ArrayList<String>();
        list.add("NOT PAID");
        list.add("CREDIT");
        list.add("PAID");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(activity,
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

        Button dialogButton = dialog.findViewById(R.id.errUpdate);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("delegates").document(unique_id)
                        .update("payment_status", payment_status)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(activity, "Delegate Payment Status Updated", Toast.LENGTH_SHORT).show();
                                Date todayDate = Calendar.getInstance().getTime();
                                SimpleDateFormat timeF = new SimpleDateFormat("HH:mm:ss");
                                SimpleDateFormat dayF = new SimpleDateFormat("dd");
                                final String day = dayF.format(todayDate);
                                final String time = timeF.format(todayDate);

                                Map<String, Object> checkInInfo = new HashMap<>();
                                checkInInfo.put("checkIn" + day, true);
                                checkInInfo.put("checkIn" + day + "Time", time);
                                db.collection("delegates").document(unique_id).update(checkInInfo);
                                qrcheckinSuccessful alert = new qrcheckinSuccessful(attendee, sponsor_name, "checkIn");
                                alert.showDialog(activity, "found");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(activity, "Error Saving Document, Check your internet connection", Toast.LENGTH_LONG).show();
                            }
                        });
                dialog.dismiss();
            }
        });

        dialog.show();

    }
}
