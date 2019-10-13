package com.aspinax.ihrm;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import static android.text.style.TtsSpan.ARG_TEXT;

public class attendanceFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View viewFrag=inflater.inflate(R.layout.attendance, container, false);

        ImageView swipe = viewFrag.findViewById(R.id.swiper);
        Animation slideIn = AnimationUtils.loadAnimation(getActivity(), R.anim.swing);
        swipe.startAnimation(slideIn);

        //Redirect to specified activities
        RelativeLayout gotodayone = viewFrag.findViewById(R.id.gotodayone);

        gotodayone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DayOne.class);
                startActivity(intent);
            }
        });
        return viewFrag;
    }
}