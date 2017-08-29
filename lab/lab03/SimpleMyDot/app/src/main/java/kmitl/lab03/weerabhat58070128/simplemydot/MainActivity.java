package kmitl.lab03.weerabhat58070128.simplemydot;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

import kmitl.lab03.weerabhat58070128.simplemydot.model.Dot;
import kmitl.lab03.weerabhat58070128.simplemydot.view.DotView;

public class MainActivity extends AppCompatActivity
        implements Dot.OnDotChangedListener {

    private DotView dotView;
    private ArrayList<Dot> dots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dotView = (DotView) findViewById(R.id.dotView);
        dots = new ArrayList<>();
    }

    public void onRandomDot(View view) {
        Random random = new Random();
        int radius = random.nextInt(121) + 30;
        int centerX = random.nextInt((dotView.getWidth() - radius) - radius + 1) + radius; //[(max - min) + 1] + min
        int centerY = random.nextInt((dotView.getHeight() - radius) - radius + 1) + radius;
        int color = Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));

        Dot dot = new Dot(this, centerX, centerY, radius, color);
        dots.add(dot);
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
