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

public class giftFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View viewFrag=inflater.inflate(R.layout.gift, container, false);

        ImageView swipe = viewFrag.findViewById(R.id.swiper);
        Animation slideIn = AnimationUtils.loadAnimation(getActivity(), R.anim.swing);
        swipe.startAnimation(slideIn);

        //Redirect to specified activities
        RelativeLayout gotogiftdayone = viewFrag.findViewById(R.id.gotogiftdayone);
        RelativeLayout gotogiftdaytwo = viewFrag.findViewById(R.id.gotogiftdaytwo);
        RelativeLayout gotogiftdaythree = viewFrag.findViewById(R.id.gotogiftdaythree);
        RelativeLayout gotogiftdayfour = viewFrag.findViewById(R.id.gotogiftdayfour);

        gotogiftdayone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), GiftDayOne.class);
                startActivity(intent);
            }
        });
        gotogiftdaytwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), GiftDayTwo.class);
                startActivity(intent);
            }
        });
        gotogiftdaythree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), GiftDayThree.class);
                startActivity(intent);
            }
        });
        gotogiftdayfour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), GiftDayFour.class);
                startActivity(intent);
            }
        });

        return viewFrag;
    }
}