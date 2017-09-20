package kmitl.lab03.weerabhat58070128.simplemydot.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Dots implements Parcelable {

    public Dots() {
    }

    protected Dots(Parcel in) {
        in.readList(dots, null);
    }

    public static final Creator<Dots> CREATOR = new Creator<Dots>() {
        @Override
        public Dots createFromParcel(Parcel in) {
            return new Dots(in);
        }

        @Override
        public Dots[] newArray(int size) {
            return new Dots[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(dots);
    }

    public interface OnDotsChangeListener {
        void onDotsChanged(Dots dots);
    }

    private OnDotsChangeListener listener;

    public void setListener(OnDotsChangeListener listener) {
        this.listener = listener;
    }

    private List<Dot> dots = new ArrayList<>();

    public List<Dot> getDots() {
        return dots;
    }

    public void addDot(Dot dot) {
        this.dots.add(dot);
        this.listener.onDotsChanged(this);
    }

    public void removeDot(Dot dot) {
        this.dots.remove(dot);
        this.listener.onDotsChanged(this);
    }

    public void clearAll() {
        this.dots.clear();
        this.listener.onDotsChanged(this);
    }

    public void setDot(int index, Dot dot) {
        this.dots.set(index, dot);
        this.listener.onDotsChanged(this);
    }

    public int getDotIndex(Dot dot) {
        return this.dots.indexOf(dot);
    }

    public Dot findDot(int x, int y) {
        for (int i = dots.size() - 1; i >= 0; i--) {
            Dot dot = dots.get(i);
            double distance = Math.pow(Math.pow((dot.getCenterX() - x), 2) + Math.pow((dot.getCenterY() - y), 2), 0.5);

            if (distance <= dot.getRadius()) {
                return dot;
            }
        }

        return null;
    }
}
