package kmitl.lab03.weerabhat58070128.simplemydot.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import kmitl.lab03.weerabhat58070128.simplemydot.R;
import kmitl.lab03.weerabhat58070128.simplemydot.fragment.DotFragment;
import kmitl.lab03.weerabhat58070128.simplemydot.fragment.EditDotFragment;
import kmitl.lab03.weerabhat58070128.simplemydot.model.Dot;

public class MainActivity extends AppCompatActivity implements DotFragment.DotFragmentListener, EditDotFragment.EditDotFragmentListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragmentContainer, new DotFragment().newInstance(), DotFragment.TAG)
                    .commit();
        }
    }

    @Override
    public void onDotSelected(Dot dot, int index) {
        getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.fragmentContainer, new EditDotFragment().newInstance(dot, index))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onCancelPressed() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onConfirmPressed(Dot dot, int index) {
        DotFragment dotFragment = (DotFragment) getSupportFragmentManager().findFragmentByTag(DotFragment.TAG);
        dotFragment.updateEditedDot(dot, index);

        getSupportFragmentManager().popBackStack();
    }
}
