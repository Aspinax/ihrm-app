package com.aspinax.ihrm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import static android.text.style.TtsSpan.ARG_TEXT;

public class giftFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View viewFrag=inflater.inflate(R.layout.gift, container, false);

        ImageView swipe = viewFrag.findViewById(R.id.swiper);
        Animation slideIn = AnimationUtils.loadAnimation(getActivity(), R.anim.swing);
        swipe.startAnimation(slideIn);
        return viewFrag;
    }
}