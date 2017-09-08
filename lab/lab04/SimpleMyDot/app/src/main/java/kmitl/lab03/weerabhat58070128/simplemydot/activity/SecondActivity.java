package kmitl.lab03.weerabhat58070128.simplemydot.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import kmitl.lab03.weerabhat58070128.simplemydot.R;
import kmitl.lab03.weerabhat58070128.simplemydot.model.DotParcelable;
import kmitl.lab03.weerabhat58070128.simplemydot.model.DotSerializable;

public class SecondActivity extends AppCompatActivity {

    private TextView textView;
    private Button btnBack;
    private DotSerializable dotSerializable;
    private DotParcelable dotParcelable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        dotSerializable = (DotSerializable) getIntent().getSerializableExtra("dotSerializable");
        dotParcelable = (DotParcelable) getIntent().getParcelableExtra("dotParcelable");

        textView = (TextView) findViewById(R.id.textView);
        textView.setText("CenterX: " + dotParcelable.getCenterX());
    }
}
