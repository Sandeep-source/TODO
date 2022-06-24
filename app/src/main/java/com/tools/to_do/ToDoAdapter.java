package com.tools.to_do;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.tools.to_do.db.To_Do;
import com.tools.to_do.db.To_Do_Dao;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ViewHolder> {

    List<To_Do> todos;
    To_Do_Dao dao;
    SimpleDateFormat formatter;
    public ToDoAdapter(List<To_Do> todos, To_Do_Dao dao) {
        this.todos=todos;
        this.dao=dao;
        formatter=new SimpleDateFormat("dd/MM/yy");
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.todo_item,parent,false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        To_Do todo=todos.get(position);
        holder.title.setText(todo.getTitle());
        holder.details.setText(todo.getDetail());
        if(todo.isDone()){
            holder.icon.setImageResource(R.drawable.ic_baseline_done_all_24);
        }else {
            holder.icon.setImageResource(R.drawable.ic_baseline_notes_24);
        }

        holder.icon.setOnClickListener((v)->{

            if(todo.isDone()){
                todo.setDone(false);
                holder.icon.setImageResource(R.drawable.ic_baseline_notes_24);
                Toast.makeText(v.getContext(),"Mark as Undone",Toast.LENGTH_SHORT).show();
                new Thread(()->dao.update(todo)).start();
            }else{
                todo.setDone(true);
                holder.icon.setImageResource(R.drawable.ic_baseline_done_all_24);
                Toast.makeText(v.getContext(),"Mark as Done",Toast.LENGTH_SHORT).show();
                new Thread(()->dao.update(todo)).start();
            }
        });
        holder.root.setOnClickListener((v)->{
            new AlertDialog.Builder(v.getContext())
                    .setTitle(todo.getTitle())
                    .setMessage(todo.getDetail())
                    .setPositiveButton("Got it",null)
                    .show();
        });
        holder.deadline.setText(formatter.format(todo.getDeadline()));
        holder.pos.setText(String.valueOf(position));
        holder.cancel.setOnClickListener((v)->{
            To_Do todelete=todos.get(position);
            todos.remove(position);
            notifyItemRemoved(position);
            new Thread(()->dao.delete(todelete)).start();
        });
    }

    @Override
    public int getItemCount() {
        return todos==null?0:todos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView details;
        ImageView icon;
        ImageView cancel;
        TextView deadline;
        TextView pos;
        View root;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            root=itemView;
            title=itemView.findViewById(R.id.title);
            details=itemView.findViewById(R.id.details);
            icon=itemView.findViewById(R.id.icon);
            cancel=itemView.findViewById(R.id.cancel);
            deadline=itemView.findViewById(R.id.date);
            pos=itemView.findViewById(R.id.pos);

        }
    }
}
