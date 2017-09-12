package kmitl.lab03.weerabhat58070128.simplemydot.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

import kmitl.lab03.weerabhat58070128.simplemydot.R;
import kmitl.lab03.weerabhat58070128.simplemydot.model.Dot;
import kmitl.lab03.weerabhat58070128.simplemydot.model.DotParcelable;
import kmitl.lab03.weerabhat58070128.simplemydot.model.DotSerializable;
import kmitl.lab03.weerabhat58070128.simplemydot.model.Dots;
import kmitl.lab03.weerabhat58070128.simplemydot.view.DotView;

public class MainActivity extends AppCompatActivity
        implements Dots.OnDotsChangeListener {

    private DotView dotView;
    private Dots dots;
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
                    Dot dotTouched = dots.findDot(x, y);

                    if (dotTouched == null) {
                        createDot(x, y);
                    } else {
                        dots.removeDot(dotTouched);
                    }
                }

                return true;
            }
        });
        dots = new Dots();
        dots.setListener(this);

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

    public void onShare(View view) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            return;
        }

        shareImage();
    }

    public void shareImage() {
        String dirPath = Environment.getExternalStorageDirectory() + "/SimpleMyDot";
        String fileName = "capture.png";
        storeImage(getScreenshot(dotView), dirPath, fileName);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(dirPath, fileName)));

        startActivity(Intent.createChooser(intent, "Share to ..."));
    }

    public Bitmap getScreenshot(View view) {
        view.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);
        view.draw(canvas);

        return bitmap;
    }

    public void storeImage(Bitmap bitmap, String dirPath, String fileName) {
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File file = new File(dirPath, fileName);
        try {
            FileOutputStream fOut = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createDot(int x, int y) {
        Random random = new Random();
        int radius = random.nextInt(121) + 30;
        int color = Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));

        Dot dot = new Dot(x, y, radius, color);
        dots.addDot(dot);
    }


    public void onRandomDot(View view) {
        Random random = new Random();
        int centerX = random.nextInt(dotView.getWidth() + 1);
        int centerY = random.nextInt(dotView.getHeight() + 1);

        createDot(centerX, centerY);
    }

    public void onClear(View view) {
        dots.clearAll();
    }

    @Override
    public void onDotsChanged(Dots dots) {
        dotView.setDots(dots);
        dotView.invalidate();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission granted.", Toast.LENGTH_SHORT).show();
                    shareImage();
                } else {
                    Toast.makeText(this, "Permission denied.", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }
}
