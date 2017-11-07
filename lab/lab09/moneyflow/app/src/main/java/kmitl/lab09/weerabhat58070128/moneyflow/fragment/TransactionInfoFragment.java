package kmitl.lab09.weerabhat58070128.moneyflow.fragment;


import android.arch.persistence.room.Room;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import kmitl.lab09.weerabhat58070128.moneyflow.R;
import kmitl.lab09.weerabhat58070128.moneyflow.model.TransactionInfo;
import kmitl.lab09.weerabhat58070128.moneyflow.database.TransactionInfoDB;
import lib.kingja.switchbutton.SwitchMultiButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class TransactionInfoFragment extends Fragment implements View.OnClickListener {

    private TransactionInfoDB transactionInfoDB;
    private TransactionInfo transactionInfo;
    private SwitchMultiButton smbType;
    private EditText etDescribe;
    private EditText etAmount;
    private Button btnSave;
    private Button btnDelete;

    private TransactionInfoFragmentListener listener;

    public interface TransactionInfoFragmentListener {
        void onBtnSavePressed();
        void onBtnDeletePressed();
    }

    public TransactionInfoFragment() {
        // Required empty public constructor
    }

    public static TransactionInfoFragment newInstance(TransactionInfo transactionInfo) {
        Bundle args = new Bundle();
        args.putParcelable("transactionInfo", transactionInfo);
        TransactionInfoFragment fragment = new TransactionInfoFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        transactionInfoDB = Room.databaseBuilder(getActivity(), TransactionInfoDB.class, "TRANSACTION_INFO_DB")
                .fallbackToDestructiveMigration()
                .build();

        transactionInfo = getArguments().getParcelable("transactionInfo");

        listener = (TransactionInfoFragmentListener) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_transaction_info, container, false);

        setup(view);

        return view;
    }

    private void setup(View view) {
        smbType = view.findViewById(R.id.smb_type);

        etDescribe = view.findViewById(R.id.et_describe);
        etAmount = view.findViewById(R.id.et_amount);

        btnSave = view.findViewById(R.id.btn_save);
        btnSave.setOnClickListener(this);
        btnDelete = view.findViewById(R.id.btn_delete);
        btnDelete.setOnClickListener(this);

        if (transactionInfo.getId() != 0) {
            if (transactionInfo.getType().equals("expense")) {
                smbType.setSelectedTab(1);
            }

            etDescribe.setText(transactionInfo.getDescribe());
            etAmount.setText(String.valueOf(transactionInfo.getAmount()));

            btnDelete.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_save:
                onBtnSave();
                break;

            case R.id.btn_delete:
                onBtnDelete();
                break;
        }
    }

    private void onBtnSave() {
        String type = smbType.getSelectedTab() == 0 ? "income" : "expense";
        String describe = etDescribe.getText().toString();
        String amount = etAmount.getText().toString();

        transactionInfo.setType(type);

        if (!describe.isEmpty()) {
            transactionInfo.setDescribe(describe);
        } else {
            Toast.makeText(getActivity(), "Please enter description.", Toast.LENGTH_LONG).show();
            return;
        }

        try {
            transactionInfo.setAmount(Integer.parseInt(amount));
        } catch (NumberFormatException e) {
            Toast.makeText(getActivity(), "Please enter amount.", Toast.LENGTH_LONG).show();
            return;
        }

        if (transactionInfo.getId() == 0) {
            insertTransaction();
        } else {
            updateTransaction();
        }
    }

    private void insertTransaction() {
        new AsyncTask<Void, Void, TransactionInfo>() {
            @Override
            protected TransactionInfo doInBackground(Void... voids) {
                transactionInfoDB.transactionInfoDAO().insert(transactionInfo);

                return null;
            }

            @Override
            protected void onPostExecute(TransactionInfo transactionInfo) {
                listener.onBtnSavePressed();
            }
        }.execute();
    }

    private void updateTransaction() {
        new AsyncTask<Void, Void, TransactionInfo>() {
            @Override
            protected TransactionInfo doInBackground(Void... voids) {
                transactionInfoDB.transactionInfoDAO().update(transactionInfo);

                return null;
            }

            @Override
            protected void onPostExecute(TransactionInfo transactionInfo) {
                listener.onBtnSavePressed();
            }
        }.execute();
    }

    private void onBtnDelete() {
        deleteTransaction();
    }

    private void deleteTransaction() {
        new AsyncTask<Void, Void, TransactionInfo>() {
            @Override
            protected TransactionInfo doInBackground(Void... voids) {
                transactionInfoDB.transactionInfoDAO().delete(transactionInfo);

                return null;
            }

            @Override
            protected void onPostExecute(TransactionInfo transactionInfo) {
                listener.onBtnDeletePressed();
            }
        }.execute();
    }
}
