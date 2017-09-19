package kmitl.lab03.weerabhat58070128.simplemydot.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import kmitl.lab03.weerabhat58070128.simplemydot.R;
import kmitl.lab03.weerabhat58070128.simplemydot.fragment.DotFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragmentContainer, new DotFragment().newInstance())
                    .commit();
        }
    }
}
