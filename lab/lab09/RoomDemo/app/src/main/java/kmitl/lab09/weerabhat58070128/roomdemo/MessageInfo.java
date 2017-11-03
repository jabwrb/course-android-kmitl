package kmitl.lab09.weerabhat58070128.roomdemo;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
class MessageInfo {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String text;

    private String time;

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return String.format("%s :: %s", text, time);
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
