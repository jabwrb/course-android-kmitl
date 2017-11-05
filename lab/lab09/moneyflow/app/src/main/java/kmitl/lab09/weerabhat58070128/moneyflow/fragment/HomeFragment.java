package kmitl.lab09.weerabhat58070128.moneyflow.fragment;


import android.arch.persistence.room.Room;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.List;

import kmitl.lab09.weerabhat58070128.moneyflow.R;
import kmitl.lab09.weerabhat58070128.moneyflow.model.Balance;
import kmitl.lab09.weerabhat58070128.moneyflow.model.TransactionInfo;
import kmitl.lab09.weerabhat58070128.moneyflow.database.TransactionInfoDB;
import kmitl.lab09.weerabhat58070128.moneyflow.adapter.TransactionInfoRecyclerViewAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {

    private TransactionInfoDB transactionInfoDB;
    private TextView tvMoney;
    private TransactionInfoRecyclerViewAdapter adapter;
    private RecyclerView list;
    private Button btnAdd;

    private HomeFragmentListener listener;

    public interface HomeFragmentListener {
        void onBtnAddPressed();
    }

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        transactionInfoDB = Room.databaseBuilder(getActivity(), TransactionInfoDB.class, "TRANSACTION_INFO_DB")
                .fallbackToDestructiveMigration()
                .build();

        adapter = new TransactionInfoRecyclerViewAdapter(getActivity());

        listener = (HomeFragmentListener) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        setup(view);
        loadData();

        return view;
    }

    private void setup(View view) {
        tvMoney = (TextView) view.findViewById(R.id.tv_money);

        list = (RecyclerView) view.findViewById(R.id.list);
        list.setLayoutManager(new LinearLayoutManager(getActivity()));
        list.setAdapter(adapter);

        btnAdd = (Button) view.findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(this);
    }

    private void loadData() {
        new AsyncTask<Void, Void, Balance>() {
            @Override
            protected Balance doInBackground(Void... voids) {
                return transactionInfoDB.transactionInfoDAO().getBalance();
            }

            @Override
            protected void onPostExecute(Balance balance) {
                int total = balance.getBalance();
                double balanceRatio = (double) total/balance.getSumIncome();

                if (balanceRatio < 0.25) {
                    tvMoney.setTextColor(Color.parseColor("#cc0000"));
                } else if (balanceRatio <= 0.5) {
                    tvMoney.setTextColor(Color.parseColor("#f1c232"));
                } else {
                    tvMoney.setTextColor(Color.parseColor("#6aa84f"));
                }

                tvMoney.setText(NumberFormat.getNumberInstance().format(total));
            }
        }.execute();

        new AsyncTask<Void, Void, List<TransactionInfo>>() {
            @Override
            protected List<TransactionInfo> doInBackground(Void... voids) {
                return transactionInfoDB.transactionInfoDAO().getAll();
            }

            @Override
            protected void onPostExecute(List<TransactionInfo> transactionInfos) {
                adapter.setData(transactionInfos);
                adapter.notifyDataSetChanged();
            }
        }.execute();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add:
                listener.onBtnAddPressed();
                break;
        }
    }
}
