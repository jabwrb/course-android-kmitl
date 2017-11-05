package kmitl.lab09.weerabhat58070128.moneyflow.activity;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import kmitl.lab09.weerabhat58070128.moneyflow.R;
import kmitl.lab09.weerabhat58070128.moneyflow.adapter.TransactionInfoRecyclerViewAdapter;
import kmitl.lab09.weerabhat58070128.moneyflow.fragment.HomeFragment;
import kmitl.lab09.weerabhat58070128.moneyflow.fragment.TransactionInfoFragment;
import kmitl.lab09.weerabhat58070128.moneyflow.model.TransactionInfo;

public class MainActivity extends AppCompatActivity implements HomeFragment.HomeFragmentListener, TransactionInfoRecyclerViewAdapter.TransactionInfoRecyclerViewAdapterListener, TransactionInfoFragment.TransactionInfoFragmentListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragmentContainer, new HomeFragment())
                    .commit();
        }
    }

    @Override
    public void onBtnAddPressed() {
        getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.fragmentContainer, new TransactionInfoFragment().newInstance(new TransactionInfo()))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onItemClickedListener(TransactionInfo transactionInfo) {
        getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.fragmentContainer, new TransactionInfoFragment().newInstance(transactionInfo))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onBtnSavePressed() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onBtnDeletePressed() {
        getSupportFragmentManager().popBackStack();
    }
}
