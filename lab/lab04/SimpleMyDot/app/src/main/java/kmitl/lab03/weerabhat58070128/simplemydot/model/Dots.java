package kmitl.lab03.weerabhat58070128.simplemydot.model;

import java.util.ArrayList;
import java.util.List;

public class Dots {

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
