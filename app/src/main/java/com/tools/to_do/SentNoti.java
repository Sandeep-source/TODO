package com.tools.to_do;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.room.Room;

import com.tools.to_do.db.TODODB;
import com.tools.to_do.db.To_Do;
import com.tools.to_do.db.To_Do_Dao;

public class SentNoti extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        TODODB db= Room.databaseBuilder(context,TODODB.class,"MainDb").build();
        To_Do_Dao dao=db.getToDoDao();
        NotificationManager manager= (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            manager.createNotificationChannel(new NotificationChannel("to-do","to-do",NotificationManager.IMPORTANCE_HIGH));
        }
        new Thread(()->{
        To_Do to_do=dao.getToDo(intent.getLongExtra("id",0));
        if(to_do==null)
            return;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            Notification notification= new Notification.Builder(context,"to-do")
                    .setContentTitle(to_do.getTitle()+"- Hurry... Deadline is today")
                    .setContentText(to_do.getDetail())
                    .setSmallIcon(R.drawable.ic_baseline_assignment_24)
                    .setLargeIcon(
                            BitmapFactory.decodeResource(
                                    context.getResources(),
                                    R.drawable.ic_baseline_notes_24
                            ))
                    .build();
            manager.notify(to_do.get_id(),notification);

        }else{
            Notification notification= new Notification.Builder(context)
                    .setContentTitle(to_do.getTitle()+"- Hurry... Deadline is today")
                    .setContentText(to_do.getDetail())
                    .setLargeIcon(
                            BitmapFactory.decodeResource(
                                    context.getResources(),
                                    R.drawable.ic_baseline_notes_24
                            ))
                    .setSmallIcon(R.drawable.ic_baseline_assignment_24)
                    .build();
            manager.notify(to_do.get_id(),notification);
        }
        }).start();

    }
}