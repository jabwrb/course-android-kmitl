package kmitl.lab03.weerabhat58070128.simplemydot.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

import kmitl.lab03.weerabhat58070128.simplemydot.model.Dot;

public class DotView extends View {

    private Paint paint;
    private ArrayList<Dot> dots;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(this.dots != null) {
            for (Dot dot : dots) {
                paint.setColor(dot.getColor());
                canvas.drawCircle(dot.getCenterX(), dot.getCenterY(), dot.getRadius(), paint);
            }
        }
    }

    public DotView(Context context) {
        super(context);
        paint = new Paint();
    }

    public DotView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
    }

    public DotView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
    }

    public void setDots(ArrayList<Dot> dots) {
        this.dots = dots;
    }
}
