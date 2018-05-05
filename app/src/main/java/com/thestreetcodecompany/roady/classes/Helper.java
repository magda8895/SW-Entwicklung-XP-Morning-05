package com.thestreetcodecompany.roady.classes;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

import com.thestreetcodecompany.roady.R;
import com.thestreetcodecompany.roady.StartActivity;

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


    public static void MakePush(Context context)
    {
        MakePush("Hello I'm a subject","and i'm a body",StartActivity.class, context);
    }
    public static void MakePush(String subject,Context context)
    {
        MakePush(subject," I'm a body",StartActivity.class, context);
    }

    public static void MakePush(String subject, String body,Context context)
    {
        MakePush(subject,body,StartActivity.class,context);
    }

    public static void MakePush(String subject, String body,Class<?> intentClass,Context context)
    {
        Intent intent = new Intent(context, intentClass);
        PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent, 0);

        NotificationManager notif=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notify=new Notification.Builder(context)
                .setContentText(body)
                .setContentTitle(subject)
                .setSmallIcon(R.drawable.ic_export)
                .setContentIntent(pIntent)
                .build();
        notify.flags |= Notification.FLAG_AUTO_CANCEL;

        notif.notify(0, notify);

        //TODO: add app icon + maybe change color
    }

}
