package com.tools.to_do.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Date;

@Entity
public class To_Do {
    @PrimaryKey(autoGenerate = true)
    int _id;

    public To_Do(String title, String detail, long deadline, boolean done) {
        this.title = title;
        this.detail = detail;
        this.deadline = deadline;
        this.done = done;
    }
    public To_Do(){

    }

    String title;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public long getDeadline() {
        return deadline;
    }

    public void setDeadline(long deadline) {
        this.deadline = deadline;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    String detail;
    long deadline;
    boolean done;
}
