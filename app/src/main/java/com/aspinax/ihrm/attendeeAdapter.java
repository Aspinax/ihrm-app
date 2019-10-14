package com.aspinax.ihrm;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class attendeeAdapter extends ArrayAdapter<Attendees> {
    private Integer date;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public attendeeAdapter(Context context, List<Attendees> object, Integer date){
        super(context,0, object);
        this.date = date;
    }

    private String capitalize(String capString){
        StringBuffer capBuffer = new StringBuffer();
        Matcher capMatcher = Pattern.compile("([a-z])([a-z]*)", Pattern.CASE_INSENSITIVE).matcher(capString);
        while (capMatcher.find()){
            capMatcher.appendReplacement(capBuffer, capMatcher.group(1).toUpperCase() + capMatcher.group(2).toLowerCase());
        }

        return capMatcher.appendTail(capBuffer).toString();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView == null){
            convertView =  ((Activity)getContext()).getLayoutInflater().inflate(R.layout.item_attendee,parent,false);
        }



        TextView attendee_nameTextView = convertView.findViewById(R.id.attendeeName);
        ImageView checkImg = convertView.findViewById(R.id.checkedInBox);

        final Attendees attendee = getItem(position);

        String pure_names = attendee.getAttendee();
        String lowercase = pure_names.toLowerCase();
        String fixed_names = capitalize(lowercase);
        attendee_nameTextView.setText(fixed_names);

        Boolean checkedIn = false;

        switch(this.date) {
            case 15:
                checkedIn = attendee.getCheckIn15();
                break;
            case 16:
                checkedIn = attendee.getCheckIn16();
                break;
            case 17:
                checkedIn = attendee.getCheckIn17();
                break;
            case 18:
                checkedIn = attendee.getCheckIn18();
                break;
        }

        if (checkedIn) {
            checkImg.setBackgroundResource(R.drawable.ic_checked);
        }else{
            checkImg.setBackgroundResource(R.drawable.ic_unchecked);
        }

        RelativeLayout checkindelegate = convertView.findViewById(R.id.checkindelegate);
        checkindelegate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Map<String, Object> checkInInfo = new HashMap<>();
//                checkInInfo.put("checkIn" + date, true);
//                db.collection("delegates").document(attendee.getUnique_id()).update(checkInInfo);

                CheckinSuccessful alert = new CheckinSuccessful();
                alert.showDialog((Activity)getContext(), "You may now let the attendee in!");
            }
        });



        return convertView;


    }
}