package kmitl.lab03.weerabhat58070128.simplemydot.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Random;

import kmitl.lab03.weerabhat58070128.simplemydot.R;
import kmitl.lab03.weerabhat58070128.simplemydot.model.Dot;
import kmitl.lab03.weerabhat58070128.simplemydot.model.DotParcelable;
import kmitl.lab03.weerabhat58070128.simplemydot.model.DotSerializable;
import kmitl.lab03.weerabhat58070128.simplemydot.view.DotView;

public class MainActivity extends AppCompatActivity
        implements Dot.OnDotChangedListener {

    private DotView dotView;
    private ArrayList<Dot> dots;

    //private DotSerializable dotSerializable;
    private Button btnOpenActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dotView = (DotView) findViewById(R.id.dotView);
        dotView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int x = (int) event.getX();
                int y = (int) event.getY();

                if (event.getAction() == event.ACTION_UP) {
                    Dot dotTouched = getDotTouched(x, y);

                    if (dotTouched == null) {
                        createDot(x, y);
                    } else {
                        dots.remove(dotTouched);
                        dotView.invalidate();
                    }
                }

                return true;
            }
        });
        dots = new ArrayList<>();

        /*--- Lab04: Intent ---*/
        final DotSerializable dotSerializable = new DotSerializable(150, 150, 30, Color.RED);
        final DotParcelable dotParcelable = new DotParcelable(100, 100, 50, Color.BLUE);

        btnOpenActivity = (Button) findViewById(R.id.btnOpenActivity);
        btnOpenActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);

                intent.putExtra("test", "Test Text.");
                intent.putExtra("dotSerializable", dotSerializable);
                intent.putExtra("dotParcelable", dotParcelable);

                startActivity(intent);
            }
        });
    }

    public void createDot(int x, int y) {
        Random random = new Random();
        int radius = random.nextInt(121) + 30;
        int color = Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));

        Dot dot = new Dot(this, x, y, radius, color);
        dots.add(dot);
    }

    public Dot getDotTouched(int x, int y) {
        for (int i = dots.size() - 1; i >= 0; i--) {
            Dot dot = dots.get(i);
            double distance = Math.pow(Math.pow((dot.getCenterX() - x), 2) + Math.pow((dot.getCenterY() - y), 2), 0.5);

            if(distance <= dot.getRadius()) {
                return dot;
            }
        }

        return null;
    }

    public void onRandomDot(View view) {
        Random random = new Random();
        int centerX = random.nextInt(dotView.getWidth() + 1);
        int centerY = random.nextInt(dotView.getHeight() + 1);

        createDot(centerX, centerY);
    }

    public void onClear(View view) {
        dots.clear();
        dotView.invalidate();
    }

    @Override
    public void onDotChange(Dot dot) {
        dotView.setDots(dots);
        dotView.invalidate();
    }
}
