package com.tools.to_do;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.tools.to_do.databinding.FragmentFirstBinding;
import com.tools.to_do.databinding.FragmentSecondBinding;
import com.tools.to_do.db.TODODB;
import com.tools.to_do.db.To_Do;
import com.tools.to_do.db.To_Do_Dao;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.List;

public class ViewModel extends AndroidViewModel {
    private static final String TAG = "ViewModel";
    private TODODB db;
    private FragmentFirstBinding binding;

    public To_Do_Dao getDao() {
        return dao;
    }

    private To_Do_Dao dao;
    public ViewModel(@NonNull Application application) {
        super(application);
        db=Room.databaseBuilder(
                application.getApplicationContext(),
                TODODB.class,
                "MainDb").build();
        dao=db.getToDoDao();

    }

    public void setBinding(@NotNull FragmentFirstBinding binding) {
        new Thread(()->{
            List<To_Do> lst=dao.getToDos();
            new Handler(getApplication().getMainLooper()).post(()-> {
                ToDoAdapter adapter=new ToDoAdapter(lst,dao);
                binding.todoList.setLayoutManager(
                        new LinearLayoutManager(
                                getApplication().getApplicationContext()
                        )
                );
                if(lst.size()<1){
                    binding.helpText.setText("Great...😀\n It Seams you have finished all your work. 🙌");
                    binding.helpText.setVisibility(View.VISIBLE);
                }else{
                    binding.helpText.setVisibility(View.INVISIBLE);
                }
                binding.todoList.setAdapter(adapter);
            });
        }).start();
    }

    public boolean setToDo(@NotNull FragmentSecondBinding binding) {
        String title=binding.titleInput.getText().toString();
        String details=binding.detailsInput.getText().toString();
        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH,binding.deadline.getDayOfMonth());
        calendar.set(Calendar.MONTH,binding.deadline.getMonth());
        calendar.set(Calendar.YEAR,binding.deadline.getYear());
        long time=calendar.getTimeInMillis();
        To_Do todo=new To_Do(title,details,time,false);

        new Thread(()->{

            long id=dao.add(todo);
            Log.d(TAG, "setToDo: "+id);
            AlarmManager manager= (AlarmManager) getApplication().getApplicationContext().getSystemService(Context.ALARM_SERVICE);
            Intent intent=new Intent(getApplication().getApplicationContext(),SentNoti.class);
            intent.putExtra("id",id);
            PendingIntent pintent=PendingIntent.getBroadcast(
                    getApplication().getApplicationContext(),(int)id,intent,PendingIntent.FLAG_ONE_SHOT);
            manager.set(AlarmManager.RTC_WAKEUP,time-System.currentTimeMillis(),pintent);
            Log.d(TAG, " time in mili : "+(time-System.currentTimeMillis()));
            Log.d(TAG, "time in hour : "+((time-System.currentTimeMillis())/1000*60));
        }).start();
        return true;
    }

    @Override
    protected void onCleared() {
        db.close();
        super.onCleared();
    }
}
