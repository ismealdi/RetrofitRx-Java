package com.ismealdi.amrestjava.util;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.ismealdi.amrestjava.R;
import com.kaopiz.kprogresshud.KProgressHUD;

/**
 * Created by Al
 * on 22/04/19 | 19:03
 */
public class Dialogs {
    
    public KProgressHUD initProgressDialog(Context context) {
        return KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(false)
                .setDimAmount(0.1f)
                .setCornerRadius(4f)
                .setSize(45,45)
                .setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setAnimationSpeed(2);
    }
}
