package com.aspinax.ihrm;

import android.app.Activity;
import android.app.Dialog;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

public class qrcheckinSuccessful{
    private String delegateName;
    private String sponsorName;

    qrcheckinSuccessful() {};

    qrcheckinSuccessful(String delegate_name, String sponsor_name) {
        this.delegateName = delegate_name;
        this.sponsorName = sponsor_name;
    }

    public void showDialog(Activity activity, String msg){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.qrcheckinsuccessful);

        RelativeLayout eclipse = dialog.findViewById(R.id.eclipse);

        int min = 50;
        int max = 255;

        Random rnd = new Random();
        int color = Color.rgb(rnd.nextInt(max - min + 1), rnd.nextInt(max - min + 1), rnd.nextInt(max - min + 1));
        eclipse.setBackgroundTintList(ColorStateList.valueOf(color));

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //Delegate Names and stuff
        TextView delegatenames = dialog.findViewById(R.id.delegatename);
        TextView firstletter = dialog.findViewById(R.id.firstletter);
        TextView sponsorname = dialog.findViewById(R.id.sponsorname);

//        String delegatename = "Delegate Name";
        String firstchar = this.delegateName.substring(0,1);

        delegatenames.setText(this.delegateName);
        firstletter.setText(firstchar);
        sponsorname.setText(this.sponsorName);

        Button dialogButton = dialog.findViewById(R.id.Done);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }
}
