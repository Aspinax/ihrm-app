package com.aspinax.ihrm;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

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
        return inflater.inflate(R.layout.home,container,false);
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
                    attDayOne.setText(Long.toString(snapshot.getLong(" 2019-10-15CheckIn")));
                    giftDayOne.setText(Long.toString(snapshot.getLong(" 2019-10-15Gift")));
                    attDayTwo.setText(Long.toString(snapshot.getLong(" 2019-10-16CheckIn")));
                    giftDayTwo.setText(Long.toString(snapshot.getLong(" 2019-10-16Gift")));
                    attDayThree.setText(Long.toString(snapshot.getLong(" 2019-10-17CheckIn")));
                    giftDayThree.setText(Long.toString(snapshot.getLong(" 2019-10-17Gift")));
                    attDayFour.setText(Long.toString(snapshot.getLong(" 2019-10-18CheckIn")));
                    giftDayFour.setText(Long.toString(snapshot.getLong(" 2019-10-18Gift")));
                } else {
                    Log.d(TAG, "Current data: null");
                }
            }
        });
    }
}