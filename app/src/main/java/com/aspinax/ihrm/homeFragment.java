package com.aspinax.ihrm;

import android.os.Bundle;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.RelativeLayout;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import static android.support.constraint.Constraints.TAG;
import static android.text.style.TtsSpan.ARG_TEXT;

public class homeFragment extends Fragment {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static homeFragment newInstance(String text) {
        Bundle args = new Bundle();
        args.putString(ARG_TEXT, text);

        homeFragment sampleFragment = new homeFragment();
        sampleFragment.setArguments(args);

        return sampleFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View viewhomeFrag=inflater.inflate(R.layout.home, container, false);

        //massive redirecting happening here

        RelativeLayout gotopaiddelegates = viewhomeFrag.findViewById(R.id.gotopaiddelegates);
        RelativeLayout gotocreditdelegates = viewhomeFrag.findViewById(R.id.gotocreditdelegates);
        RelativeLayout gotounpaiddelegates = viewhomeFrag.findViewById(R.id.gotounpaiddelegates);

        RelativeLayout gotoreportdayone = viewhomeFrag.findViewById(R.id.gotoreportdayone);
        RelativeLayout gotoreportdaytwo = viewhomeFrag.findViewById(R.id.gotoreportdaytwo);
        RelativeLayout gotoreportdaythree = viewhomeFrag.findViewById(R.id.gotoreportdaythree);
        RelativeLayout gotoreportdayfour = viewhomeFrag.findViewById(R.id.gotoreportdayfour);

        RelativeLayout gotogiftreportdayone = viewhomeFrag.findViewById(R.id.gotogiftreportdayone);
        RelativeLayout gotogiftreportdaytwo = viewhomeFrag.findViewById(R.id.gotogiftreportdaytwo);
        RelativeLayout gotogiftreportdaythree = viewhomeFrag.findViewById(R.id.gotogiftreportdaythree);
        RelativeLayout gotogiftreportdayfour = viewhomeFrag.findViewById(R.id.gotogiftreportdayfour);


        gotopaiddelegates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PaidDelegates.class);
                startActivity(intent);
            }
        });
        gotocreditdelegates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CreditDelegates.class);
                startActivity(intent);
            }
        });
        gotounpaiddelegates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), UnpaidDelegates.class);
                startActivity(intent);
            }
        });

        gotoreportdayone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ReportDayOne.class);
                startActivity(intent);
            }
        });
        gotoreportdaytwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ReportDayTwo.class);
                startActivity(intent);
            }
        });
        gotoreportdaythree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ReportDayThree.class);
                startActivity(intent);
            }
        });
        gotoreportdayfour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ReportDayFour.class);
                startActivity(intent);
            }
        });


        gotogiftreportdayone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), GiftReportDayOne.class);
                startActivity(intent);
            }
        });
        gotogiftreportdaytwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), GiftReportDayTwo.class);
                startActivity(intent);
            }
        });
        gotogiftreportdaythree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), GiftReportDayThree.class);
                startActivity(intent);
            }
        });
        gotogiftreportdayfour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), GiftReportDayFour.class);
                startActivity(intent);
            }
        });

        //end of massive redirects
        return viewhomeFrag;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        // Hardcoding for Dummies, LOL
        final TextView totalDelegates= getView().findViewById(R.id.total_delegates);
        final TextView paidBookings = getView().findViewById(R.id.paidbookingsno);
        final TextView creditBookings = getView().findViewById(R.id.credit_bookings);
        final TextView unpaidBookings = getView().findViewById(R.id.unpaid_bookings);
        final TextView attDayOne = getView().findViewById(R.id.dayonedelegates);
        final TextView giftDayOne = getView().findViewById(R.id.dayonegiftdelegates);
        final TextView attDayTwo = getView().findViewById(R.id.daytwodelegates);
        final TextView giftDayTwo = getView().findViewById(R.id.daytwogiftdelegates);
        final TextView attDayThree = getView().findViewById(R.id.daythreedelegates);
        final TextView giftDayThree = getView().findViewById(R.id.daythreegiftdelegates);
        final TextView attDayFour = getView().findViewById(R.id.dayfourdelegates);
        final TextView giftDayFour = getView().findViewById(R.id.dayfourgiftdelegates);


        final DocumentReference delegateRef = db.collection("n").document("totals");
        delegateRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e);
                    return;
                }

                if (snapshot != null && snapshot.exists()) {
                    Log.d(TAG, "Current data: " + snapshot.getData());
                    // Hardcoding for Dummies, LOL
                    totalDelegates.setText(Long.toString(snapshot.getLong("checkIn")));
                    paidBookings.setText(Long.toString(snapshot.getLong("paid")));
                    creditBookings.setText(Long.toString(snapshot.getLong("credit")));
                    unpaidBookings.setText(Long.toString(snapshot.getLong("unpaid")));
                    attDayOne.setText(Long.toString(snapshot.getLong("checkIn15")));
                    giftDayOne.setText(Long.toString(snapshot.getLong("gift15")));
                    attDayTwo.setText(Long.toString(snapshot.getLong("checkIn16")));
                    giftDayTwo.setText(Long.toString(snapshot.getLong("gift16")));
                    attDayThree.setText(Long.toString(snapshot.getLong("checkIn17")));
                    giftDayThree.setText(Long.toString(snapshot.getLong("gift17")));
                    attDayFour.setText(Long.toString(snapshot.getLong("checkIn18")));
                    giftDayFour.setText(Long.toString(snapshot.getLong("gift18")));
                } else {
                    Log.d(TAG, "Current data: null");
                }
            }
        });
    }
}