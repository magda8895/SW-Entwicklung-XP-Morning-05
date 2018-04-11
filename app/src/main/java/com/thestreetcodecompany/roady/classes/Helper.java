package com.thestreetcodecompany.roady.classes;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Rutter on 23.03.2018.
 */

public class Helper {


    public static void MakeToast(String mes, Context context) {
        CharSequence text = mes;
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
    public static void MakeSnackbar(String mes,View view)
    {
        Snackbar.make(view, mes, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }


}
