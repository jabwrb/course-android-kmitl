package kmitl.lab03.weerabhat58070128.simplemydot.fragment;


import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

import kmitl.lab03.weerabhat58070128.simplemydot.R;
import kmitl.lab03.weerabhat58070128.simplemydot.model.Dot;
import kmitl.lab03.weerabhat58070128.simplemydot.model.Dots;
import kmitl.lab03.weerabhat58070128.simplemydot.view.DotView;

/**
 * A simple {@link Fragment} subclass.
 */
public class DotFragment extends Fragment implements View.OnClickListener, Dots.OnDotsChangeListener {

    private final String KEY_DOTS = "dots";

    private DotView dotView;
    private Dots dots;
    private ImageButton btnShare;
    private Button btnRandomDot;
    private Button btnClear;

    public static DotFragment newInstance() {
        Bundle args = new Bundle();
        DotFragment fragment = new DotFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState == null) {
            dots = new Dots();
        } else {
            dots = savedInstanceState.getParcelable(KEY_DOTS);
        }
        dots.setListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_dot, container, false);

        dotView = (DotView) rootView.findViewById(R.id.dotView);
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

        btnShare = (ImageButton) rootView.findViewById(R.id.btnShare);
        btnRandomDot = (Button) rootView.findViewById(R.id.btnRandomDot);
        btnClear = (Button) rootView.findViewById(R.id.btnClear);

        btnShare.setOnClickListener(this);
        btnRandomDot.setOnClickListener(this);
        btnClear.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        onDotsChanged(dots);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable(KEY_DOTS, dots);
    }

    public void onShare() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            return;
        }

        shareImage();
    }

    private void shareImage() {
        final String DIR_PATH = Environment.getExternalStorageDirectory() + "/SimpleMyDot";
        final String FILE_NAME = "capture.png";

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Share");
        builder.setItems(R.array.shareble_view, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        storeImage(getScreenshot(getView().getRootView()), DIR_PATH, FILE_NAME);
                        break;
                    case 1:
                        storeImage(getScreenshot(dotView), DIR_PATH, FILE_NAME);
                        break;
                }
                sendImage(new File(DIR_PATH, FILE_NAME));
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.create();
        builder.show();
    }

    private Bitmap getScreenshot(View view) {
        view.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);
        view.draw(canvas);

        return bitmap;
    }

    private void storeImage(Bitmap bitmap, String dirPath, String fileName) {
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

    private void sendImage(File file) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));

        startActivity(Intent.createChooser(intent, "Share to"));
    }

    public void onRandomDot() {
        Random random = new Random();
        int centerX = random.nextInt(dotView.getWidth() + 1);
        int centerY = random.nextInt(dotView.getHeight() + 1);

        createDot(centerX, centerY);
    }

    private void createDot(int x, int y) {
        Random random = new Random();
        int radius = random.nextInt(121) + 30;
        int color = Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));

        Dot dot = new Dot(x, y, radius, color);
        dots.addDot(dot);
    }

    public void onClear() {
        dots.clearAll();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnShare:
                onShare();
                break;
            case R.id.btnRandomDot:
                onRandomDot();
                break;
            case R.id.btnClear:
                onClear();
                break;
        }
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
                    Toast.makeText(getContext(), "Permission granted.", Toast.LENGTH_SHORT).show();
                    shareImage();
                } else {
                    Toast.makeText(getContext(), "Permission denied.", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }
}
